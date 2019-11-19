package com.engine;

import java.util.Stack;

/**
 * Interfaz game que ejecuta el juego y lleva las instancias de input y graphics
 */
public interface Game{

    /**
     * Asigna el GameState que se va a ejecutar
     * @param gameState a ejecutar
     */
	void setGameState(GameState gameState);

    /**
     * Ejecuta el bucle principal del gameState asignado
     */
	void runGame();


    Graphics getGraphics();
	Input getInput();

    /**
     * Devuelve la anchura del juego
     * @return anchura del juego
     */
	int getGameWidth();
    /**
     * Devuelve la altura del juego
     * @return altura del juego
     */
	int getGameHeight();
}