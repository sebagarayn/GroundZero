package com.sign.groundzero;

/*INTERFAZ: Para objetos que pueden recibir daño*/

public interface Daniable {
	void recibirDanio(int danio);
	int getSalud();
	boolean estaMuerto();
	void curar(int cantidad);
}
