package com.desktopgame;

import com.engine.desktopengine.Game;
import com.logic.GameLogic;
import com.logic.GameOverState;
import com.logic.HowToState;
import com.logic.MenuState;

public class Main {
    public static void main(String[] args){
		//Crea un objeto Game del desktopEngine. un GameLogic y los enlaza
        Game desktopGame = new Game();
        GameOverState gameLogic = new GameOverState(desktopGame, 0);

        desktopGame.setGameState(gameLogic);
        //Ejecuta el bucle principal del juego
        desktopGame.runGame();
    }

}
