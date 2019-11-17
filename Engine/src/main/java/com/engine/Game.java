package com.engine;

import java.util.Stack;

/**
 * Interfaz game
 */
 //Interfaz que aglutina todo lo normal, se encarga de las instancias de Input y Graphics
public interface Game{
	void setGameState(GameState gameState);
	void runGame();

    Graphics getGraphics();

	Input getInput();

	int getGameWidth();
	int getGameHeight();
}