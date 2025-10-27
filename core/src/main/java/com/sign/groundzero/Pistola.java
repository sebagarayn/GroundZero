package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import java.util.List;

//Clase Concreta: Para el comportamiento de disparo de una pistola.
//Extiende Arma.

public class Pistola extends Arma {
	private static final float cadenciaPistola = 2.5f; //Se define la cadencia de la pistola, 2.5 disparos por segundo.
	private static final float velocidadProyectil = 8.0f; //Velocidad base de la pistola.
	private Texture texturaProyectil; //Textura para la bala de pistola;
	
	//Constructor, se llama al constructor de Arma, pasando la cadencia.
	
	public Pistola(Texture texturaProyectil) {
		super(cadenciaPistola);
		this.texturaProyectil = texturaProyectil;
	}
	
	//Para implementar algoritmos de disparo para la pistola.
	
	@Override
	public void disparar(Superviviente portador, List<Proyectil> balasDelMundo) {
        
        if (!puedeDisparar()) { //Usa el método heredado de Arma para verificar el cooldown.
            return;
        }

        float x = portador.getX();
        float y = portador.getY();
        
        BalaPistola bala = new BalaPistola(x, y, 0, velocidadProyectil, texturaProyectil); //Creacion de la bala.

        balasDelMundo.add(bala); //Se agrega bala a la lista del mundo.
        
        reiniciarCooldown(); //Se reinicia el cooldown con el metodo heredado por protected.
    }
}