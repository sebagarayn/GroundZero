package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;

/**********************************************************************
 Clase que representa un proyectil básico (bala)
 Extiende Proyectil e implementa comportamiento específico
**********************************************************************/

public class BalaPistola extends Proyectil {
    private int danio;
    
    public BalaPistola(float x, float y, float velocidadX, float velocidadY, Texture textura) {
        super(x, y, velocidadX, velocidadY, textura, 10, 10);
        this.danio = 10;
    }
    
    public BalaPistola(float x, float y, float velocidadX, float velocidadY, 
                  Texture textura, int danio) {
        super(x, y, velocidadX, velocidadY, textura, 10, 10);
        this.danio = danio;
    }
    
    @Override
    public void alColisionar(Colisionable otro) {
        if (otro instanceof Enemigo) {
            Enemigo enemigo = (Enemigo) otro;
            enemigo.recibirDanio(danio);
            destruir();
        }
    }
    
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {
        return !estaDestruido() && otro instanceof Enemigo;
    }
    
    public int getDanio() {
        return danio;
    }
}