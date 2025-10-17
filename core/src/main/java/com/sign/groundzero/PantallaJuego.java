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

/**
 * Pantalla principal del juego
 * Maneja la lógica del juego, renderizado y colisiones
 */
public class PantallaJuego implements Screen {

	private GroundZero game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXZombies; 
	private int velYZombies; 
	private int cantZombies;
	
	private Superviviente jugador;
	private ArrayList<Zombie> zombies = new ArrayList<>();
	private ArrayList<BalaPistola> balas = new ArrayList<>();

	public PantallaJuego(GroundZero game, int ronda, int vidas, int score,  
			int velXZombies, int velYZombies, int cantZombies) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXZombies = velXZombies;
		this.velYZombies = velYZombies;
		this.cantZombies = cantZombies;
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1, 0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // Crear jugador
	    jugador = new Superviviente(
	    		Gdx.graphics.getWidth()/2-50, 30,
	    		new Texture(Gdx.files.internal("Superviviente/Superviviente.png")),
	    		Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    		new Texture(Gdx.files.internal("Rocket2.png")), 
	    		Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))
	    );
        jugador.setVidas(vidas);
        
        // Crear zombies
        crearZombies();
	}
    
	private void crearZombies() {
	    Random r = new Random();
	    
	    // Calcular proporción de la imagen original
	    float aspectRatio = 291f / 256f;
	    
	    int anchoZombie = 80;
	    int altoZombie = (int)(anchoZombie * aspectRatio);  //
	    
	    for (int i = 0; i < cantZombies; i++) {
	        Zombie zombie = new Zombie(
	            r.nextInt((int)Gdx.graphics.getWidth()),
	            50 + r.nextInt((int)Gdx.graphics.getHeight()-50),
	            anchoZombie,  
	            altoZombie,  
	            velXZombies + r.nextInt(4), 
	            velYZombies + r.nextInt(4), 
	            new Texture(Gdx.files.internal("Enemigos/zombie.png"))
	        );	   
	        zombies.add(zombie);
	    }
	}
	
	private void dibujarEncabezado() {
		CharSequence str = "Vidas: " + jugador.getVidas() + " Ronda: " + ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Actualizar lógica
		actualizarJuego(delta);
		
		// Renderizar
		batch.begin();
		dibujarEncabezado();
		dibujarEntidades();
		batch.end();
		
		// Verificar condiciones de fin
		verificarEstadoJuego();
	}
	
	private void actualizarJuego(float delta) {
		if (!jugador.estaHerido()) {
			// Actualizar jugador
			jugador.actualizar(delta);
			
			// Disparar si presiona espacio
			if (jugador.puedeDisparar()) {
				balas.add(jugador.disparar());
			}
			
			// Actualizar balas
			actualizarBalas(delta);
			
			// Actualizar zombies
			actualizarZombies(delta);
			
			// Detectar colisiones
			detectarColisiones();
		} else {
			jugador.actualizar(delta);
		}
	}
	
	private void actualizarBalas(float delta) {
		for (int i = 0; i < balas.size(); i++) {
			BalaPistola bala = balas.get(i);
			bala.actualizar(delta);
			
			if (bala.estaDestruido()) {
				balas.remove(i);
				i--;
			}
		}
	}
	
	private void actualizarZombies(float delta) {
		for (int i = 0; i < zombies.size(); i++) {
			Zombie zombie = zombies.get(i);
			zombie.actualizar(delta);
			
			if (zombie.estaDestruido()) {
				zombies.remove(i);
				i--;
			}
		}
	}
	
	private void detectarColisiones() {
		// Colisiones balas vs zombies
		for (int i = 0; i < balas.size(); i++) {
			BalaPistola bala = balas.get(i);
			for (int j = 0; j < zombies.size(); j++) {
				Zombie zombie = zombies.get(j);
				
				if (bala.getBounds().overlaps(zombie.getBounds())) {
					bala.alColisionar(zombie);
					zombie.alColisionar(bala);
					explosionSound.play();
					score += zombie.getValorPuntos();
					bala.destruir();

					if (zombie.estaMuerto() || zombie.estaDestruido()) {
						zombies.remove(j);
						j--;
					}
					break;
				}
			}
		}
		
		for(int i = balas.size() - 1 ; i >= 0 ; i --) {
			if(balas.get(i).estaDestruido()) {
				balas.remove(i);
			}
		}
		
		// Colisiones jugador vs zombies
		for (int i = 0; i < zombies.size(); i++) {
			Zombie zombie = zombies.get(i);
			if (jugador.getBounds().overlaps(zombie.getBounds()) && 
				jugador.puedeColisionarCon(zombie)) {
				jugador.alColisionar(zombie);
				zombie.alColisionar(jugador);
			}
		}
		
		// Colisiones entre zombies
		for (int i = 0; i < zombies.size(); i++) {
			Zombie zombie1 = zombies.get(i);
			for (int j = i + 1; j < zombies.size(); j++) {
				Zombie zombie2 = zombies.get(j);
				if (zombie1.getBounds().overlaps(zombie2.getBounds())) {
					zombie1.alColisionar(zombie2);
				}
			}
		}
	}
	
	private void dibujarEntidades() {
		// Dibujar balas
		for (BalaPistola bala : balas) {
			bala.dibujar(batch);
		}
		
		// Dibujar jugador
		jugador.dibujar(batch);
		
		// Dibujar zombies
		for (Zombie zombie : zombies) {
			zombie.dibujar(batch);
		}
	}
	
	private void verificarEstadoJuego() {
		// Game Over
		if (jugador.estaDestruido()) {
			if (score > game.getHighScore()) {
				game.setHighScore(score);
			}
			Screen ss = new PantallaGameOver(game);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
		
		// Nivel completado
		if (zombies.size() == 0) {
			Screen ss = new PantallaJuego(game, ronda+1, jugador.getVidas(), score, 
					velXZombies+3, velYZombies+3, cantZombies+10);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	}
	
	@Override
	public void show() {
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		// TODO: Implementar si es necesario
	}

	@Override
	public void pause() {
		// TODO: Implementar si es necesario
	}

	@Override
	public void resume() {
		// TODO: Implementar si es necesario
	}

	@Override
	public void hide() {
		// TODO: Implementar si es necesario
	}

	@Override
	public void dispose() {
		explosionSound.dispose();
		gameMusic.dispose();
	}
}