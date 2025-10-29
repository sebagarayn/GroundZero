package com.sign.groundzero;

/*Clase Concreta: Declarada como public final porque ninguna clase hereda 
 *de ella, solo es para contener constantes estaticas..*/

//====================   CLASE CONFIGURACIONJUEGO   ====================

public final class ConfiguracionJuego {
	
	// ====================   MUNDO   ====================
	
	public static final float WORLD_WIDTH = 1600f;
	public static final float WORLD_HEIGHT = 900f;
	
	// ====================   JUEGO - FLUJO  ====================
	
    public static final int RONDA_INICIAL = 1;
    public static final int VIDAS_INICIALES = 3;
    public static final int SCORE_INICIAL = 0;
    public static final int CANTIDAD_ZOMBIES_INICIAL = 10;
    
    // ====================   ENEMIGOS   ====================
    
    public static final int ANCHO_ZOMBIE = 120;
    public static final float ASPECT_RATIO_ZOMBIE = 291f / 256f;
    public static final float ESCALA_HITBOX_ENEMIGO = 0.4f;
    public static final float VELOCIDAD_ZOMBIE = 40f;    
    public static final float VELOCIDAD_ACECHADOR = 120f;  
    public static final int PROBABILIDAD_ACECHADOR = 5;
    
    // ====================   SUPERVIVIENTE   ====================
    
    public static final float VELOCIDAD_SUPERVIVIENTE = 300f;
    public static final float PUSH_DISTANCE = 30.0f;
    public static final int TIEMPO_HERIDO_MAX = 50;
    public static final int EFECTO_TEMBLOR = 2;
    
    // ====================   ARMAS   ====================
    
    public static final float CADENCIA_PISTOLA = 2.5f;
    public static final float CADENCIA_ESCOPETA = 0.8f;
    public static final float SPREAD_ESCOPETA = 60f;
    
 // ====================   PROYECTILES   ====================
    
    public static final float VELOCIDAD_BALA_PISTOLA = 480f;
    public static final float VELOCIDAD_BALA_ESCOPETA = 420f;
    public static final int DANIO_PISTOLA = 10;
    public static final int DANIO_ESCOPETA = 25;
    public static final float ESCALA_HITBOX_PROYECTIL = 5f;
	public static final int SCORE_DESBLOQUEO_ESCOPETA = 100;
    
    
    
    //Se utiliza un constructor privado de esta manera se evita la instanciacion, porque esta clase es solo para acceder a constantes estaticas. Asi evitamos errores.
    
    private ConfiguracionJuego() {
        throw new UnsupportedOperationException(
            "ConfiguracionJuego es una clase de utilidad y no debe ser instanciada"
        );
    }
}