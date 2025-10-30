package com.sign.groundzero;

/* INTERFAZ para representar una entidad que puede ser objetivo de enemigos. Las entidades que la implementen puede ser perseguida por enemigos que requieran de un objetivo*/

public interface Objetivo {	
	float getX(); //Obtiene la posición actual en el eje X del objetivo.
	float getY(); //Obtiene la posición actual en el eje Y del objetivo
	boolean estaDestruido(); //Verifica si el objetivo ha sido destruido o eliminado del juego
}
