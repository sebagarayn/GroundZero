package com.sign.groundzero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Clase que representa un enemigo zombie
 * Extiende Enemigo e implementa comportamiento de rebote
 */
public class Zombie extends Enemigo {
    
    public Zombie(float x, float y, int ancho, int alto, float velocidadX, float velocidadY, 
                  Texture textura) {
        super(x, y, ancho, alto, textura, 10, 10); //Primer 10 es la vida
        
        // Validar que no quede fuera de pantalla
        if (x - ancho < 0) {
            this.x = x + ancho;
        }
        if (x + ancho > Gdx.graphics.getWidth()) {
            this.x = x - ancho;
        }
        if (y - alto < 0) {
            this.y = y + alto;
        }
        if (y + alto > Gdx.graphics.getHeight()) {
            this.y = y - alto;
        }
        
        setVelocidad(velocidadX, velocidadY);
        getSprite().setPosition(this.x, this.y);
    }
    
    @Override
    public void actualizar(float delta) {
        comportamientoMovimiento(delta);
        super.actualizar(delta);
    }
    
    @Override
    public void comportamientoMovimiento(float delta) {
        // Rebotar en los bordes de la pantalla
        float vx = getVelocidadX();
        float vy = getVelocidadY();
        
        if (x + vx < 0 || x + vx + ancho > Gdx.graphics.getWidth()) {
            vx *= -1;
        }
        if (y + vy < 0 || y + vy + alto > Gdx.graphics.getHeight()) {
            vy *= -1;
        }
        
        setVelocidad(vx, vy);
    }
    
    @Override
    public void alColisionar(Colisionable otro) {
        if (otro instanceof Zombie) {
            Zombie otroZombie = (Zombie) otro;
            
            // Física de rebote entre zombies
            float vx = getVelocidadX();
            float vy = getVelocidadY();
            float otroVx = otroZombie.getVelocidadX();
            float otroVy = otroZombie.getVelocidadY();
            
            if (vx == 0) vx += otroVx / 2;
            if (otroVx == 0) otroVx += vx / 2;
            
            if (vy == 0) vy += otroVy / 2;
            if (otroVy == 0) otroVy += vy / 2;
            
            setVelocidad(-vx, -vy);
            otroZombie.setVelocidad(-otroVx, -otroVy);
        }
        else if (otro instanceof BalaPistola) {
            BalaPistola bala = (BalaPistola) otro;
            recibirDanio(bala.getDanio());
        }
    }
    
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {
        return !estaDestruido() && (otro instanceof Zombie || otro instanceof Superviviente);
    }
}