package com.sign.groundzero;
import java.util.List;

//Clase Abstracta: Permite definir el comportamiento de disparo de las armas, pudiendo cambiar el comportamiento de disparo en tiempo de ejecucion..

public abstract class Arma {
	private final float cadencia; //Cadencia de disparos por segundo
	private float tiempoDesdeUltimoDisparo; //Contador de tiempo desde el ultimo disparo.
	
	//Constructor
	public Arma(float cadencia) {
		this.cadencia = cadencia; //Se inicializa listo para disparar la primera vez.
		this.tiempoDesdeUltimoDisparo = 1 / cadencia;
	}
	
	//Plantilla del Template Method
	public final void disparar(Superviviente portador, List<Proyectil> balasDelMundo) {
		if(puedeDisparar()) {
			crearProyectiles(portador, balasDelMundo);
			reproducirSonido();
			reiniciarCooldown();
		}
	}
	
	//Pasos que deben implementar las subclases
	protected abstract void crearProyectiles(Superviviente portador, List<Proyectil> balasDelMundo);
	protected abstract void reproducirSonido();
	
	//Actualiza el temporizador interno del cooldown, se llama en cada frame desde el superviviente.	
	public void actualizar(float delta) {
		tiempoDesdeUltimoDisparo += delta;
	}
	
	//Verifica si el arma esta lista para disparar de nuevo.	
	public boolean puedeDisparar() {
		return tiempoDesdeUltimoDisparo >= (1 / cadencia);
	}
	
	//Para reiniciar el temporizador de cooldown.	
	protected void reiniciarCooldown() { //Lo pongo como protected porque es un método interno que solo deben llamar las clases que heredan de Arma y no debe ser visible para clases externas.
		tiempoDesdeUltimoDisparo = 0;
	}
	
	//Para obtener la cadencia de disparo, en caso de que sea necesario para el UI.	
	public float getCadencia() {
		return cadencia;
	}
}
