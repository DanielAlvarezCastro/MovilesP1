package com.example.movilesp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.engine.androidengine.Game;
import com.logic.MenuState;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SurfaceView surfaceView = new SurfaceView(this);
        setContentView(surfaceView);
        _androidGame = new Game(getAssets(), surfaceView, 1080, 1920);
        MenuState gameLogic = new MenuState(_androidGame);
        _androidGame.setGameState(gameLogic);

    }

    @Override
    protected void onResume() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        _androidGame.resume();

    } // onResume

    @Override
    protected void onPause() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onPause();
        _androidGame.pause();

    } // onPause

    Game _androidGame;
}
