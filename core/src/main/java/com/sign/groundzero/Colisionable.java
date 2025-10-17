package com.sign.groundzero;
import com.badlogic.gdx.math.Rectangle; //Para detectar colisiones

/*INTERFAZ: Para objetos que pueden colisionar con otros*/

public interface Colisionable {
	Rectangle getBounds();
	void alColisionar(Colisionable otro);
	boolean puedeColisionarCon(Colisionable otro);
}