package com.sign.groundzero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/*CLASE CONCRETA: Representa al jugador, extiende entidad e implementa
 *comportamiento especifico del jugador*/

public class Superviviente extends Entidad {
	private Sound sonidoHerido;
	private Sound sonidoDisparo;
	private Texture texturaProyectil;
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido;
	private int vidas = 3;
	
	public Superviviente(float x, float y, Texture textura, Sound sonidoHerido, Texture texturaProyectil, Sound sonidoDisparo) {
		super(x, y, 90, 90, textura, 100);
		this.sonidoHerido = sonidoHerido;
		this.sonidoDisparo = sonidoDisparo;
		this.texturaProyectil = texturaProyectil;
		this.vidas = 3;
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
		super.actualizar(delta);
	}
	
	@Override
	public void dibujar(SpriteBatch batch) {
		if(herido) {
			getSprite().setX(getSprite().getX() + MathUtils.random(-2, 2));
		}
		super.dibujar(batch);
		if(herido) {
			getSprite().setX(x);
		}
	}
	
	//Permite manejar el movimiento presionando las teclas
	private void manejarMovimiento() {
		float vx = 0;
		float vy = 0;
		
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) vx = -3;
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) vx = 3;
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) vy = -3;
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) vy = 3;
		
		setVelocidad(vx, vy);
	}
	
	private void mantenerEnPantalla() {
	    // Evitar que salga de la pantalla (sin rebotar)
	    if (x < 0) x = 0;
	    if (x + ancho > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - ancho;
	    if (y < 0) y = 0;
	    if (y + alto > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - alto;
	    
	    setPosicion(x, y);
	}
	
	private void manejarEstadoHerido() {
		tiempoHerido--;
		if (tiempoHerido <= 0) {
			herido = false;
		}
	}
	
	@Override
	public void alColisionar(Colisionable otro) {
		if (herido) return;
		if (otro instanceof Enemigo) {
			Enemigo enemigo = (Enemigo) otro;
			setVelocidad(0, 0);
			enemigo.setVelocidad(-enemigo.getVelocidadX(), -enemigo.getVelocidadY());
			
			//Recibir danio
			recibirDanio(1);
			vidas--;
			herido = true;
			tiempoHerido = tiempoHeridoMax;
			sonidoHerido.play();
		}
	}
	
	public BalaPistola disparar() {
		if (sonidoDisparo != null) {
			sonidoDisparo.play();
		}
		return new BalaPistola(x + ancho / 2 - 5, y + alto - 5, 0, 5, texturaProyectil); 
	}
	
	public boolean puedeDisparar() {
		return Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE);
	}
	
	@Override
	public boolean puedeColisionarCon(Colisionable otro) {
        return !herido && !estaDestruido() && otro instanceof Enemigo;
    }
	
	public boolean estaDestruido() {
        return !herido && estaMuerto();
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
}