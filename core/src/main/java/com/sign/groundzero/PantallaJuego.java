package com.sign.groundzero;
import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

//Pantalla principal del juego
//Maneja la lógica del juego, renderizado y colisiones.

public class PantallaJuego implements Screen, LimitesJuego {
	
	private Viewport viewport;
	private Texture backgroundTexture;
	
	//Componentes Principales
	
	private GroundZero game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	
	//Audio
	
	private Sound ZombieHeridoSonido;
	private Music gameMusic;
	
	// Recursos
	
	private Texture texturaJugador;
	private Sound sonidoHeridoJugador;
	private Texture texturaBalaPistola;
	private Sound sonidoDisparo;
	
	//Estado del juego
	
	private int score;
	private int ronda;
	private int cantZombies;
	private boolean escopetaDesbloqueada = false; //Si tiene o no la escopeta el jugador
	
	//Texturas
	
	private Texture texturaEscopeta;
	private Texture texturaZombie;
	private Texture texturaAcechador;
	
	//Entidades del juego
	
	private Superviviente jugador;
	private ArrayList<Enemigo> zombies;
	private ArrayList<Proyectil> balas;
	
	//Constructor PantallaJuego

	public PantallaJuego(GroundZero game, int ronda, int vidas, int score, int cantZombies) {
		
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.cantZombies = cantZombies;
		this.escopetaDesbloqueada = false;
		
		//Inicializar listas
		
		this.zombies = new ArrayList<Enemigo>();
		this.balas = new ArrayList<Proyectil>();
		
		//Configurar camara
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, ConfiguracionJuego.WORLD_WIDTH, ConfiguracionJuego.WORLD_HEIGHT);
		
		viewport = new FitViewport(ConfiguracionJuego.WORLD_WIDTH, ConfiguracionJuego.WORLD_HEIGHT, camera);
		backgroundTexture = new Texture(Gdx.files.internal("Fondos/FondoJuego.png"));
		
		//Inicializar audio
		
		inicializarAudio();
		
		//Inicializar texturas
		
		texturaEscopeta = new Texture(Gdx.files.internal("Rocket2.png"));
		texturaJugador = new Texture(Gdx.files.internal("Superviviente/Superviviente.png"));
		sonidoHeridoJugador = Gdx.audio.newSound(Gdx.files.internal("hurt.mp3")); 
		texturaBalaPistola = new Texture(Gdx.files.internal("Rocket2.png")); 
		sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal("SonidoBala.mp3"));
		texturaZombie = new Texture(Gdx.files.internal("Enemigos/zombie.png"));
		texturaAcechador = new Texture(Gdx.files.internal("Enemigos/acechador.png"));
		
		//Crear jugador
		
		Arma pistolaInicial = new Pistola(texturaBalaPistola, sonidoDisparo);
		jugador = new Superviviente(Gdx.graphics.getWidth()/2-50, 30, texturaJugador, sonidoHeridoJugador, pistolaInicial);
		jugador.setVidas(vidas);
		
		//Crear zombies
		
		crearZombies();
	}
	
	//Para inicializar los recursos de audio
	
	private void inicializarAudio() {

		ZombieHeridoSonido = Gdx.audio.newSound(Gdx.files.internal("ZombieHerido.mp3"));
		ZombieHeridoSonido.setVolume(1, 0.5f);
		
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("OST.mp3"));		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
	}
	
	//Para crear loz zombies en la ronda actual
    
	private void crearZombies() {
		
	    Random r = new Random();
	    int altoZombie = (int)(ConfiguracionJuego.ANCHO_ZOMBIE * ConfiguracionJuego.ASPECT_RATIO_ZOMBIE);
	    
	    for (int i = 0 ; i < cantZombies ; i++) {
	        float posX = r.nextInt((int)Gdx.graphics.getWidth() - ConfiguracionJuego.ANCHO_ZOMBIE); 
	        float posY = 50 + r.nextInt((int)Gdx.graphics.getHeight() - 50 - altoZombie);
	        
	        Enemigo nuevoEnemigo = crearEnemigoAleatorio(r, posX, posY, ConfiguracionJuego.ANCHO_ZOMBIE, altoZombie);
	        zombies.add(nuevoEnemigo);
	    }
	}
	
	//Para crear un enemigo aleatorio (Zombie / Acechador)
	
	private Enemigo crearEnemigoAleatorio(Random r, float posX, float posY, int ancho, int alto) {
	    
	    if (r.nextInt(ConfiguracionJuego.PROBABILIDAD_ACECHADOR) == 0) {
	        // Acechador NO necesita al jugador (movimiento errático)
	        return new Acechador(
	            posX, posY, 
	            (float)ancho, (float)alto, 
	            ConfiguracionJuego.VELOCIDAD_ACECHADOR, 
	            texturaAcechador,
	            this
	        );
	    } else {
	        
	        float velocidadZombie = ConfiguracionJuego.VELOCIDAD_ZOMBIE; 
	        
	        return new Zombie(
	            posX, posY, 
	            (float)ancho, (float)alto, 
	            texturaZombie,
	            this,           
	            jugador,        
	            velocidadZombie 
	        );
	    }
	}

	//Para dibujar el encabezado con informacion del juego
	
	private void dibujarEncabezado() {
		CharSequence str = "Vidas: " + jugador.getVidas() + " Ronda: " + ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:" + this.score, ConfiguracionJuego.WORLD_WIDTH - 200, 30);
		game.getFont().draw(batch, "HighScore:" + game.getHighScore(), ConfiguracionJuego.WORLD_WIDTH / 2 - 100, 30);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		actualizarJuego(delta); //Se actualiza la logica
		
		viewport.apply();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin(); //Se renderiza
		game.getBatch().draw(backgroundTexture, 0, 0, ConfiguracionJuego.WORLD_WIDTH, ConfiguracionJuego.WORLD_HEIGHT);
		dibujarEncabezado();
		dibujarEntidades();
		batch.end();

		verificarEstadoJuego(); //Se verifica las condiciones de fin
	}
	
	//Para actualizar toda la logica del juego
	
	private void actualizarJuego(float delta) {
		if (!jugador.estaHerido()) {
			jugador.actualizar(delta); //Actualizar jugador (esto también actualiza el cooldown del arma)
			
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) { //Logica de disparo
                jugador.intentarDisparar(balas); // Le pasa la lista abstracta <Proyectil>
            }
		
			actualizarBalas(delta); //Actualizar balas
			actualizarZombies(delta); //Actualizar Zombies
			detectarColisiones(); //Detectar colisiones
		} else {
			jugador.actualizar(delta);
		}
	}
	
	//Para actualizar todas las balas y eliminar las destruidas
	
	private void actualizarBalas(float delta) {
		for (int i = balas.size() - 1 ; i >= 0 ; i--) { //Para eliminar balas.
			Proyectil bala = balas.get(i);
			bala.actualizar(delta);		
			if (bala.estaDestruido()) {
				balas.remove(i);
			}
		}
	}
	
	//Para actualizar todos los zombies y eliminar los destruidos
	
	private void actualizarZombies(float delta) {
		for (int i = zombies.size() - 1 ; i >= 0 ; i--) { //Para eliminar zombies.
			Enemigo zombie = zombies.get(i);
			zombie.actualizar(delta);
			
			if (zombie.estaDestruido()) {
				zombies.remove(i);
			}
		}
	}
	
	//Para detectar y procesar todas las colisiones del juego, usa bucles inversos para evitar problemas con indices
	
	private void detectarColisiones() {
		detectarColisionesBalasZombies();
		detectarColisionesJugadorZombies();
		detectarColisionesEntreZombies();
	}
	
	//Detecta colisiones entre balas y zombies
	
	private void detectarColisionesBalasZombies() {
		for (int i = balas.size() - 1 ; i >= 0 ; i--) {
			Proyectil bala = balas.get(i);
			boolean balaDestruida = false;
			
			for (int j = zombies.size() - 1; j >= 0; j--) {
                Enemigo zombie = zombies.get(j);
                
                if (bala.getBounds().overlaps(zombie.getBounds())) {
                    bala.alColisionar(zombie);
                    zombie.alColisionar(bala);
                    ZombieHeridoSonido.play();
                    score += zombie.getValorPuntos();
                    
                    if (zombie.estaMuerto() || zombie.estaDestruido()) {
                        zombies.remove(j);
                    }
                    
                    if (bala.estaDestruido()) {
                        balas.remove(i);
                        balaDestruida = true;
                        break;
                    }
                }
            }
            
            if (balaDestruida) {
                break;
            }
		}
	}
	
	//Para detectar colisiones entre jugador y zombies
	
    private void detectarColisionesJugadorZombies() {
        for (int i = 0; i < zombies.size(); i++) {
            Enemigo zombie = zombies.get(i);
            if (jugador.getBounds().overlaps(zombie.getBounds()) && 
                jugador.puedeColisionarCon(zombie)) {
                jugador.alColisionar(zombie);
                zombie.alColisionar(jugador);
            }
        }
    }
    
    //Para detectar colisiones entre zombies
    
    private void detectarColisionesEntreZombies() {
        for (int i = 0; i < zombies.size(); i++) {
            Enemigo zombie1 = zombies.get(i);
            for (int j = i + 1; j < zombies.size(); j++) {
                Enemigo zombie2 = zombies.get(j);
                if (zombie1.getBounds().overlaps(zombie2.getBounds())) {
                    zombie1.alColisionar(zombie2);
                    zombie2.alColisionar(zombie1);
                }
            }
        }
    }
    
    //Para dibujar las entidades del juego
    
    private void dibujarEntidades() {
        for (Proyectil bala : balas) { //Dibujar las balas
            bala.dibujar(batch);
        }
        
        jugador.dibujar(batch); //Dibujar al jugador
        
        for (Enemigo zombie : zombies) { //Dibujas zombies
            zombie.dibujar(batch);
        }
    }
    
    //Verifica condiciones de fin, ya sea Game Over o nivel completo
    
    private void verificarEstadoJuego() {
        verificarDesbloqueoEscopeta();
        verificarGameOver();
        verificarNivelCompletado();
    }
    
    //Verifica si se debe desbloquear la escopeta
    
    private void verificarDesbloqueoEscopeta() {
        if (!escopetaDesbloqueada && score >= ConfiguracionJuego.SCORE_DESBLOQUEO_ESCOPETA) {
            escopetaDesbloqueada = true;
            Escopeta nuevaArma = new Escopeta(texturaEscopeta, sonidoDisparo);
            jugador.equiparArma(nuevaArma);
        }
    }
    
    //Verifica condiciones para Game Over
    
    private void verificarGameOver() {
        if (jugador.estaDestruido()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            Screen ss = new PantallaGameOver(game);
            ss.resize((int)getWorldWidth(), (int)getWorldHeight());
            game.setScreen(ss);
            dispose();
        }
    }
    
    //Verifica si el nivel fue completado
    
    private void verificarNivelCompletado() {
        if (zombies.size() == 0) {
            Screen ss = new PantallaJuego(game, ronda + 1, jugador.getVidas(), score, cantZombies + 10);
            ss.resize((int)getWorldWidth(), (int)getWorldHeight());
            game.setScreen(ss);
            dispose();
        }
    }
    
    
    //Para la implementacion de LimitesJuego
    
    @Override
    public float getWorldWidth() {
        return ConfiguracionJuego.WORLD_WIDTH;
    }
    
    @Override
    public float getWorldHeight() {
        return ConfiguracionJuego.WORLD_HEIGHT;
    }
    
    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // No se requiere implementación para este juego
    }

    @Override
    public void resume() {
        // No se requiere implementación para este juego
    }

    @Override
    public void hide() {
        // No se requiere implementación para este juego
    }

    @Override
    public void dispose() {
    	if (ZombieHeridoSonido != null) ZombieHeridoSonido.dispose();
		if (gameMusic != null) gameMusic.dispose();
		if (texturaEscopeta != null) texturaEscopeta.dispose();
		if (texturaJugador != null) texturaJugador.dispose();
		if (sonidoHeridoJugador != null) sonidoHeridoJugador.dispose();
		if (texturaBalaPistola != null) texturaBalaPistola.dispose();
		if (sonidoDisparo != null) sonidoDisparo.dispose();
	    if (texturaZombie != null) texturaZombie.dispose();
	    if (texturaAcechador != null) texturaAcechador.dispose();
	    if (backgroundTexture != null) {
	        backgroundTexture.dispose();
	    }
    }
}		