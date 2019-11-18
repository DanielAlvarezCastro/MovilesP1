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

class Ball
{
    public GameObject gameObject;
    public boolean color;
};

public class PlayState implements GameState {
    public PlayState(Game game){
        _game=game;
        gameObjects = new ArrayList<>();
    }

    @Override
    public void init() {

        Random r = new Random();
        _rndColor = r.nextInt((8 - 0) + 1);
        Graphics graphics = _game.getGraphics();
        int screenWidth = _game.getGameWidth();
        int screenHeight = _game.getGameHeight();
        _backgroundOb = new GameObject("background",new Sprite(graphics.newImage("Sprites/backgrounds.png"),
                new Rect(32*_rndColor,0,32,32)),screenWidth/6 , 0, (screenWidth/3)*2, screenHeight*2);
        _flechas1 = new GameObject("arrows1",new Sprite(graphics.newImage("Sprites/arrowsBackground.png"),100),
                screenWidth/6 , 0, (screenWidth/3)*2, screenHeight*2);
        _flechas2 = new GameObject("arrows2",new Sprite(graphics.newImage("Sprites/arrowsBackground.png"),100),
                screenWidth/6 , (-screenHeight*2)-1, (screenWidth/3)*2, screenHeight*2);

        gameObjects.add(_backgroundOb);
        gameObjects.add(_flechas1);
        gameObjects.add(_flechas2);

        _ballTimer=0;
        balls = new Ball[nBalls];
        //Init balls
        for(int i=0; i < nBalls; i++){
            Ball b = new Ball();
            b.gameObject = new GameObject("ball"+i,new Sprite(graphics.newImage("Sprites/balls.png"),
                    new Rect(0,128,128,128)),(_game.getGameWidth()/2)-50 , -500, 100, 100);
            b.gameObject.setActive(false);
            b.color=false;
            balls[i]=b;
            gameObjects.add(balls[i].gameObject);
        }

    }

    @Override
    public void update(double deltaTime) {
        double incr = ballVel*deltaTime;
        _fUpdateTimer+=deltaTime;
        _ballTimer+=deltaTime;
        if(_fUpdateTimer>=_fixedUpdateDelay) fixedUpdate(deltaTime);
        handleInput();
    }

    public void handleInput(){
        List<Input.TouchEvent> events = _game.getInput().getTouchEvents();
        for (Input.TouchEvent event : events) {
            if(event.type == Input.EventType.clicked){
                //System.out.println("WindowW" + _game.getGraphics().getWidth());
                //System.out.println("WindowH" + _game.getGraphics().getHeight());
                //System.out.println("X: "+ event.x);
                //System.out.println("Y: "+ event.y);
                if(_backgroundOb.within(event.x, event.y))_game.setGameState(new GameOverState(_game, 95));
            }
        }
    }

    public void fixedUpdate(double deltaTime){
        _fUpdateTimer-=_fixedUpdateDelay;
        animateArrows(deltaTime);
        for (int i =0; i<nBalls; i++){
            if(balls[i].gameObject.getActive())balls[i].gameObject.setY(balls[i].gameObject.getY()+3);
        }
        if(_ballTimer >= _fixedUpdateDelay*100){
            throwBall();
        }
    }

    public void throwBall()
    {
        _ballTimer=0;
        int i=0;
        while(i<nBalls && balls[i].gameObject.getActive()){
            i++;
        }
        balls[i].gameObject.setActive(true);
        balls[i].gameObject.setY(0);
        Random rnd = new Random();
        int r = rnd.nextInt((1 - 0) + 1);
        System.out.println(r);
        if(r==0){
            balls[i].color=false;
            balls[i].gameObject.setSprite(new Sprite(_game.getGraphics().newImage("Sprites/balls.png"),
                    new Rect(0,128,128,128)));
        }else{
            balls[i].color=true;
            balls[i].gameObject.setSprite(new Sprite(_game.getGraphics().newImage("Sprites/balls.png"),
                    new Rect(0,0,128,128)));
        }
    }

    public void animateArrows(double deltaTime){
        _flechas1.setY(_flechas1.getY() + 1);
        _flechas2.setY(_flechas2.getY() + 1);

        if(_flechas1.getY()>_game.getGameHeight())_flechas1.setY((-_game.getGameHeight()*3)-1);
        if(_flechas2.getY()>_game.getGameHeight())_flechas2.setY((-_game.getGameHeight()*3)-1);
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
    double _fUpdateTimer=0;
    double _fixedUpdateDelay = 0.02;
    GameObject _flechas1;
    GameObject _flechas2;
    GameObject _backgroundOb;
    boolean _tapAnimUp;
    int[] _colors = { 0xFF41a85f, 0xFF00a885, 0xFF3d8eb9, 0xFF2969b0, 0xFF553982, 0xFF28324e, 0xFFf37934,
            0xFFd14b41, 0xFF75706b};
    int _rndColor;
    Ball[] balls;
    double _ballTimer;
    int nBalls = 20;
    List<GameObject> gameObjects;
    Game _game;
}