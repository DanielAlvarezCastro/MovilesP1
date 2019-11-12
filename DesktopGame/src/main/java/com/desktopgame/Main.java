package com.desktopgame;

import com.engine.desktopengine.Game;
import com.logic.GameLogic;

public class Main {
    public static void main(String[] args){
		//Crea un objeto Game del desktopEngine. un GameLogic y los enlaza
        Game desktopGame = new Game();
        GameLogic gameLogic = new GameLogic(desktopGame);

        desktopGame.setGameState(gameLogic);
        //Ejecuta el bucle principal del juego
        desktopGame.runGame();
    }

}
