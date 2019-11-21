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
        _pointsGo = new ArrayList<>();
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

        _player = new GameObject("background",new Sprite(graphics.newImage("Sprites/players.png"),
                new Rect(0,0,528,192)),
                screenWidth/2-528/2 , (screenHeight/3)*2, 528, 192);
        gameObjects.add(_player);
        _actualColor = true;
        addPoint();

        _ballTimer=0;
        balls = new Ball[nBalls];
        //Init balls
        for(int i=0; i < nBalls; i++){
            Ball b = new Ball();
                b.gameObject = new GameObject("ball" + i, new Sprite(graphics.newImage("Sprites/balls.png"),
                        new Rect(0, 128, 128, 128)), (_game.getGameWidth() / 2) - 50, -500, 100, 100);
            b.gameObject.setActive(false);
            b.color=false;
            balls[i]=b;
            gameObjects.add(balls[i].gameObject);
        }
        throwBall(true);
        throwBall(false);

    }

    public void playerSwap(){
        if(_actualColor){
            _actualColor=false;
            _player.setSprite(new Sprite(_game.getGraphics().newImage("Sprites/players.png"),
                    new Rect(0,192,528,192)));
        }
        else{
            _actualColor=true;
            _player.setSprite(new Sprite(_game.getGraphics().newImage("Sprites/players.png"),
                    new Rect(0,0,528,192)));
        }
    }

    @Override
    public void update(double deltaTime) {
        _speedTimer+=deltaTime;
        if(_speedTimer > 2 && _speedDelta < 5) {
            _speedTimer =0;
            _speedDelta +=0.10;
        }
        animateArrows(deltaTime);
        updateBalls(deltaTime);
        _ballTimer+=deltaTime;
        handleInput();
    }

    public void handleInput(){
        List<Input.TouchEvent> events = _game.getInput().getTouchEvents();
        for (Input.TouchEvent event : events) {
            if(event.type == Input.EventType.clicked){
                playerSwap();
            }
        }
    }

    public  void updateBalls(double deltaTime){
        for (int i =0; i<nBalls; i++){
            if(balls[i].gameObject.getActive())
            {
                balls[i].gameObject.setY(balls[i].gameObject.getY()+(150*deltaTime*_speedDelta));
                if(balls[i].gameObject.getY() > ((_game.getGameHeight()/3)*2)-100){
                    balls[i].gameObject.setActive(false);
                    if(balls[i].color != _actualColor) _game.setGameState(new GameOverState(_game, _points));
                    else {
                        addPoint();
                        throwBall(false);
                    }
                }
            }
        }
    }

    public void addPoint(){
        _points++;
        int aux = _points;
        int i=0;
        if(_points ==0){
            GameObject number = new GameObject("number", new Sprite(_game.getGraphics().newImage("Sprites/scoreFont.png"),
                    new Rect(125 * (0 + 7), 160 * 3, 125, 160), 255),
                    ((_game.getGameWidth()-_game.getGameWidth()/7) + 105) - (210/2 * i) + (60 * (i - 1)), _game.getGameHeight() / 8, 210 / 2, 140);
            _pointsGo.add(number);
            gameObjects.add(number);
        }
        else{
            while(aux != 0) {
                int n = aux % 10;
                aux = (aux - n) / 10;
                if (_pointsGo.size() <= i) {
                    if (n < 8) {
                        GameObject number = new GameObject("number"+i, new Sprite(_game.getGraphics().newImage("Sprites/scoreFont.png"),
                                new Rect(125 * (n + 7), 160 * 3, 125, 160), 255),
                                ((_game.getGameWidth()-_game.getGameWidth()/7) + 105) - (210/2 * i) + (60 * (i - 1)), _game.getGameHeight() / 8, 210 / 2, 140);
                        _pointsGo.add(number);
                        gameObjects.add(number);
                    } else {
                        GameObject number = new GameObject("number"+i, new Sprite(_game.getGraphics().newImage("Sprites/scoreFont.png"),
                                new Rect(125 * (n - 8), 160 * 4, 125, 160), 255),
                                ((_game.getGameWidth()-_game.getGameWidth()/7) + 105) - (210/2 * i) + (60 * (i - 1)), _game.getGameHeight() / 8, 210, 140 * 2);
                        _pointsGo.add(number);
                        gameObjects.add(number);
                    }
                } else {
                    if (n < 8) {
                        _pointsGo.get(i).setSprite(new Sprite(_game.getGraphics().newImage("Sprites/scoreFont.png"),
                                new Rect(125 * (n + 7), 160 * 3, 125, 160), 255));
                    } else {
                        _pointsGo.get(i).setSprite(new Sprite(_game.getGraphics().newImage("Sprites/scoreFont.png"),
                                new Rect(125 * (n - 8), 160 * 4, 125, 160), 255));
                    }
                }
                i++;
            }
        }
    }

    public void throwBall(boolean first)
    {
        _ballTimer=0;
        int i=0;
        while(i<nBalls && balls[i].gameObject.getActive()){
            i++;
        }
        balls[i].gameObject.setActive(true);
        if(first) balls[i].gameObject.setY(-(_player.getY()/2)-100);
        else balls[i].gameObject.setY(-100);
        Random rnd = new Random();
        int r = rnd.nextInt((1 - 0) + 1);
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
            double y =(150*deltaTime)*_speedDelta;
            _flechas1.setY(_flechas1.getY()+y);
            _flechas2.setY(_flechas2.getY()+y);

            if(_flechas1.getY()>_game.getGameHeight())_flechas1.setY((_flechas2.getY()-_game.getGameHeight()*2)-1);
            if(_flechas2.getY()>_game.getGameHeight())_flechas2.setY((_flechas1.getY()-_game.getGameHeight()*2)-1);
    }


    @Override
    public void render() {

        _game.getGraphics().clear(_colors[_rndColor]);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(_game.getGraphics());
        }

    }

    public int GetGameWidth(){ return _gameWidth;}
    public int GetGameHeight(){ return _gameHeight;}
    int _gameWidth = 1080;
    int _gameHeight = 1920;
    double _speedTimer =0;
    double _speedDelta=1;
    GameObject _flechas1;
    GameObject _flechas2;
    GameObject _backgroundOb;
    GameObject _player;
    boolean _actualColor;
    int[] _colors = { 0xFF41a85f, 0xFF00a885, 0xFF3d8eb9, 0xFF2969b0, 0xFF553982, 0xFF28324e, 0xFFf37934,
            0xFFd14b41, 0xFF75706b};
    int _rndColor;
    Ball[] balls;
    double _ballTimer;
    int nBalls = 2;
    int _points =-1;
    List<GameObject> _pointsGo;
    List<GameObject> gameObjects;
    Game _game;
}