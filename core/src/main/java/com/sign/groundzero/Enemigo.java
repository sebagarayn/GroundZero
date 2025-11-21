package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

//Clase Abstracta Enemigo: Para todos los enemigos del juego. Implementa el Patrón Strategy para el movimiento

public abstract class Enemigo extends Entidad {
	private final int valorPuntos;
	protected EstrategiaMovimiento estrategia;
	
	// Constructor	
	public Enemigo(float x, float y, float ancho, float alto, Texture textura, int salud, int valorPuntos) {	
		super(x, y, ancho, alto, textura, salud);
		this.valorPuntos = valorPuntos;
	}
	
	//Retorna los puntos que el enemigo otorga al ser destruido
	public int getValorPuntos() {
		return valorPuntos;
	}
	
	//Permite inyectar o cambiar la estrategia de movimiento del enemigo
	public void setEstrategia(EstrategiaMovimiento estrategia) {
		this.estrategia = estrategia;
	}
	
	@Override
	public void actualizar(float delta) {
		if(estrategia != null) {
			estrategia.mover(this, delta);
		}
		super.actualizar(delta);
		validarPosicionInicial();
	}
	
	@Override
	public boolean puedeColisionarCon(Colisionable otro) { // Define las entidades con las que los enemigos pueden interactuar
		return !estaDestruido();
	}
	
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
	
	protected void validarPosicionInicial() {
	    float newX = getX();
	    float newY = getY();
	    if (newX < 0) newX = 0;
	    if (newX + getAncho() > ConfiguracionJuego.WORLD_WIDTH) newX = ConfiguracionJuego.WORLD_WIDTH - getAncho();
	    if (newY < 0) newY = 0;
	    if (newY + getAlto() > ConfiguracionJuego.WORLD_HEIGHT) newY = ConfiguracionJuego.WORLD_HEIGHT - getAlto();
	    setPosicion(newX, newY);
	}
}