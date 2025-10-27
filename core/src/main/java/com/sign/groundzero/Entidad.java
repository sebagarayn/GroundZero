package com.sign.groundzero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*CLASE ABSTRACTA: Para entidades vivas del juego (Jugador y Enemigos)
 *extiende ObjetoJuego e implementa las interfaces necesarias*/

public abstract class Entidad extends ObjetoJuego implements Colisionable, Movible, Daniable {
	private Sprite sprite;
	private float velocidadX = 0;
	private float velocidadY = 0;
	private int salud;
	private int saludMaxima;
	
	public Entidad(float x, float y, float ancho, float alto, Texture textura, int salud) {
		super(x, y, ancho, alto);
		this.sprite = new Sprite(textura);
		this.sprite.setBounds(x, y, ancho, alto);
		this.salud = salud;
		this.saludMaxima = salud;
	}
	
//=====================   IMPLEMENTACION OBJETOJUEGO   ======================
	
	@Override
	public void actualizar(float delta) { //Delta: Tiempo en segundos desde utlimo frame
		mover(delta);
		sprite.setPosition(getX(), getY());
	}
	
	@Override
	public void dibujar(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
//======================   IMPLEMENTACION DE MOVIBLE   ======================
	
	@Override
	public void mover(float delta) {
		setPosicion(getX() + velocidadX, getY() + velocidadY);
	}
	
	@Override
	public void setVelocidad(float vx, float vy) {
		this.velocidadX = vx;
		this.velocidadY = vy;
	}
	
	@Override
	public float getVelocidadX() {
		return velocidadX;
	}
	
	@Override
	public float getVelocidadY() {
		return velocidadY;
	}
	
//======================   IMPLEMENTACION DE DANIABLE   =====================
	
	@Override
	public void recibirDanio(int danio) {
		salud -= danio;
		if(salud <= 0) {
			salud = 0;
			destruir();
		}
	}
	
	@Override
	public int getSalud() {
		return salud;
	}
	
	@Override
	public boolean estaMuerto() {
		return salud <= 0;
	}
	
	@Override
	public void curar(int cantidad) {
		salud = Math.min(salud + cantidad, saludMaxima);
	}
	
//=========================   IMPLEMENTACION DE COLISIONABLE   ==============
	@Override
	public void alColisionar(Colisionable otro) {
		//Implementacion por defecto, para poder sobrescribir
	}
	
	@Override
	public boolean puedeColisionarCon(Colisionable otro) {
		return !estaDestruido();
	}
	
//=========================   GETTERS PROTEGIDOS   =======================
	
	protected Sprite getSprite() {
		return sprite;
	}
	
	protected void setVelocidadX(float vx) {
		this.velocidadX = vx;
	}
	
	protected void setVelocidadY(float vy) {
		this.velocidadY = vy;
	}
	
	protected int getSaludMaxima() {
		return saludMaxima;
	}
	
	protected void setSalud(int salud) {
		this.salud = Math.max(0,  Math.min(salud, saludMaxima));
	}
}
