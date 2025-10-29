package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;

 /* Clase que representa un enemigo zombie
  * Extiende Enemigo e implementa comportamiento de rebote.*/

//  ====================   CLASE CONCRETA ZOMBIE   ====================

public class Zombie extends Enemigo {
	private LimitesJuego limites;
	private Objetivo objetivo;
	private float velocidadPersecucion;
    
	// Constructor Zombie
	
    public Zombie(float x, float y, float ancho, float alto, Texture textura, LimitesJuego limites, Objetivo objetivo, float velocidadPersecucion) {
    	
	  super(x, y, ancho, alto, textura, 10, 10);
	  if (limites == null) {
          throw new IllegalArgumentException("Los límites no pueden ser null");
      }
      if (objetivo == null) {
          throw new IllegalArgumentException("El objetivo no puede ser null");
      }
	  this.limites = limites;
	  this.objetivo = objetivo;
	  this.velocidadPersecucion = velocidadPersecucion;
	  
	  validarPosicionInicial(limites);
	  getSprite().setPosition(getX(), getY());
	}
    
    // Para actualizar el estado del zombie
    
    @Override
    public void actualizar(float delta) {
    	
        comportamientoMovimiento(delta);
        super.actualizar(delta);
    }
    
    // Para implementar el comportamiento de persecucion al objetivo, calcula direccion y ajusta velocidad hacia el objetivo
    
    @Override
    public void comportamientoMovimiento(float delta) {

        if (objetivo == null || objetivo.estaDestruido()) {
            return; 
        }       
        float dx = objetivo.getX() - getX(); // Vector direccion hacia el objetivo
        float dy = objetivo.getY() - getY();       
        float distancia = (float) Math.sqrt(dx * dx + dy * dy); //Calcula distancia    
        if (distancia > 0) { // Normaliza y aplica distancia
            float vx = (dx / distancia) * velocidadPersecucion;
            float vy = (dy / distancia) * velocidadPersecucion;
            setVelocidad(vx, vy);
        }
        validarLimitesPantalla();
    }
    
    // Asegura que el zombie permaneza en el mapa
    
    private void validarLimitesPantalla() {
    	
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
    
    // Maneja colision con otros objetos
    
    @Override
    public void alColisionar(Colisionable otro) {
    	
    	// Implementa regla del juego: "enemigos rebotan solo entre sí, previene rebote no deseado con jugador u otras entidades móviles
        if (otro instanceof Enemigo) {
            manejarReboteConEnemigo((Movible) otro);
        }
    }
    
    // Con enemigos implementa un rebote simple inviritiendo velocidad
    
    private void manejarReboteConEnemigo(Movible otroMovible) {
    	
        float vx = getVelocidadX();
        float vy = getVelocidadY();
        setVelocidad(-vx, -vy);
    }
    
    // Determina si puede colisionar con otro objeto
    
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {
    	
    	return !estaDestruido() && (otro instanceof Enemigo || otro instanceof Daniable);
    }
}