package com.sign.groundzero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;

//Clase PantallaGameOver: Muestra los resultados finales del juego. Actúa como Cliente del Patrón Singleton (ScoreManager)

public class PantallaGameOver implements Screen {

	private GroundZero game;
	private OrthographicCamera camera;
	private Viewport viewport;
	private Texture backgroundTexture;

	public PantallaGameOver(GroundZero game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, ConfiguracionJuego.WORLD_WIDTH, ConfiguracionJuego.WORLD_HEIGHT);
		viewport = new FitViewport(ConfiguracionJuego.WORLD_WIDTH, ConfiguracionJuego.WORLD_HEIGHT, camera);
		backgroundTexture = new Texture(Gdx.files.internal("Fondos/FondoGameOver.png"));
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		
		viewport.apply();
		game.getBatch().setProjectionMatrix(camera.combined);
		
		game.getBatch().begin();
		game.getBatch().draw(backgroundTexture, 0, 0, ConfiguracionJuego.WORLD_WIDTH, ConfiguracionJuego.WORLD_HEIGHT);
		
		game.getFont().draw(game.getBatch(), "Score Obtenido: " + ScoreManager.getInstance().getScore(), 700, 550);
		game.getFont().draw(game.getBatch(), "HighScore: " + ScoreManager.getInstance().getHighScore(), 725, 500);
		
		game.getFont().draw(game.getBatch(), "Pincha en cualquier lado para reiniciar ...", 550, 250);
	
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			reiniciarJuego();
		}
	}
	
	//Para reiniciar el juego
	private void reiniciarJuego() {
		ScoreManager.getInstance().resetScore();
		Screen ss = new PantallaJuego(game, ConfiguracionJuego.RONDA_INICIAL, ConfiguracionJuego.VIDAS_INICIALES, ConfiguracionJuego.CANTIDAD_ENEMIGOS_INICIAL);
        ss.resize((int)ConfiguracionJuego.WORLD_WIDTH, (int)ConfiguracionJuego.WORLD_HEIGHT);
        game.setScreen(ss);
        dispose();
	}
 
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
	    viewport.update(width, height, true); // Actualiza el viewport y centra la cámara.
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
	    if (backgroundTexture != null) {
	        backgroundTexture.dispose();
	    }
	}
   
}