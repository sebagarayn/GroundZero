package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import java.util.List;

//Clase Concreta: Para el comportamiento de disparo de una pistola.
//Extiende Arma.

public class Pistola extends Arma {
	private Texture texturaProyectil; //Textura para la bala de pistola;
	private Sound sonidoDisparo;
	
	//Constructor, se llama al constructor de Arma, pasando la cadencia.	
	public Pistola(Texture texturaProyectil, Sound sonidoDisparo) {
		super(ConfiguracionJuego.CADENCIA_PISTOLA);
		this.texturaProyectil = texturaProyectil;
		this.sonidoDisparo = sonidoDisparo;
	}
	
	//Para implementar algoritmos de disparo para la pistola.	
	@Override
    public void disparar(Superviviente portador, List<Proyectil> balasDelMundo) {
        if (!puedeDisparar()) {
            return;
        }
        float x = portador.getX() + portador.getAncho() / 2; //Centrada
        float y = portador.getY() + portador.getAlto() / 2; //Centrada
        Direccion dir = portador.getDireccionActual();
        float vx = 0;
        float vy = 0;
        float velocidad = ConfiguracionJuego.VELOCIDAD_BALA_PISTOLA;
        switch(dir) {
            case ARRIBA:
                vy = velocidad;
                break;
            case ABAJO:
                vy = -velocidad;
                break;
            case DERECHA:
                vx = velocidad;
                break;
            case IZQUIERDA:
                vx = -velocidad;
                break;
        }
        BalaPistola bala = new BalaPistola(x, y, vx, vy, texturaProyectil);
        balasDelMundo.add(bala);
        sonidoDisparo.play(0.2f);
        reiniciarCooldown();
    }
}