package com.logic;

import com.engine.Game;
import com.engine.GameState;
import com.engine.Graphics;
import com.engine.Input;
import com.engine.Rect;
import com.engine.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Random;

public class MenuState implements GameState {
    public MenuState(Game game){
        _game=game;
        gameObjects = new ArrayList<>();
    }

    @Override
    public void init() {

        Random r = new Random();
        _rndColor = r.nextInt((8 - 0) + 1);
        Graphics graphics = _game.getGraphics();
        int screenWidth = _game.getGraphics().getWidth();
        int screenHeight = _game.getGraphics().getHeight();
        GameObject backgroundOb = new GameObject("background",new Sprite(graphics.newImage("Sprites/backgrounds.png"),
                new Rect(32*_rndColor,0,32,32)),screenWidth/6 , 0, (screenWidth/3)*2, screenHeight);
        gameObjects.add(backgroundOb);

        _flechas1 = new GameObject("arrows1",new Sprite(graphics.newImage("Sprites/arrowsBackground.png"),100),
                screenWidth/6 , 0, (screenWidth/3)*2, screenHeight*2);
        _flechas2 = new GameObject("arrows2",new Sprite(graphics.newImage("Sprites/arrowsBackground.png"),100),
                screenWidth/6 , -screenHeight*2, (screenWidth/3)*2, screenHeight*2);

        gameObjects.add(_flechas1);
        gameObjects.add(_flechas2);

    }

    @Override
    public void update(double deltaTime) {
        double incr = ballVel*deltaTime;
        //int posY = test.getY();
        //int newPosY = (int)(posY+incr);
        //test.setY(newPosY);
        updateFlechas(deltaTime);
        List<Input.TouchEvent> events = _game.getInput().getTouchEvents();
        for (Input.TouchEvent event : events) {
            if(event.type == Input.EventType.clicked){
                ballVel*=-1;
            }
        }
    }

    public void updateFlechas(double deltaTime){
        _timer+=deltaTime;
        if(_timer>=0.02) {
            _timer-=0.02;
            _flechas1.setY(_flechas1.getY() + 1);
            _flechas2.setY(_flechas2.getY() + 1);
        }
        if(_flechas1.getY()>_game.getGraphics().getHeight())_flechas1.setY((-_game.getGraphics().getHeight()*3)+1);
        if(_flechas2.getY()>_game.getGraphics().getHeight())_flechas2.setY((-_game.getGraphics().getHeight()*3)+1);
    }

    @Override
    public void render(double deltaTime) {

        _game.getGraphics().clear(_colors[_rndColor]);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(_game.getGraphics());
        }

    }

    public int GetGameWidth(){ return _gameWidth;}
    public int GetGameHeight(){ return _gameHeight;}
    int _gameWidth = 1080;
    int _gameHeight = 1920;
    int ballVel = 450;
    float _timer=0;
    GameObject _flechas1;
    GameObject _flechas2;
    int[] _colors = { 0x41a85f, 0x00a885, 0x3d8eb9, 0x2969b0, 0x553982, 0x28324e, 0xf37934,
            0xd14b41, 0x75706b};;
    int _rndColor;
    List<GameObject> gameObjects;
    Game _game;
}