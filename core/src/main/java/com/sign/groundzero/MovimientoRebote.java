package com.sign.groundzero;

public class MovimientoRebote implements EstrategiaMovimiento {
	private LimitesJuego limites;
	private float velocidadBase;
	
	public MovimientoRebote(LimitesJuego limites, float velocidadBase) {
		if (limites == null) {
    		throw new IllegalArgumentException("Los limites de juego no pueden ser null");
    	}
		if (velocidadBase <= 0) {
			throw new IllegalArgumentException("La velocidad de rebote debe ser positiva");
		}
		this.limites = limites;
		this.velocidadBase = velocidadBase;
	}
	
	@Override
	public void mover(Enemigo enemigo, float delta) {
		float vx = enemigo.getVelocidadX();
		float vy = enemigo.getVelocidadY();
		
		//Si es que esta quieto, hay que darle velocidad, como al inicio
		if(vx == 0 && vy == 0) {
			vx = velocidadBase;
			vy = velocidadBase;
		}
		
		float currentX = enemigo.getX();
		float currentY = enemigo.getY();
		float ancho = enemigo.getAncho();
		float alto = enemigo.getAlto();
		float screenWidth = limites.getWorldWidth();
		float screenHeight = limites.getWorldHeight();
		
		if((currentX <= 0 && vx < 0) || (currentX + ancho >= screenWidth && vx > 0)) {
			vx *= -1;
		}
		if((currentY <= 0 && vy < 0) || (currentY + alto >= screenHeight && vy > 0)) {
			vy *= -1;
		}
		enemigo.setVelocidad(vx,  vy);
	}
}