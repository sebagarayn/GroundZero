package com.sign.groundzero;

//Interfaz: Define el contrato para las estrategias de movimiento.
public interface EstrategiaMovimiento {
	void mover(Enemigo enemigo, float delta);
}