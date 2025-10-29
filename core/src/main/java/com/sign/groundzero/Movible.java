package com.sign.groundzero;

/*INTERFAZ: Para objetos que pueden moverse.*/

public interface Movible {
	void setVelocidad(float vx, float vy);
	float getVelocidadX();
	float getVelocidadY();
	void mover(float delta);
}
