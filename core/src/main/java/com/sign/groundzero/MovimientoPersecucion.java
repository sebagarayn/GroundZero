package com.sign.groundzero;

//Implementación concreta de la estrategia de movimiento de persecución

public class MovimientoPersecucion implements EstrategiaMovimiento {
	private Objetivo objetivo;
	private float velocidadPersecucion;

	public MovimientoPersecucion(Objetivo objetivo, float velocidadPersecucion) {
		if(objetivo == null) {
			throw new IllegalArgumentException("El objetivo para la persecución no puede ser null");
		}
		if(velocidadPersecucion <= 0) {
			throw new IllegalArgumentException("La velocidad de persecución debe ser positiva");	
		}
		this.objetivo = objetivo;
		this.velocidadPersecucion = velocidadPersecucion;
	}
	
	// Implementación de la interfaz Strategy, define cómo mover al enemigo
	@Override
	public void mover(Enemigo enemigo, float delta) {
		if(objetivo == null || objetivo.estaDestruido()) {
			return;
		}
		
		float dx = objetivo.getX() - enemigo.getX();
		float dy = objetivo.getY() - enemigo.getY();
		float distancia = (float)Math.sqrt(dx * dx + dy * dy);
		
		if(distancia > 0) {
			float vx = (dx / distancia) * velocidadPersecucion;
			float vy = (dy / distancia) * velocidadPersecucion;
			enemigo.setVelocidad(vx,  vy);
		}
	}
}
