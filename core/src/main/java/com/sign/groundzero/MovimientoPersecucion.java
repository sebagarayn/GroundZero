package com.sign.groundzero;

public class MovimientoPersecucion implements EstrategiaMovimiento {
	private Objetivo objetivo;
	private float velocidadPersecucion;

	public MovimientoPersecucion(Objetivo objetivo, float velocidadPersecucion) {
		this.objetivo = objetivo;
		this.velocidadPersecucion = velocidadPersecucion;
	}
	
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
