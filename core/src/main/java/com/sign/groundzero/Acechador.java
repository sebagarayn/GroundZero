package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;

/* Clase Concreta: Representa un enemigo de tipo acechador.
 * Extiende Enemigo. */

//====================   CLASE CONCRETA ACECHADOR   =====================

public class Acechador extends Enemigo {
	private final float velocidadBase;
	private LimitesJuego limites;
	
	//Constructor Acechador

	public Acechador(float x, float y, float ancho, float alto, float velocidad, Texture textura, LimitesJuego limites) {
		
		super(x, y, ancho, alto, textura, 20, 25);
		if (limites == null) {
            throw new IllegalArgumentException("Los límites no pueden ser null");
        }
        if (velocidad <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser positiva: " + velocidad);
        }
		this.velocidadBase = velocidad;
		this.limites = limites;
		validarPosicionInicial(limites); //Validar posicion inicial dentro de pantalla.
		getSprite().setPosition(getX(), getY());
	}
	
	//Actualizar el estado del acechador
	
    @Override
    public void actualizar(float delta) {
    	
        comportamientoMovimiento(delta);
        super.actualizar(delta);
    }
    
    //Implementa el movimiento especifico, en este caso un movimiento erratico rebotando en bordes.
    
    @Override
    public void comportamientoMovimiento(float delta) {
    	
        float vx = getVelocidadX();
        float vy = getVelocidadY();
        
        if (vx == 0 && vy == 0) { 
        	vx = velocidadBase;
        	vy = velocidadBase;
        }

        float currentX = getX();
        float currentY = getY();     
        float currentAncho = getAncho();
        float currentAlto = getAlto();
        
        float screenWidth = limites.getWorldWidth();
        float screenHeight = limites.getWorldHeight();

        if ((currentX <= 0 && vx < 0) || (currentX + currentAncho >= screenWidth && vx > 0)) {
            vx *= -1;
        }
        if ((currentY <= 0 && vy < 0) || (currentY + currentAlto >= screenHeight && vy > 0)) {
            vy *= -1;
        }

        setVelocidad(vx, vy);
    }
    
    //Para manejar las colisiones con otros objetos usando polimorfismo, respeta LSP al usar interfaces y no clases concretas.
    
    @Override
    public void alColisionar(Colisionable otro) {
    	
        if (otro instanceof Movible && otro instanceof Enemigo) { // Rebote con otros enemigos (Movible), que es una regla especifica del juego en si.
            manejarReboteConEnemigo((Movible) otro);
        }
    }
    
    //Para manejar el rebote con otro enemigo usando interfaces
    
    private void manejarReboteConEnemigo(Movible otroMovible) {
    	
        float vx = getVelocidadX();
        float vy = getVelocidadY();        
        setVelocidad(-vx, -vy);
    }
    
    //Para determinar si puede colisionar con otro objeto, respetando LSP al verificar capacidades, no tipos especificos.
    
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {
    	
        return !estaDestruido() && (otro instanceof Enemigo || otro instanceof Daniable); // Puede colisionar con otros enemigos o con entidades dañables, que es una regla del juego.
    }
}
