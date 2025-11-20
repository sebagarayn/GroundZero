package com.sign.groundzero;

public final class ScoreManager {
	private static final ScoreManager instance = new ScoreManager(); //Instancia de la clase
	private int score;
	private int highScore;
	
	//Constructor privado, para que nadie fuera de esta clase pueda hacer el new ScoreManager()
	private ScoreManager() {
		this.score = 0;
		this.highScore = 0;
	}
	
	//Para el acceso global
	public static ScoreManager getInstance() {
		return instance;
	}
	
	//Para reiniciar el puntaje
	public void resetScore() {
		this.score = 0;
	}
	
	//Para sumar puntos y actulizar el highscore si es necesario
	public void agregarPuntos(int puntos) {
		if(puntos > 0) {
			this.score += puntos;
			if(this.score > this.highScore) {
				this.highScore = this.score;
			}
		}
	}
	
	//Getters
	public int getScore() {
		return score;
	}
	
	public int getHighScore() {
		return highScore;
	}
}
