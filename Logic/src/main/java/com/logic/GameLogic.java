package com.logic;

import com.engine.Game;
import com.engine.GameState;
import com.engine.Graphics;
import com.engine.Input;
import com.engine.Rect;
import com.engine.Sprite;

import java.util.ArrayList;
import java.util.List;

public class GameLogic implements GameState {
    public GameLogic(Game game){
        _game=game;
        gameObjects = new ArrayList<>();
    }

    @Override
    public void init() {
        Graphics graphics = _game.getGraphics();
        int screenWidth = _game.getGraphics().getWidth();
        int screenHeight = _game.getGraphics().getHeight();
        GameObject backgroundOb = new GameObject("background",new Sprite(graphics.newImage("Sprites/backgrounds.png"), new Rect(0,0,32,32)),
                0 , 0, screenWidth, screenHeight);
        gameObjects.add(backgroundOb);

        test = new GameObject("test",new Sprite(graphics.newImage("Sprites/balls.png"), new Rect(0,0,128,128)),
                0 , 0, 128, 128);

        gameObjects.add(test);

        GameObject flechas = new GameObject("background",new Sprite(graphics.newImage("Sprites/arrowsBackground.png")),
                0 , 0);

        gameObjects.add(flechas);

    }

    @Override
    public void update(double deltaTime) {
        double incr = ballVel*deltaTime;
        int posY = test.getY();
        int newPosY = (int)(posY+incr);
        test.setY(newPosY);

        List<Input.TouchEvent> events = _game.getInput().getTouchEvents();
        for (Input.TouchEvent event : events) {
            if(event.type == Input.EventType.pulsado){
                ballVel*=-1;
            }
        }
    }

    @Override
    public void render(double deltaTime) {

        _game.getGraphics().clear(0xFF0000FF);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(_game.getGraphics());
        }

    }
    int _gameWidth = 1080;
    int _gameHeight = 1920;
    int ballVel = 450;
    GameObject test;
    GameObject flechas;
    List<GameObject> gameObjects;
    Game _game;
}
