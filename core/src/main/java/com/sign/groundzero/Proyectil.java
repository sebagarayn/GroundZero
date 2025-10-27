package com.sign.groundzero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*CLASE ABSTRACTA: Para todos los proyectiles del juego, extiende ObjetoJuego e implementa
 *Colisionable y Movible*/

public abstract class Proyectil extends ObjetoJuego implements Colisionable, Movible {
	private Sprite sprite;
	private float velocidadX;
	private float velocidadY;
	
	public Proyectil(float x, float y, float velocidadX, float velocidadY, Texture textura, float ancho, float alto) {
		super(x, y, ancho, alto);
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.sprite = new Sprite(textura);
		this.sprite.setBounds(x, y, ancho, alto);
	}
	
	//Implementacion de ObjetoJuego
	@Override
	public void actualizar(float delta) {
		mover(delta);
		sprite.setPosition(getX(), getY());
		
		//Para destruir si sale de la pantalla
		if(fueraDePantalla()) {
			destruir();
		}
	}
	
	@Override
	public void dibujar(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	//Implementación de Movible
	@Override
	public void mover(float delta) {
		setPosicion(getX() + velocidadX, getY() + velocidadY);
	}
	
	@Override
	public void setVelocidad(float vx, float vy) {
		this.velocidadX = vx;
		this.velocidadY = vy;
	}
	
	@Override
	public float getVelocidadX() {
		return velocidadX;
	}
	
	@Override
	public float getVelocidadY() {
		return velocidadY;
	}
	
	//Implementacion de Colisionable
	@Override
	public void alColisionar(Colisionable otro) {
		destruir();
	}
	
	@Override
	public boolean puedeColisionarCon(Colisionable otro) {
		return !estaDestruido();
	}
	
	//Metodos Auxiliares
	private boolean fueraDePantalla() {
		return getX() < 0 || getX() > Gdx.graphics.getWidth() || getY() < 0 || getY() > Gdx.graphics.getHeight();
	}
	
	protected Sprite getSprite() { //Se necesita que la sublase acceda
		return sprite;
	}
}
