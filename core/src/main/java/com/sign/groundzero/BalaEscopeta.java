package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;

//Clase Concreta: Representa un proyectil de escopeta.
//Extiende Proyectil e implementa comportamiento especifico..

public class BalaEscopeta extends Proyectil {
	private final int danio;
	
	//Constructor con valores por defecto.	
	public BalaEscopeta(float x, float y, float velocidadX, float velocidadY, Texture textura) {
		super(x, y, velocidadX, velocidadY, textura, 15, 15);
		this.danio = ConfiguracionJuego.DANIO_ESCOPETA; 
	}
	
	//Constructor para poner danio personalizado.
	public BalaEscopeta(float x, float y, float velocidadX, float velocidadY, Texture textura, int danio) {
		super(x, y, velocidadX, velocidadY, textura, 15, 15);
		this.danio = danio;
	}
	
	//Para manejar la colision con otros objetos usando polimorfismo, respetando LSP verificando capacidad no un tipo concreto.	
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
	
	//Obtiene el danio que hace este proyectil	
	public int getDanio() {
		return danio;
	}
}
