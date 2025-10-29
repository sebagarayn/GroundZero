package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

/* Clase abstracta para entidades vivas del juego (Jugador y Enemigos)
 * extiende ObjetoJuego e implementa las interfaces necesarias.*/

// ====================   CLASE ABSTRACTA ENTIDAD   ====================

public abstract class Entidad extends ObjetoJuego implements Colisionable, Movible, Daniable {
	
	private Sprite sprite;
	private float velocidadX = 0;
	private float velocidadY = 0;
	private int salud;
	private int saludMaxima;
	private boolean isHit = false;
	private float hitTimer = 0f;
	private static final float HIT_DURATION = 0.15f;
	
	// Constructor Entidad
	
	public Entidad(float x, float y, float ancho, float alto, Texture textura, int salud) {
		
		super(x, y, ancho, alto);
		if(textura == null) {
			throw new IllegalArgumentException("La textura no puede ser null");
		}
		if(salud <= 0) {
			throw new IllegalArgumentException("La salud debe ser positiva: " + salud);
		}
		this.sprite = new Sprite(textura);
		this.sprite.setBounds(x, y, ancho, alto);
		this.salud = salud;
		this.saludMaxima = salud;
	}
	
// =====================   IMPLEMENTACION OBJETOJUEGO   ======================
	
	// Para actualizar el estado de la entidad. Gestiona el movimiento, sincroniza la posición del sprite, y controla el temporizador del efecto visual de daño
	
	@Override
	public void actualizar(float delta) { //Delta: Tiempo en segundos desde utlimo frame
		
		mover(delta);
		sprite.setPosition(getX(), getY());	
		if (isHit) {
	        hitTimer -= delta; // Restar el tiempo pasado
	        if (hitTimer <= 0) {
	            isHit = false;
	            sprite.setColor(Color.WHITE); // Volver al color normal
	        }
	    }
	}
	
	// Para renderizar la entidad con efectos visuales de danio (Rojo) si corresponde
	
	@Override
	public void dibujar(SpriteBatch batch) {
		
	    if (isHit) {
	        sprite.setColor(Color.RED); // Aplica rojo si está golpeado
	    } else {
	        sprite.setColor(Color.WHITE); 
	    }
	    sprite.draw(batch);
	}
	
// ======================   IMPLEMENTACION DE MOVIBLE   ======================
	
	// Aplica movimiento basado en la velocidad actual, por eso delta
	
	@Override
	public void mover(float delta) {
		
		setPosicion(getX() + velocidadX * delta, getY() + velocidadY * delta);
	}
	
	// Para establecer la velocidad de movimiento en ambos ejes
	
	@Override
	public void setVelocidad(float vx, float vy) {
		
		this.velocidadX = vx;
		this.velocidadY = vy;
	}
	
	@Override
	public float getVelocidadX() {
		
		return velocidadX;
	}
	
	@Override
	public float getVelocidadY() {
		
		return velocidadY;
	}
	
// ======================   IMPLEMENTACION DE DANIABLE   =====================
	
	// Aplica danio a la entidad en caso de que sea posible, ademas verifica condiciones de vulnearbilidad antes, activa efectos y destruye entidades
	
	@Override
	public void recibirDanio(int danio) {
		
		if (estaMuerto() || estaDestruido() || esInvulnerableTemporalmente()) {
	         return;
	    }
		if(danio < 0) {
			throw new IllegalArgumentException("El daño no puede ser negativo: " + danio);
		}
		salud -= danio;		
		isHit = true;
		hitTimer = HIT_DURATION;		
		if(salud <= 0) {
			salud = 0;
			destruir();
		}
	}
	
	// Para detemirnar si una entidad no puede recibir danio en el momento
	
	protected boolean esInvulnerableTemporalmente() {
		
		return isHit;
	}
	
	@Override
	public int getSalud() {
		
		return salud;
	}
	
	@Override
	public boolean estaMuerto() {
		
		return salud <= 0;
	}
	
	// Para restaurar vida sin exceder el limite
	
	@Override
	public void curar(int cantidad) {
		
		if(cantidad < 0) {
			throw new IllegalArgumentException("La cantidad de curación no puede ser negativa: " + cantidad);
		}
		salud = Math.min(salud + cantidad, saludMaxima);
	}
	
// =========================   IMPLEMENTACION DE COLISIONABLE   ==============
	
	// Para manejar la logica cuando la entidad colisiona con otro objeto
	
	@Override
	public void alColisionar(Colisionable otro) {
		
		//Implementacion por defecto, para poder sobrescribir y tener comportamientos especificos
	}
	
	// Determina si esta entidad puede colisionar con otro objeto
	
	@Override
	public boolean puedeColisionarCon(Colisionable otro) {
		
		return !estaDestruido();
	}
	
//=========================   GETTERS/SETTERS PROTEGIDOS   =======================
	
	// Para obtener el sprite asociada a la entidad
	
	protected Sprite getSprite() {
		
		return sprite;
	}
	
	// Para establecer la velocidad en el eje x
	
	protected void setVelocidadX(float vx) {
		
		this.velocidadX = vx;
	}
	
	// Para establecer la velocidad en el eje y
	
	protected void setVelocidadY(float vy) {
		
		this.velocidadY = vy;
	}
	
	// Para obtener la salud maxima de la entidad
	
	protected int getSaludMaxima() {
		
		return saludMaxima;
	}
	
	// Para establecer directamente la salud de la entidad
	
	protected void setSalud(int salud) {
		
		this.salud = Math.max(0,  Math.min(salud, saludMaxima));
	}
}
