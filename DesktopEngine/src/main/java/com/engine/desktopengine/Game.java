package com.engine.desktopengine;

import com.engine.GameState;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game implements com.engine.Game{

    public Game(){
        //Crea la ventana
        JFrame window = new JFrame();
        window.setSize(screenWidth,screenHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        _graphics = new Graphics(window, _gameWidth, _gameHeight);

        window.createBufferStrategy(2);
        // Obtenemos el Buffer Strategy que se supone acaba de crearse.
        _strategy = window.getBufferStrategy();
        _input = new Input();
        window.addMouseListener(_input);

    }
    @Override
    public void setGameState(GameState gameState) {
        _gameState = gameState;
        _gameState.init();

    }

    @Override
    public void runGame() {
        //Llama al init del gamestate
        _gameState.init();

        long lastFrameTime = System.nanoTime();
        while(true) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double deltaTime = (double) nanoElapsedTime / 1.0E9;
            //Update
            _gameState.update(deltaTime);
            //Render
            do {

                do {
                    java.awt.Graphics graphics = _strategy.getDrawGraphics();
                    _graphics.setGraphics(graphics);
                    try {

                        _gameState.render(deltaTime);
                    }
                    finally {
                        graphics.dispose();
                    }

                }while(_strategy.contentsRestored());
                _strategy.show();
            }while(_strategy.contentsLost());

        }

    }

    @Override
    public Graphics getGraphics() {
        return _graphics;
    }
	
	@Override
	public Input getInput(){
		return _input;
	}

    @Override
    public int getGameWidth() {
        return _gameWidth;
    }

    @Override
    public int getGameHeight() {
        return _gameHeight;
    }

    GameState _gameState;
    Graphics _graphics;
    Input _input;
    BufferStrategy _strategy;

    //TODO : Pasarle estas constantes de otra forma
    int screenWidth = 540;
    int screenHeight = 960;

    int _gameWidth = 1080;
    int _gameHeight = 1920;
}
