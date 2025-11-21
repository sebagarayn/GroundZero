package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;

//Clase que representa un enemigo zombie. Extiende Enemigo y utiliza la estrategia MovimientoPersecucion

public class Zombie extends Enemigo {    
	// Constructor 
	
	public Zombie(float x, float y, float ancho, float alto, Texture textura, Objetivo objetivo, float velocidad) {
		super(x, y, ancho, alto, textura, 10, 10);
    	if (objetivo == null) {
    		throw new IllegalArgumentException("El objetivo no puede ser null");
    	}
    	this.setEstrategia(new MovimientoPersecucion(objetivo, velocidad));
	}
    
    // Maneja colision con otros objetos  
    @Override
    public void alColisionar(Colisionable otro) {   	
    	//Regla de colisión: Los enemigos rebotan solo entre sí
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
    
    // Determina si puede colisionar con otro objeto (respeta LSP al verificar capacidades)  
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {   	
    	return !estaDestruido() && (otro instanceof Enemigo || otro instanceof Daniable);
    }
}