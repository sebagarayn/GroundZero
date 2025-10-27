package com.sign.groundzero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Clase que representa un enemigo zombie
 * Extiende Enemigo e implementa comportamiento de rebote
 */
public class Zombie extends Enemigo {
    
    public Zombie(float x, float y, int ancho, int alto, float velocidadX, float velocidadY, Texture textura) {
        super(x, y, ancho, alto, textura, 10, 10); //Primer 10 es la vida
        
        validarPosicionInicial(ancho, alto);
        getSprite().setPosition(getX(), getY());
        setVelocidad(velocidadX, velocidadY);
    }
    
    private void validarPosicionInicial(int ancho, int alto) {
    	float newX = getX();
        float newY = getY();
        
        if (newX < 0) newX = 0;
        if (newX + ancho > Gdx.graphics.getWidth()) newX = Gdx.graphics.getWidth() - ancho;
        if (newY < 0) newY = 0;
        if (newY + alto > Gdx.graphics.getHeight()) newY = Gdx.graphics.getHeight() - alto;
        
        setPosicion(newX, newY);
    }
    
    @Override
    public void actualizar(float delta) {
        comportamientoMovimiento(delta);
        super.actualizar(delta);
    }
    
    @Override
    public void comportamientoMovimiento(float delta) {
        float vx = getVelocidadX();
        float vy = getVelocidadY();
        
        float currentX = getX();
        float currentY = getY();
        float currentAncho = getAncho();
        float currentAlto = getAlto();
        
        float screenWidth = PantallaJuego.getWorldWidth();
        float screenHeight = PantallaJuego.getWorldHeight();

		if ((currentX <= 0 && vx < 0) || (currentX + currentAncho >= screenWidth && vx > 0)) {
            vx *= -1;
        }
        if ((currentY <= 0 && vy < 0) || (currentY + currentAlto >= screenHeight && vy > 0)) {
            vy *= -1;
        }

        setVelocidad(vx, vy);
    }
    
    @Override
    public void alColisionar(Colisionable otro) {
        if (otro instanceof Enemigo) {
            manejarReboteConEnemigo((Movible) otro);
        }
    }
    
    private void manejarReboteConEnemigo(Movible otroMovible) {
        float vx = getVelocidadX();
        float vy = getVelocidadY();
        
        setVelocidad(-vx, -vy);
    }
    
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {
    	return !estaDestruido() && (otro instanceof Enemigo || otro instanceof Daniable);
    }
}