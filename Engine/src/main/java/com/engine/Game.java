package com.engine;

import java.util.Stack;

/**
 * Interfaz game
 */
 //Interfaz que aglutina todo lo normal, se encarga de las instancias de Input y Graphics
public interface Game{
	public void setGameState(GameState gameState);
	public void runGame();

    public Graphics getGraphics();

	public Input getInput();

	
	//TODO ampliar Game para incorporar el estado de la aplicación
}