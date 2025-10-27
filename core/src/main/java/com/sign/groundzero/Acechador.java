package com.sign.groundzero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

//Clase Concreta: Representa un enemigo de tipo acechador.
//Extiende Enemigo.

public class Acechador extends Enemigo {
	private final float velocidadBase;
	
	//Constructor

	public Acechador(float x, float y, int ancho, int alto, float velocidad, Texture textura) {
		super(x, y, ancho, alto, textura, 20, 25);
		this.velocidadBase = velocidad;
		validarPosicionInicial(ancho, alto); //Validar posicion inicial dentro de pantalla.
		getSprite().setPosition(getX(), getY());
	}
	
	//Para validar que la posicion inicial este dentro de los limites.
	
	private void validarPosicionInicial(int ancho, int alto) {
		float newX = getX();
		float newY = getY();

		if(newX < 0) {
			newX = 0;
		}
        if (newX + ancho > Gdx.graphics.getWidth()) {
            newX = Gdx.graphics.getWidth() - ancho;
        }
        if (newY < 0) {
            newY = 0;
        }
        if (newY + alto > Gdx.graphics.getHeight()) {
            newY = Gdx.graphics.getHeight() - alto;
        }
        setPosicion(newX, newY);
	}
	
	//Actualizar el estado del acechador
	
    @Override
    public void actualizar(float delta) {
        comportamientoMovimiento(delta);
        super.actualizar(delta);
    }
    
    //Implementa el movimiento especifico, en este caso un movimiento erratico rebotando en bordes.
    
    @Override
    public void comportamientoMovimiento(float delta) {
        float vx = getVelocidadX();
        float vy = getVelocidadY();
        
        if (vx == 0 && vy == 0) { 
        	vx = velocidadBase;
        	vy = velocidadBase;
        }

        float currentX = getX();
        float currentY = getY();
        float currentAncho = getAncho();
        float currentAlto = getAlto();

        if ((currentX <= 0 && vx < 0) || (currentX + currentAncho >= Gdx.graphics.getWidth() && vx > 0)) {
            vx *= -1;
        }
        
        if ((currentY <= 0 && vy < 0) || (currentY + currentAlto >= Gdx.graphics.getHeight() && vy > 0)) {
            vy *= -1;
        }
        
        setVelocidad(vx, vy);
    }
    
    //Para manejar las colisiones con otros objetos usando polimorfismo, respeta LSP al usar interfaces y no clases concretas.
    
    @Override
    public void alColisionar(Colisionable otro) {
        if (otro instanceof Movible && otro instanceof Enemigo) { // Rebote con otros enemigos (Movible), que es una regla especifica del juego en si.
            manejarReboteConEnemigo((Movible) otro);
        }
    }
    
    //Para manejar el rebote con otro enemigo usando interfaces
    
    private void manejarReboteConEnemigo(Movible otroMovible) {
        float vx = getVelocidadX();
        float vy = getVelocidadY();
        
        setVelocidad(-vx, -vy);
    }
    
    //Para determinar si puede colisionar con otro objeto, respetando LSP al verificar capacidades, no tipos especificos.
    
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {
        return !estaDestruido() && (otro instanceof Enemigo || otro instanceof Daniable); // Puede colisionar con otros enemigos o con entidades dañables, que es una regla del juego.
    }
}
