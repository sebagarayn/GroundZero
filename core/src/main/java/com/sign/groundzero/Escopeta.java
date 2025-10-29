package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import java.util.List;

//Clase Concreta: Para el comportamiento de disparo de una escopeta.
//Extiende Arma..

public class Escopeta extends Arma {
	private Texture texturaProyectil; //Textura para los perdigones, bala de escopeta.
	private Sound sonidoDisparo;
	
	//Constructor, se llama al constructor de arma pasando la cadencia.
	
    public Escopeta(Texture texturaProyectil, Sound sonidoDisparo) {
        super(ConfiguracionJuego.CADENCIA_ESCOPETA);
        this.texturaProyectil = texturaProyectil;
        this.sonidoDisparo = sonidoDisparo;
    }
	
	//Para implementar el algoritmo de disparo para la escopeta.
	
    @Override
    public void disparar(Superviviente portador, List<Proyectil> balasDelMundo) {
        if (!puedeDisparar()) {
            return;
        }

        float x = portador.getX();
        float y = portador.getY();
        Direccion dir = portador.getDireccionActual();

        float vx_centro = 0, vy_centro = 0;
        float vx_spread = 0, vy_spread = 0;
        float velocidad = ConfiguracionJuego.VELOCIDAD_BALA_ESCOPETA;
        float spread = ConfiguracionJuego.SPREAD_ESCOPETA;

        switch(dir) {
            case ARRIBA:
                vy_centro = velocidad;
                vx_spread = spread;
                break;
            case ABAJO:
                vy_centro = -velocidad;
                vx_spread = spread;
                break;
            case DERECHA:
                vx_centro = velocidad;
                vy_spread = spread;
                break;
            case IZQUIERDA:
                vx_centro = -velocidad;
                vy_spread = spread;
                break;
        }
        
        BalaEscopeta balaCentro = new BalaEscopeta(x, y, vx_centro, vy_centro, texturaProyectil);
        BalaEscopeta balaIzquierda = new BalaEscopeta(x, y, vx_centro - vx_spread, vy_centro - vy_spread, texturaProyectil);
        BalaEscopeta balaDerecha = new BalaEscopeta(x, y, vx_centro + vx_spread, vy_centro + vy_spread, texturaProyectil);

        balasDelMundo.add(balaCentro);
        balasDelMundo.add(balaIzquierda);
        balasDelMundo.add(balaDerecha);
        
        sonidoDisparo.play(0.2f);
        reiniciarCooldown();
    }
}