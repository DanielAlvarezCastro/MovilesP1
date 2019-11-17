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

public class GameOverState implements GameState {
    public GameOverState(Game game, int points){
        _game=game;
        gameObjects = new ArrayList<>();
        _points = points;
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

        GameObject gameOverSprite = new GameObject("gameOverSprite",new Sprite(graphics.newImage("Sprites/gameOver.png"),255),
                (screenWidth/2)-(252/4) , screenHeight/5, 252/2, 208/2);
        GameObject muteButton = new GameObject("muteButton",new Sprite(graphics.newImage("Sprites/buttons.png"),
                new Rect(140*2, 0, 140, 140), 255),
                (screenWidth/12)-(140/4) , screenHeight/8, 140/2, 140/2);
        GameObject helpButton = new GameObject("helpButton",new Sprite(graphics.newImage("Sprites/buttons.png"),
                new Rect(0, 0, 140, 140), 255),
                screenWidth-((screenWidth/12)+(140/4)) , screenHeight/8, 140/2, 140/2);
        tapSprite = new GameObject("tapSprite",new Sprite(graphics.newImage("Sprites/tapToPlay.png"),255),
                (screenWidth/2)-(532/4) , (screenHeight/3)*2, 532/2, 72/2);



        _tapAnimUp = false;
        gameObjects.add(_flechas1);
        gameObjects.add(_flechas2);
        gameObjects.add(gameOverSprite);
        gameObjects.add(muteButton);
        gameObjects.add(helpButton);
        gameObjects.add(tapSprite);

    }

    @Override
    public void update(double deltaTime) {
        double incr = ballVel*deltaTime;
        _fUpdateTimer+=deltaTime;
        if(_fUpdateTimer>=_fixedUpdateDelay) fixedUpdate(deltaTime);
        //int posY = test.getY();
        //int newPosY = (int)(posY+incr);
        //test.setY(newPosY);
        List<Input.TouchEvent> events = _game.getInput().getTouchEvents();
        for (Input.TouchEvent event : events) {
            if(event.type == Input.EventType.clicked){
                ballVel*=-1;
            }
        }
    }

    public void fixedUpdate(double deltaTime){
        _fUpdateTimer-=_fixedUpdateDelay;
        animateArrows(deltaTime);
        animateTap(deltaTime);
    }

    public void animateTap(double deltaTime){
        if(_tapAnimUp) {
            tapSprite.getSprite().setAlpha(tapSprite.getSprite().getAlpha()+5);//Bajar alfa
            if(tapSprite.getSprite().getAlpha() >= 255) _tapAnimUp=false;
        }
        else{
            tapSprite.getSprite().setAlpha(tapSprite.getSprite().getAlpha()-5);//Subir alfa
            if(tapSprite.getSprite().getAlpha() <= 0) _tapAnimUp=true;
        }
    }
    public void animateArrows(double deltaTime){
        _flechas1.setY(_flechas1.getY() + 1);
        _flechas2.setY(_flechas2.getY() + 1);

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
    int _points;
    double _fUpdateTimer=0;
    double _fixedUpdateDelay = 0.02;
    GameObject _flechas1;
    GameObject _flechas2;
    GameObject tapSprite;
    boolean _tapAnimUp;
    int[] _colors = { 0x41a85f, 0x00a885, 0x3d8eb9, 0x2969b0, 0x553982, 0x28324e, 0xf37934,
            0xd14b41, 0x75706b};;
    int _rndColor;
    List<GameObject> gameObjects;
    Game _game;
}