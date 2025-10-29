package com.sign.groundzero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import java.util.List;

/*CLASE CONCRETA: Representa al jugador, extiende entidad e implementa
 *comportamiento especifico del jugador.*/

//====================   CLASE CONCRETA SUPERVIVIENTE   ====================

public class Superviviente extends Entidad implements Objetivo {
	
	//Audio
	
	private Sound sonidoHerido;
	
	// Estado del jugador
	
	private boolean herido;
	private int tiempoHerido;
	private int vidas;
	private Direccion direccionActual;
	
	// Arma equipada
	
	private Arma armaEquipada;	
	
	// Constructor Superviviente
	
	public Superviviente(float x, float y, Texture textura, Sound sonidoHerido, Arma armaInicial) {
		
		super(x, y, 90, 90, textura, ConfiguracionJuego.VIDAS_INICIALES);
	    if (sonidoHerido == null || armaInicial == null) {
	        throw new IllegalArgumentException("Los parámetros no pueden ser null");
	    }
		this.sonidoHerido = sonidoHerido;
		this.vidas = ConfiguracionJuego.VIDAS_INICIALES;
		this.herido = false;
		this.tiempoHerido = 0;
		this.direccionActual = Direccion.ARRIBA;
		this.armaEquipada = armaInicial;
		getSprite().setOriginCenter();
	}
	
	//Actualiza el estado del superviviente, gestiona movimiento, estado herido y actualizacion de arma
	
	@Override
	public void actualizar(float delta) {
		
		if(!herido) {
			manejarMovimiento();
			mantenerEnPantalla(getX(), getY());
		}
		else {
			manejarEstadoHerido();
		}
		armaEquipada.actualizar(delta);
		super.actualizar(delta);
	}
	
	//Para dibujar al superviviente, con temblor si esta herido.
	
	@Override
	public void dibujar(SpriteBatch batch) {
		
		if(herido) {
			getSprite().setX(getX() + MathUtils.random(-ConfiguracionJuego.EFECTO_TEMBLOR, ConfiguracionJuego.EFECTO_TEMBLOR));
		}
		super.dibujar(batch);
		if(herido) {
			getSprite().setX(getX());
		}
	}
	
	//Permite manejar el movimiento presionando las teclas de direccion
	
	private void manejarMovimiento() {
		
		float vx = 0;
		float vy = 0;
		
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            vx = -ConfiguracionJuego.VELOCIDAD_SUPERVIVIENTE;
            direccionActual = Direccion.IZQUIERDA;
            getSprite().setRotation(90);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            vx = ConfiguracionJuego.VELOCIDAD_SUPERVIVIENTE;
            direccionActual = Direccion.DERECHA;
            getSprite().setRotation(-90);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            vy = -ConfiguracionJuego.VELOCIDAD_SUPERVIVIENTE;
            direccionActual = Direccion.ABAJO;
            getSprite().setRotation(180);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            vy = ConfiguracionJuego.VELOCIDAD_SUPERVIVIENTE;
            direccionActual = Direccion.ARRIBA;
            getSprite().setRotation(0);
        }
        
        setVelocidad(vx, vy);
    }
	
	//Para mantener al superviviente dentro de los limites de la pantalla
	
	private void mantenerEnPantalla(float targetX, float targetY) {
	
		float currentAncho = getAncho();
    	float currentAlto = getAlto();
    	
    	float screenWidth = Gdx.graphics.getWidth();
    	float screenHeight = Gdx.graphics.getHeight();
		
		if (targetX < 0) {
	        targetX = 0;
	    }
	    if (targetX + currentAncho > screenWidth) {
	        targetX = screenWidth - currentAncho;
	    }
	    if (targetY < 0) {
	        targetY = 0;
	    }
	    if (targetY + currentAlto > screenHeight) {
	        targetY = screenHeight - currentAlto;
	    }
        
	    setPosicion(targetX, targetY);
    }
	
	// Para manejar el estado herido del superviviente
	 
	private void manejarEstadoHerido() {
		
		tiempoHerido--;
		if (tiempoHerido <= 0) {
			herido = false;
		}
	}
	
	// Para que el superviviente no pueda recibir danio mientras esta herido
	
    @Override
    protected boolean esInvulnerableTemporalmente() {
    	
        return estaHerido();
    }
	
	//Para recibir danio y actualizar el estado
	
    @Override
    public void recibirDanio(int danio) {
        if (esInvulnerableTemporalmente() || estaMuerto()) {
            return;
        }   
        vidas -= danio;
        herido = true;
        tiempoHerido = ConfiguracionJuego.TIEMPO_HERIDO_MAX;
        sonidoHerido.play();
        
        if (estaMuerto()) {
            destruir();
        }
    }

    //Para obtener salud actual
	
	@Override
	public int getSalud() {
		
		return vidas;
	}
	
	//Para verificar si el superviviente esta muerto
	
	@Override
	public boolean estaMuerto() {
		
		return vidas <= 0;
	}
	
	//Para manejar la colision con otros objetos
	
    @Override
    public void alColisionar(Colisionable otro) {
    	
        if (esColisionConEnemigo(otro)) {
            manejarColisionConEnemigo((Enemigo) otro);
        }
    }
    
    //Verifica si la colision es con un enemigo
    
    private boolean esColisionConEnemigo(Colisionable otro) {
    	
        return otro instanceof Enemigo;
    }
    
    //Para manejar la colision con un enemigo
    
    private void manejarColisionConEnemigo(Enemigo enemigo) {
    	
        recibirDanio(1);
        setVelocidad(0, 0);
        enemigo.setVelocidad(-enemigo.getVelocidadX(), -enemigo.getVelocidadY());
        
        float dx = getX() - enemigo.getX(); //Para el vector de direccion del empujon
        float dy = getY() - enemigo.getY();
        
        float len = (float) Math.sqrt(dx * dx + dy * dy); //Normalizar el vector
        
        if (len == 0) { //Que no se divida por cero
        	dx = 0; 
        	dy = 1;
        }
        else {
        	dx /= len; 
        	dy /= len;
        }
        
        float targetX = getX() + dx * ConfiguracionJuego.PUSH_DISTANCE; //Nueva posicion
        float targetY = getY() + dy * ConfiguracionJuego.PUSH_DISTANCE;
        
        mantenerEnPantalla(targetX, targetY);
    }
    
    //Para intentar disparar usando el arma que el superviviente tiene equipada
	
	public void intentarDisparar(List<Proyectil> balasDelMundo) {
		
		armaEquipada.disparar(this, balasDelMundo);
	}
	
	//Para cambiar el arma equipada, nueva arma
	
    public void equiparArma(Arma nuevaArma) {
    	
        if (nuevaArma != null) {
            this.armaEquipada = nuevaArma;
        }
    }
	
    //Determina si puede colisionar con otro objeto
    
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {
    	
        return !herido && !estaMuerto() && otro instanceof Enemigo;
    }
    
    // =============== GETTERS ===================
	
	public int getVidas() {
		
        return vidas;
    }
	
    public void setVidas(int vidas) {
    	
        if (vidas >= 0) {
            this.vidas = vidas;
        }
    }
	
	public boolean estaHerido() {
		
        return herido;
    }
	
	public Direccion getDireccionActual() { //Getter para que el arma separ la direccion

        return direccionActual;
    }
}