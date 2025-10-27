package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import java.util.List;

//Clase Concreta: Para el comportamiento de disparo de una escopeta.
//Extiende Arma.

public class Escopeta extends Arma {
	private static final float cadenciaEscopeta = 0.8f; //Define la cadencia de la escopeta, esto es disparos por segundo.
	private static final float velocidadProyectil = 7.0f; //Velocidad base de los perdigones.
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
        
        BalaEscopeta balaCentro = new BalaEscopeta( //Bala 1 creada.
            x, y, 0, velocidadProyectil, texturaProyectil
        );

        BalaEscopeta balaIzquierda = new BalaEscopeta( //Bala 2 creada.
            x, y, -1.0f, velocidadProyectil * 0.95f, texturaProyectil
        );

        BalaEscopeta balaDerecha = new BalaEscopeta( //Bala 3 creada.
            x, y, 1.0f, velocidadProyectil * 0.95f, texturaProyectil
        );

        balasDelMundo.add(balaCentro); //Se agregan las balas a la lista del mundo.
        balasDelMundo.add(balaIzquierda);
        balasDelMundo.add(balaDerecha);
        
        reiniciarCooldown(); //Se reinicia el cooldown con el metodo heredado por protected.
    }
}