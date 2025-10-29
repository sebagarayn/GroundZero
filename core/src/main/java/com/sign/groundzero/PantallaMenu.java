package com.sign.groundzero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;

public class PantallaMenu implements Screen {

	private GroundZero game;
	private OrthographicCamera camera;
	private Viewport viewport;
	private Texture backgroundTexture;

	public PantallaMenu(GroundZero game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, ConfiguracionJuego.WORLD_WIDTH, ConfiguracionJuego.WORLD_HEIGHT);
		viewport = new FitViewport(ConfiguracionJuego.WORLD_WIDTH, ConfiguracionJuego.WORLD_HEIGHT, camera);
		backgroundTexture = new Texture(Gdx.files.internal("Fondos/FondoMenu2.png"));
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		
		viewport.apply();
		game.getBatch().setProjectionMatrix(camera.combined);

		game.getBatch().begin();
		game.getBatch().draw(backgroundTexture, 0, 0, ConfiguracionJuego.WORLD_WIDTH, ConfiguracionJuego.WORLD_HEIGHT);
		game.getFont().draw(game.getBatch(), "Pincha en cualquier lado o presiona cualquier tecla para comenzar ...", 50, 50);	
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			iniciarJuego();
		}
	}
	
	//Para iniciar una nueva partida con valores iniciales.
	
	private void iniciarJuego() {
        Screen ss = new PantallaJuego(game,ConfiguracionJuego.RONDA_INICIAL, ConfiguracionJuego.VIDAS_INICIALES, ConfiguracionJuego.SCORE_INICIAL, ConfiguracionJuego.CANTIDAD_ZOMBIES_INICIAL);
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
	    viewport.update(width, height, true); // Actualiza el viewport y centra la cámara
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