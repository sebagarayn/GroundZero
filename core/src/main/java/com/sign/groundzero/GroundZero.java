package com.sign.groundzero;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Clase concreta GroundZero

public class GroundZero extends Game {
	private SpriteBatch batch;
	private BitmapFont font;
	private int highScore;	

	// Se encarga de inicializar los recursos globales y la pantalla de menu.
	public void create() {
		highScore = 0;
		batch = new SpriteBatch();
		font = new BitmapFont(); // usa Arial font x defecto
		font.getData().setScale(2f);
		Screen ss = new PantallaMenu(this);
		this.setScreen(ss);
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
	    if (highScore >= 0) {
	        this.highScore = highScore;
	    }
	}
}