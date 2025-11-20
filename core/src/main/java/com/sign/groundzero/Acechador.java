package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;

//Clase Concreta Acechador: Representa un enemigo de tipo acechador.
//Extiende Enemigo.

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
    
    //Para manejar las colisiones con otros objetos usando polimorfismo, respeta LSP al usar interfaces y no clases concretas.  
    @Override
    public void alColisionar(Colisionable otro) {  	
        if (otro instanceof Movible && otro instanceof Enemigo) { // Implementa regla del juego: "enemigos rebotan solo entre sí, previene rebote no deseado con jugador u otras entidades móviles
            manejarReboteConEnemigo((Movible) otro);
        }
    }
    
    //Para determinar si puede colisionar con otro objeto, respetando LSP al verificar capacidades, no tipos especificos.   
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {   	
        return !estaDestruido() && (otro instanceof Enemigo || otro instanceof Daniable); // Puede colisionar con otros enemigos o con entidades dañables, que es una regla del juego.
    }
    
    //Para manejar el rebote con otro enemigo usando interfaces.
    private void manejarReboteConEnemigo(Movible otroMovible) {   	
        float vx = getVelocidadX();
        float vy = getVelocidadY();        
        setVelocidad(-vx, -vy);
    }
}