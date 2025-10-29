package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/*CLASE ABSTRACTA: Para todos los enemigos del juego, extiene Entidad y agrega
 *comportamiento especifo de enemigos.*/

// ====================   CLASE ABSTRACTA ENEMIGO   ====================

public abstract class Enemigo extends Entidad {
	private final int valorPuntos;
	
	// Constructor Enemigo
	
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
	
	//Para el rango de ataque de los enemigos
	
	@Override
	public Rectangle getBounds() {
		float scale = ConfiguracionJuego.ESCALA_HITBOX_ENEMIGO;
		float originalWidth = getAncho(); // Obtener ancho de ObjetoJuego
		float originalHeight = getAlto(); // Obtener alto de ObjetoJuego
		float originalX = getX(); // Obtener X de ObjetoJuego
		float originalY = getY(); // Obtener Y de ObjetoJuego
		
		float hitboxWidth = originalWidth * scale;
        float hitboxHeight = originalHeight * scale;
        
        float hitboxX = originalX + (originalWidth - hitboxWidth) / 2f; // Calcular la nueva posición X,Y para que la hitbox quede centrada
        float hitboxY = originalY + (originalHeight - hitboxHeight) / 2f;
        
        return new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
	}
	
	protected void validarPosicionInicial(LimitesJuego limites) {
	    float newX = getX();
	    float newY = getY();
	    float ancho = getAncho();
	    float alto = getAlto();

	    float worldWidth = limites.getWorldWidth();
	    float worldHeight = limites.getWorldHeight();

	    if (newX < 0) newX = 0;
	    if (newX + ancho > worldWidth) newX = worldWidth - ancho;
	    if (newY < 0) newY = 0;
	    if (newY + alto > worldHeight) newY = worldHeight - alto;

	    setPosicion(newX, newY);
	}
}