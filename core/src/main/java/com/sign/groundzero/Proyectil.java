package com.sign.groundzero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/*CLASE ABSTRACTA: Para todos los proyectiles del juego, extiende ObjetoJuego e implementa
 *Colisionable y Movible.*/

// ========================= CLASE ABSTRACTA PROYECTIL ========================= 

public abstract class Proyectil extends ObjetoJuego implements Colisionable, Movible {
	private Sprite sprite;
	private float velocidadX;
	private float velocidadY;
	
	public Proyectil(float x, float y, float velocidadX, float velocidadY, Texture textura, float ancho, float alto) {
		super(x, y, ancho, alto);
        if (textura == null) {
            throw new IllegalArgumentException("La textura no puede ser null");
        }
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.sprite = new Sprite(textura);
		this.sprite.setBounds(x, y, ancho, alto);
	}
	
	//Implementacion de ObjetoJuego
	
	@Override
	public void actualizar(float delta) {
		mover(delta);
		sprite.setPosition(getX(), getY());
		
		//Para destruir si sale de la pantalla
		if(fueraDePantalla()) {
			destruir();
		}
	}
	
	@Override
	public Rectangle getBounds() {
		float scale = ConfiguracionJuego.ESCALA_HITBOX_PROYECTIL;
		float originalWidth = getAncho(); // Obtener ancho de ObjetoJuego
		float originalHeight = getAlto(); // Obtener alto de ObjetoJuego
		float originalX = getX(); // Obtener X de ObjetoJuego
		float originalY = getY(); // Obtener Y de ObjetoJuego
		
		float hitboxWidth = originalWidth * scale;
        float hitboxHeight = originalHeight * scale;
        
        float hitboxX = originalX + (originalWidth - hitboxWidth) / 2f;
        float hitboxY = originalY + (originalHeight - hitboxHeight) / 2f;
        
        return new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
	}
	
	@Override
	public void dibujar(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	//Implementación de Movible
	@Override
	public void mover(float delta) {
		setPosicion(getX() + velocidadX * delta, getY() + velocidadY * delta);
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
	
	//Implementacion de Colisionable
	@Override
	public void alColisionar(Colisionable otro) {
		destruir();
	}
	
	@Override
	public boolean puedeColisionarCon(Colisionable otro) {
		return !estaDestruido();
	}
	
	//Metodos Auxiliares
    private boolean fueraDePantalla() {
        return getX() < 0 || getX() > Gdx.graphics.getWidth() || getY() < 0 || getY() > Gdx.graphics.getHeight();
    }
	
	protected Sprite getSprite() { //Se necesita que la sublase acceda
		return sprite;
	}
}
