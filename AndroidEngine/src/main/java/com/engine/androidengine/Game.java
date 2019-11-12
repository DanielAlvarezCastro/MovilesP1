package com.engine.androidengine;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.engine.GameState;

public class Game implements com.engine.Game, Runnable {

    public Game(AssetManager assetManager, SurfaceView surfaceView){
        _surfaceView = surfaceView;
        _graphics = new Graphics(surfaceView, assetManager);
        _holder = surfaceView.getHolder();

    }

    @Override
    public void setGameState(GameState gameState) {
        _gameState = gameState;
    }

    @Override
    public void runGame() {

        _gameState.init();

        long lastFrameTime = System.nanoTime();
        while(_running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double deltaTime = (double) nanoElapsedTime / 1.0E9;
            //Update
            _gameState.update(deltaTime);

            //Pintamos el frame
            while(!_holder.getSurface().isValid());

            Canvas canvas = _holder.lockCanvas();
            _graphics.setCanvas(canvas);
            _gameState.render(deltaTime);
            _holder.unlockCanvasAndPost(canvas);
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
    public void run() {

        if (_renderThread != Thread.currentThread()) {
            //Evitar que se llame el método directamente
            throw new RuntimeException("run() should not be called directly");
        }

        runGame();
    }

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {

        if (!_running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva, nunca se sabe quién va a
            // usarnos...)
            _running = true;
            // Lanzamos la ejecución de nuestro método run()
            // en una hebra nueva.
            _renderThread = new Thread(this);
            _renderThread.start();
        } // if (!_running)

    } // resume


    /**
     * Método llamado cuando el active rendering debe ser detenido.
     * Puede tardar un pequeño instante en volver, porque espera a que
     * se termine de generar el frame en curso.
     *
     * Se hace así intencionadamente, para bloquear la hebra de UI
     * temporalmente y evitar potenciales situaciones de carrera (como
     * por ejemplo que Android llame a resume() antes de que el último
     * frame haya terminado de generarse).
     */
    public void pause() {

        if (_running) {
            _running = false;
            while (true) {
                try {
                    _renderThread.join();
                    _renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            } // while(true)
        } // if (_running)

    } // pause


    Thread _renderThread;
    GameState _gameState;
    Graphics _graphics;
    Input _input;
    SurfaceView _surfaceView;
    SurfaceHolder _holder;
    boolean _running;

}//class Game