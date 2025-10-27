/*Clase abstracta para los diferentes objetos del juego, como entidades,
objetos*/

package com.sign.groundzero;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//============================   CLASE OBJETOJUEGO   ========================

public abstract class ObjetoJuego {
	private float x, y;
	private float ancho, alto;
	private boolean destruido = false;
	
	//CONSTRUCTOR
	public ObjetoJuego(float x, float y, float ancho, float alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
	}
	
//=========================   MÉTODOS ABSTRACTOS   ==========================
	
	public abstract void actualizar(float delta);
	public abstract void dibujar(SpriteBatch batch);
	
//=========================   MÉTODOS CONCRETOS   ===========================
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, ancho, alto);
	}
	
	public boolean estaDestruido() {
		return destruido;
	}
	
	public void destruir() {
		destruido = true;
	}
	
//==========================   GETTERS/SETTERS   ============================
	
	public float getX() {return x;}
	public float getY() {return y;}
	public float getAncho() {return ancho;}
	public float getAlto() {return alto;}
	
	protected void setPosicion(float x, float y) {
		this.x = x;
		this.y = y;
	}
}