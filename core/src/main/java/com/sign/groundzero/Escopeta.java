package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import java.util.List;

//Clase Concreta: Para el comportamiento de disparo de una escopeta.
//Extiende Arma.

public class Escopeta extends Arma {
	private static final float cadenciaEscopeta = 0.8f; //Define la cadencia de la escopeta, esto es disparos por segundo.
	private static final float velocidadProyectil = 7.0f; //Velocidad base de los perdigones.
	private static final float spread = 1.0f; // Amplitud de la dispersión
	private Texture texturaProyectil; //Textura para los perdigones, bala de escopeta.
	
	//Constructor, se llama al constructor de arma pasando la cadencia.
	
	public Escopeta(Texture texturaProyectil) {
		super(cadenciaEscopeta);
		this.texturaProyectil = texturaProyectil;
	}
	
	//Para implementar el algoritmo de disparo para la escopeta.
	
	@Override
	public void disparar(Superviviente portador, List<Proyectil> balasDelMundo) {
		
        if (!puedeDisparar()) { //Usa el método heredado de Arma para verificar el cooldown.
            return;
        }
        
        float x = portador.getX();
        float y = portador.getY();
        
        Superviviente.Direccion dir = portador.getDireccionActual();
        
        float vx_centro = 0, vy_centro = 0;
        float vx_spread = 0, vy_spread = 0;
        
        switch(dir) {
        	case ARRIBA:
        		vy_centro = velocidadProyectil;
        		vx_spread = spread; // Dispersión horizontal
        		break;
        	case ABAJO:
        		vy_centro = -velocidadProyectil;
        		vx_spread = spread; // Dispersión horizontal
        		break;
        	case DERECHA:
        		vx_centro = velocidadProyectil;
        		vy_spread = spread; // Dispersión vertical
        		break;
        	case IZQUIERDA:
        		vx_centro = -velocidadProyectil;
        		vy_spread = spread; // Dispersión vertical
        		break;
    }
        
        BalaEscopeta balaCentro = new BalaEscopeta(x, y, vx_centro, vy_centro, texturaProyectil);

        BalaEscopeta balaIzquierda = new BalaEscopeta(x, y, vx_centro - vx_spread, vy_centro - vy_spread, texturaProyectil);

        BalaEscopeta balaDerecha = new BalaEscopeta(x, y, vx_centro + vx_spread, vy_centro + vy_spread, texturaProyectil
            );

        balasDelMundo.add(balaCentro); //Se agregan las balas a la lista del mundo.
        balasDelMundo.add(balaIzquierda);
        balasDelMundo.add(balaDerecha);
        
        reiniciarCooldown(); //Se reinicia el cooldown con el metodo heredado por protected.
    }
}