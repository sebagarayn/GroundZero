package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;

/*CLASE ABSTRACTA: Para todos los enemigos del juego, extiene Entidad y agrega
 *comportamiento especifo de enemigos*/

public abstract class Enemigo extends Entidad {
	public int valorPuntos;
	
	public Enemigo(float x, float y, float ancho, float alto, Texture textura, int salud, int valorPuntos) {
		super(x, y, ancho, alto, textura, salud);
		this.valorPuntos = valorPuntos;
	}
	
	//Para obtener puntos que el enemigo da al destruirlo
	public int getValorPuntos() {
		return valorPuntos;
	}
	
	@Override
	public boolean puedeColisionarCon(Colisionable otro) {
		return !estaDestruido();
	}
	
	//Comportamiento especifico de movimiento del enemigo
	//Las sublclase pueden sobrescribir este metodo
	public abstract void comportamientoMovimiento(float delta);
}
