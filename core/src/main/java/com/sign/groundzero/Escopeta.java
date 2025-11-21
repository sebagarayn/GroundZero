package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import java.util.List;

//Clase Concreta: Para el comportamiento de disparo de una escopeta.
//Extiende Arma.

public class Escopeta extends Arma {
	private Texture texturaProyectil; //Textura para los perdigones, bala de escopeta.
	private Sound sonidoDisparo;
	
	//Constructor, se llama al constructor de arma pasando la cadencia.	
    public Escopeta(Texture texturaProyectil, Sound sonidoDisparo) {
        super(ConfiguracionJuego.CADENCIA_ESCOPETA);
		if (texturaProyectil == null) {
			throw new IllegalArgumentException("La textura del proyectil no puede ser null");
		}
		if (sonidoDisparo == null) {
			throw new IllegalArgumentException("El sonido de disparo no puede ser null");
		}
        this.texturaProyectil = texturaProyectil;
        this.sonidoDisparo = sonidoDisparo;
    }
	
	//Para implementar el algoritmo de disparo para la escopeta.	
    @Override
    protected void crearProyectiles(Superviviente portador, List<Proyectil> balasDelMundo) {
        float x = portador.getX() + portador.getAncho() / 2;
        float y = portador.getY() + portador.getAlto() / 2;
        Direccion dir = portador.getDireccionActual();
        
        float vxCentro = 0, vyCentro = 0;
        float vxSpread = 0, vySpread = 0;
        float velocidad = ConfiguracionJuego.VELOCIDAD_BALA_ESCOPETA;
        float spread = ConfiguracionJuego.SPREAD_ESCOPETA;
        
        switch(dir){
	    	case ARRIBA:
	    		vyCentro = velocidad;
	    		vxSpread = spread;
	    		break;
	    	case ABAJO:
	    		vyCentro = - velocidad;
	    		vxSpread = spread;
	    		break;
	    	case DERECHA:
	    		vxCentro = velocidad;
	    		vySpread = spread;
	    		break;
	    	case IZQUIERDA:
	    		vxCentro = - velocidad;
	    		vySpread = spread;
	    		break;
	    }
        
        BalaEscopeta balaCentro = new BalaEscopeta(x, y, vxCentro, vyCentro, texturaProyectil);
        BalaEscopeta balaIzquierda = new BalaEscopeta(x, y, vxCentro - vxSpread, vyCentro - vySpread, texturaProyectil);
        BalaEscopeta balaDerecha = new BalaEscopeta(x, y, vxCentro + vxSpread, vyCentro + vySpread, texturaProyectil);
        
        balasDelMundo.add(balaCentro);
        balasDelMundo.add(balaIzquierda);
        balasDelMundo.add(balaDerecha);
    }
    
    @Override
    protected void reproducirSonido() {
    	if(sonidoDisparo != null) {
    		sonidoDisparo.play(0.2f);
    	}
    }
}