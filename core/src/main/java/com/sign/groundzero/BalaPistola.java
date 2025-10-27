package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;

//Clase Concreta: Representa un proyectil de pistola.
//Extiende Proyectil e implementa comportamiento específico.

public class BalaPistola extends Proyectil {
    private final int danio;
    
    //Constructor con valores por defecto
    
    public BalaPistola(float x, float y, float velocidadX, float velocidadY, Texture textura) {
        super(x, y, velocidadX, velocidadY, textura, 10, 10);
        this.danio = 10;
    }
    
    //Constructor con danio personalizado
    
    public BalaPistola(float x, float y, float velocidadX, float velocidadY, Texture textura, int danio) {
        super(x, y, velocidadX, velocidadY, textura, 10, 10);
        this.danio = danio;
    }
    
    //Maneja la colision con otros objetos usando polimorfismo (cualquier cosa que pueda recibir danio) para respetar LSP, verificando capacidad no un tipo concreto.
    
    @Override
    public void alColisionar(Colisionable otro) {
        if (otro instanceof Daniable) {
            Daniable objetivo = (Daniable) otro;
            objetivo.recibirDanio(danio);
            destruir();
        }
    }
    
    //Determina si se puede colisionar con otro objeto, solo verifica interfaces no tipos concretos, respetando LSP.
    
    @Override
    public boolean puedeColisionarCon(Colisionable otro) {
        return !estaDestruido() && otro instanceof Daniable;
    }
    
    //Obtiene el danio que hace este proyectil.
    
    public int getDanio() {
        return danio;
    }
}