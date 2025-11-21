package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;

//Clase Concreta Acechador: Extiende Enemigo y utiliza la estrategia MovimientoRebote

public class Acechador extends Enemigo {

	//Constructor
	public Acechador(float x, float y, float ancho, float alto, float velocidad, Texture textura, LimitesJuego limites) {		
		super(x, y, ancho, alto, textura, 20, 25);
		if (limites == null) {
            throw new IllegalArgumentException("Los límites no pueden ser null");
        }
        if (velocidad <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser positiva: " + velocidad);
        }
        this.setEstrategia(new MovimientoRebote(limites, velocidad));
        validarPosicionInicial();
	}
    
    //Maneja colisiones con otros objetos (respeta LSP)
    @Override
    public void alColisionar(Colisionable otro) {  	
        if (otro instanceof Movible && otro instanceof Enemigo) { // Implementa regla del juego: "enemigos rebotan solo entre sí, previene rebote no deseado con jugador u otras entidades móviles
            manejarReboteConEnemigo((Movible) otro);
        }
    }
    
    //Determina si puede colisionar con otro objeto (respeta LSP) 
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {   	
        return !estaDestruido() && (otro instanceof Enemigo || otro instanceof Daniable); // Puede colisionar con otros enemigos o con entidades dañables, que es una regla del juego.
    }
    
    //Maneja el rebote con otro enemigo.
    private void manejarReboteConEnemigo(Movible otroMovible) {   	
        float vx = getVelocidadX();
        float vy = getVelocidadY();        
        setVelocidad(-vx, -vy);
    }
}