package com.sign.groundzero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;

/*CLASE CONCRETA: Representa al jugador, extiende entidad e implementa
 *comportamiento especifico del jugador*/

public class Superviviente extends Entidad {
	private Sound sonidoHerido;
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido;
	private int vidas = 3;
	private Arma armaEquipada;
	private Direccion direccionActual = Direccion.ARRIBA;
	
	public enum Direccion{ //Las armas deben poder verlo por eso queda publico.
		ARRIBA, ABAJO, IZQUIERDA, DERECHA
	}
	
	public Superviviente(float x, float y, Texture textura, Sound sonidoHerido, Texture texturaProyectil, Sound sonidoDisparo) {
		super(x, y, 90, 90, textura, 100);
		this.sonidoHerido = sonidoHerido;
		this.vidas = 3;
		this.armaEquipada = new Pistola(texturaProyectil);
		getSprite().setOriginCenter();
	}
	
	@Override
	public void actualizar(float delta) {
		if(!herido) {
			manejarMovimiento();
			mantenerEnPantalla();
		}
		else {
			manejarEstadoHerido();
		}
		armaEquipada.actualizar(delta);
		super.actualizar(delta);
	}
	
	@Override
	public void dibujar(SpriteBatch batch) {
		if(herido) {
			getSprite().setX(getX() + MathUtils.random(-2, 2));
		}
		super.dibujar(batch);
		if(herido) {
			getSprite().setX(getX());
		}
	}
	
	//Permite manejar el movimiento presionando las teclas
	private void manejarMovimiento() {
		float vx = 0;
		float vy = 0;
		
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            vx = -3;
            direccionActual = Direccion.IZQUIERDA;
            getSprite().setRotation(90); // Rota el sprite
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            vx = 3;
            direccionActual = Direccion.DERECHA;
            getSprite().setRotation(-90);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            vy = -3;
            direccionActual = Direccion.ABAJO;
            getSprite().setRotation(180);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            vy = 3;
            direccionActual = Direccion.ARRIBA;
            getSprite().setRotation(0);
        }
        
        setVelocidad(vx, vy);
    }
	
	private void mantenerEnPantalla() {
	    // Evitar que salga de la pantalla (sin rebotar)
		float currentX = getX();
		float currentY = getY();
		float currentAncho = getAncho();
		float currentAlto = getAlto();
		
		float screenWidth = PantallaJuego.getWorldWidth();
		float screenHeight = PantallaJuego.getWorldHeight();
		
		if (currentX < 0) currentX = 0;
	    if (currentX + currentAncho > screenWidth) currentX = screenWidth - currentAncho;
	    if (currentY < 0) currentY = 0;
	    if (currentY + currentAlto > screenHeight) currentY = screenHeight - currentAlto;

	    setPosicion(currentX, currentY);
	}
	
	private void manejarEstadoHerido() {
		tiempoHerido--;
		if (tiempoHerido <= 0) {
			herido = false;
		}
	}
	
	@Override
	public void recibirDanio(int danio) {
		if (herido || estaMuerto()) return; //No recibir danio si esta herido o muerto.
		vidas -= danio;
		herido = true;
		tiempoHerido = tiempoHeridoMax;
		sonidoHerido.play();
		
		if (estaMuerto()) {
			destruir();
		}
	}
	
	@Override
	public int getSalud() {
		return vidas;
	}
	
	@Override
	public boolean estaMuerto() {
		return vidas <= 0;
	}
	
	@Override
	public void alColisionar(Colisionable otro) {
		if (otro instanceof Enemigo) {
			recibirDanio(1);
			Enemigo enemigo = (Enemigo) otro; //Para empuje, ver si sirve
			setVelocidad(0, 0);
			enemigo.setVelocidad(-enemigo.getVelocidadX(), -enemigo.getVelocidadY());
		}
	}
	
	public void intentarDisparar(List<Proyectil> balasDelMundo) {
		armaEquipada.disparar(this, balasDelMundo);
	}
	
	//Para cambiar el arma equipada
	
	public void equiparArma(Arma nuevaArma) {
		this.armaEquipada = nuevaArma;
	}
	
	@Override
	public boolean puedeColisionarCon(Colisionable otro) {
		return !herido && !estaMuerto() && otro instanceof Enemigo;
    }
	
	public int getVidas() {
        return vidas;
    }
	
	public void setVidas(int vidas) {
        this.vidas = vidas;
    }
	
	public boolean estaHerido() {
        return herido;
    }
	
	public Direccion getDireccionActual() { //Getter para que el arma separ la direccion
        return direccionActual;
    }
}