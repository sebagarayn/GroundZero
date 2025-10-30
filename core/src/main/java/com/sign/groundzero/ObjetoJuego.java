package com.sign.groundzero;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//Clase Abstracta ObjetoJuego: Base para todos los objetos del juego. Define la estructura común de entidades, proyectiles y elementos del mundo.
//Todos los objetos tienen posición, dimensiones y ciclo de vida.*/

public abstract class ObjetoJuego {	
	private float x, y;
	private float ancho, alto;
	private boolean destruido = false;
	
	// Constructor
	public ObjetoJuego(float x, float y, float ancho, float alto) {	
		if(ancho <= 0 || alto <= 0) {
			throw new IllegalArgumentException("Las dimensiones deben ser positivas: ancho=" + ancho + ", alto=" + alto);
		}		
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
	}
	
// ====================   METODOS ABSTRACTOS   ====================
	
	// Para actualizar el estado del objeto en cada frame	
	public abstract void actualizar(float delta);
	
	// Para renderizar el objeto en pantalla	
	public abstract void dibujar(SpriteBatch batch);
	
// ====================   METODOS CONCRETOS   ====================
	
	// Para obtener el rectangulo de colision del objeto.
	public Rectangle getBounds() {
		return new Rectangle(x, y, ancho, alto);
	}
	
	// Para verificar si el objeto esta destruido	
	public boolean estaDestruido() {
		return destruido;
	}
	
	// Para verificar si el objeto puede ser eliminado	
	public void destruir() {
		destruido = true;
	}
	
// ====================   GETTERS   ====================
	
	// Para obtener la posicion en el eje x	
	public float getX() {
		return x;
	}
	
	// Para obtener la posicion en el eje y	
	public float getY() {
		return y;
	}
	
	// Para obtener el ancho del objeto	
	public float getAncho() {
		return ancho;
	}
	
	//Para obtener el alto del objeto	
	public float getAlto() {
		return alto;
	}
	
// ====================   SETTERS PROTEGIDOS   ====================	
	
	// Permite modificar la posicion del objeto, solo por subclases durante su actualizacion, No invocable desde clases externas	
	protected void setPosicion(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	// Permite modificar las dimensiones del objeto, solo por subclases. No invocable desde clases externas
	protected void setTamanio(float ancho, float alto) {
		if (ancho <= 0 || alto <= 0) {
			throw new IllegalArgumentException("Las dimensiones deben ser positivas: ancho=" + ancho + ", alto=" + alto);
		}
		this.ancho = ancho;
		this.alto = alto;
	}
}