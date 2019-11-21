package com.engine.desktopengine;

import com.engine.GameState;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game implements com.engine.Game{

    public Game(int gameWidth, int gameHeight, int screenWidth, int screenHeight){
        _gameHeight=gameHeight;
        _gameWidth=gameWidth;
        //Crea la ventana
        JFrame window = new JFrame();
        window.setSize(screenWidth,screenHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setIgnoreRepaint((true));
        window.setVisible(true);
        window.createBufferStrategy(2);

        // Obtenemos el Buffer Strategy que acaba de crearse.
        _strategy = window.getBufferStrategy();

        //Crea un Graphics
        _graphics = new Graphics(window, _gameWidth, _gameHeight);

        //Crea un nuevo MouseListener y lo añade a la ventana
        _input = new Input(this);
        window.addMouseListener(_input);

    }
    /**
     * Asigna el GameState que se va a ejecutar
     * @param gameState a ejecutar
     */
    @Override
    public void setGameState(GameState gameState) {
        _gameState = gameState;
        _gameState.init();

    }

    /**
     * Ejecuta el bucle principal del gameState asignado
     */
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

                        _gameState.render();
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

    /**
     * Devuelve la anchura del juego
     * @return anchura del juego
     */
    @Override
    public int getGameWidth() {
        return _gameWidth;
    }

    /**
     * Devuelve la altura del juego
     * @return altura del juego
     */
    @Override
    public int getGameHeight() {
        return _gameHeight;
    }

    GameState _gameState;
    Graphics _graphics;
    Input _input;
    BufferStrategy _strategy;

    int _gameWidth ;
    int _gameHeight ;
}
