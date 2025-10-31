# Ground Zero

<img width="1600" height="900" alt="FondoMenu" src="https://github.com/user-attachments/assets/1fb82255-2ed9-4ed0-8ab6-fdf63069e3f1" />

## Descripción
“Ground Zero” es un videojuego de disparos con vista cenital (top-down shooter) y centrado en niveles tipo hordas, 
ambientado en un escenario postapocalíptico. El jugador debe asumir el rol de un superviviente que debe enfrentarse 
a hordas de zombies para sobrevivir. 

## Autor
- Sebastián Garay Núñez
- Ingeniería en Informática / Pontificia Universidad Católica de Valparaíso
- Benjamín Lazcano
- Ingeniería en Informática / Pontificia Universidad Católica de Valparaíso
- Vicente Olguin
- Ingeniería en Informática / Pontificia Universidad Católica de Valparaíso
  
## Tecnologías utilizadas
- **Java:** JDK 11 (para `sourceCompatibility`) y JDK 17 (para compilación).
- **libGDX:** Framework principal para el desarrollo de videojuegos.
- **Gradle:** Gestor de dependencias y automatización de la compilación.
- **Eclipse IDE:** Entorno de desarrollo principal.
- **GitHub:** Control de versiones.

## Objetivos del Juego
Sobrevivir el mayor tiempo posible eliminando hordas de enemigos. El juego avanza por rondas, y cada ronda
aumenta la cantidad y dificultad de los zombies.

## Elementos del Juego
- **Superviviente:** Es el personaje controlado por el jugador. Tiene un número limitado de vidas y comienza con un arma básica, en este caso, una pistola.
- **Zombie:** Es el enemigo estándar, su objetivo es matar al superviviente. Posee un comportamiento de persecución simple hacia el superviviente.
- **Acechador:** Es un enemigo más resistente y rápido que se mueve erráticamente por el mapa (rebotando en los bordes).
- **Pistola:** Es el arma inicial, posee una cadencia de disparo rápida, pero un daño moderado.
- **Escopeta:** Es un arma que se desbloquea al alcanzar cierto puntaje, posee una cadencia lenta, pero dispara múltiples proyectiles con dispersión.

## Controles
- **Movimiento:** Flechas Arriba, Abajo, Izquierda y Derecha para indicar la dirección del personaje.
- **Acción:** Barra Espaciadora para disparar el arma equipada en la dirección que mira el superviviente.

## Instrucciones de Ejecución (para Desarrollo)

### Requisitos de Compilación
Este es un proyecto Gradle (Gdx-Liftoff) y tiene requisitos específicos:
1.  **JDK 17 (o superior):** **Requerido para la compilación.** El proyecto usa herramientas de *build* de Gradle que necesitan un JDK 17+ para funcionar.
2.  **JDK 11:** El código fuente (`sourceCompatibility`) está escrito para ser 100% compatible con JDK 11.
3.  **IDE:** Un IDE con soporte para Gradle, como **Eclipse** o **IntelliJ IDEA**.

### Pasos para Ejecutar
1.  **Descargar el Proyecto**
    * Opción A (Git): `git clone https://github.com/sebagarayn/GroundZero`
    * Opción B (ZIP): Descarga el ZIP desde el botón "Code" en GitHub y descomprímelo.

2.  **Importar en tu IDE**
    * En Eclipse (o IntelliJ), selecciona: `File` -> `Import...`
    * Elige: `Gradle` -> **`Existing Gradle Project`**.
    * Selecciona la carpeta `GroundZero` que acabas de descargar.
    * El IDE detectará los módulos y descargará las dependencias.

3.  **Ejecutar el Proyecto**
    * En el explorador de paquetes, navega hasta el módulo `lwjgl3`.
    * Busca y ejecuta el archivo **`DesktopLauncher.java`**.

## Estructura del Proyecto
- **`core/`**: Módulo principal con toda la lógica del juego (Entidades, Pantallas, Principios SOLID).
- **`lwjgl3/`**: Módulo de escritorio (Desktop) que contiene el `DesktopLauncher`.
- **`build.gradle`**: Archivo de configuración de Gradle que gestiona las dependencias.

## Fecha de Desarrollo
- **Versión 1.0 (Fase 1):** 30/10/2025
