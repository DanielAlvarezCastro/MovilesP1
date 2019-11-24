package com.logic;

import com.engine.Game;
import com.engine.GameState;
import com.engine.Graphics;
import com.engine.Input;
import com.engine.Rect;
import com.engine.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Clase de las bolas que caen
class Ball
{
    public GameObject gameObject;
    public boolean color;
}

//Clase de las particulas
class Particle
{
    public GameObject gameObject;
    public int[] velocity;
    public double liveTime;
}

public class PlayState implements GameState {
    public PlayState(Game game){
        _game=game;
        gameObjects = new ArrayList<>();
        _pointsGo = new ArrayList<>();
        particles = new ArrayList<>();
    }

    //Inicializacion de los objetos de la scena
    @Override
    public void init() {

        Random r = new Random();
        _rndColor = r.nextInt((8 - 0) + 1);
        Graphics graphics = _game.getGraphics();


        //Objetos del fondo
        _backgroundOb = new GameObject("background",new Sprite(graphics.newImage("Sprites/backgrounds.png"),
                new Rect(32*_rndColor,0,32,32)),GetGameWidth()/6 , 0, (GetGameWidth()/3)*2, GetGameHeight()*2);
        _flechas1 = new GameObject("arrows1",new Sprite(graphics.newImage("Sprites/arrowsBackground.png"),100),
                GetGameWidth()/6 , 0, (GetGameWidth()/3)*2, GetGameHeight()*2);
        _flechas2 = new GameObject("arrows2",new Sprite(graphics.newImage("Sprites/arrowsBackground.png"),100),
                GetGameWidth()/6 , (-GetGameHeight()*2)-2, (GetGameWidth()/3)*2, GetGameHeight()*2);
        _flechas1.getSprite().setAlpha(50);
        _flechas2.getSprite().setAlpha(50);
        gameObjects.add(_backgroundOb);
        gameObjects.add(_flechas1);
        gameObjects.add(_flechas2);

        //Avatar
        _player = new GameObject("background",new Sprite(graphics.newImage("Sprites/players.png"),
                new Rect(0,0,528,192)),
                GetGameWidth()/2-528/2 , (GetGameHeight()/3)*2, 528, 192);
        gameObjects.add(_player);
        _actualColor = true;

        addPoint();//Llamada para inicializar los objetos de la puntuacion

        //Inicializacion de las dos bolas
        _ballTimer=0;
        balls = new Ball[nBalls];
        //Init balls
        for(int i=0; i < nBalls; i++){
            Ball b = new Ball();
                b.gameObject = new GameObject("ball" + i, new Sprite(graphics.newImage("Sprites/balls.png"),
                        new Rect(0, 128, 128, 128)), (GetGameWidth() / 2) - 50, -500, 100, 100);
            b.gameObject.setActive(false);
            b.color=false;
            balls[i]=b;
            gameObjects.add(balls[i].gameObject);
        }
        //Lanzamos las bolas con una separacion
        throwBall(true);
        throwBall(false);

    }

    //Obtener particula de la pool de particulas
    public Particle getParticle()
    {
        int i=0;
        if(particles.isEmpty()){
            Particle p = new Particle();
            p.gameObject =  new GameObject("ball" + i, new Sprite(_game.getGraphics().newImage("Sprites/balls.png"),
                    new Rect(0, 128, 128, 128)), (GetGameWidth() / 2) - 50, -500, 50, 50);
            particles.add(p);
            p.gameObject.setActive(false);
            gameObjects.add(p.gameObject);
            return p;
        }
        while(i<particles.size()&& particles.get(i).gameObject.getActive()){
            i++;
        }
        if(i<particles.size())return particles.get(i);
        else{
            Particle p = new Particle();
            p.gameObject =  new GameObject("ball" + i, new Sprite(_game.getGraphics().newImage("Sprites/balls.png"),
                    new Rect(0, 128, 128, 128)), (GetGameWidth() / 2) - 50, -500, 50, 50);
            particles.add(p);
            p.gameObject.setActive(false);
            gameObjects.add(p.gameObject);
            return p;
        }
    }

    //Animacion de las particulas
    public void startAnim(){
        for(int i=0; i<animParicles; i++){
            Particle p =getParticle();
            p.gameObject.setActive(true);
            p.gameObject.setY(_player.getY()-50);
            p.gameObject.setX(GetGameWidth()/2);
            Random r = new Random();
            int x = r.nextInt(((1200 - 0) + 1) +0)-600;
            int y = -r.nextInt(((1000 - 500) + 1) + 500);
            p.velocity = new int[2];
            p.velocity[0]=x;
            p.velocity[1]=y;
            p.liveTime =0.5;
            p.gameObject.getSprite().setAlpha(255);

        }
    }

    //Actualizacion de las partículas
    public void updateAnim(double deltatime){
        for (Particle p : particles) {
            if(p.gameObject.getActive()){
                p.gameObject.setX(p.gameObject.getX()+p.velocity[0]*deltatime);
                p.velocity[1] += (int) (deltatime*4000);
                p.gameObject.setY(p.gameObject.getY()+p.velocity[1]*deltatime);
                p.liveTime-=deltatime;
                p.gameObject.getSprite().setAlpha((int)(255*(p.liveTime/0.5)));
                if(p.liveTime<=0){
                    p.gameObject.setActive(false);
                }
            }
        }
    }

    //Cambio de color de la pala
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

    //Update
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
        updateAnim(deltaTime);
    }

    //Control de eventos
    public void handleInput(){
        List<Input.TouchEvent> events = _game.getInput().getTouchEvents();
        for (Input.TouchEvent event : events) {
            if(event.type == Input.EventType.clicked){
                playerSwap();
            }
        }
    }

    //Actualizacion de las bolas
    public  void updateBalls(double deltaTime){
        for (int i =0; i<nBalls; i++){
            if(balls[i].gameObject.getActive())
            {
                balls[i].gameObject.setY(balls[i].gameObject.getY()+(150*deltaTime*_speedDelta));
                if(balls[i].gameObject.getY() > ((GetGameHeight()/3)*2)-100){
                    balls[i].gameObject.setActive(false);
                    if(balls[i].color != _actualColor) _game.setGameState(new GameOverState(_game, _points));
                    else {
                        addPoint();
                        throwBall(false);
                        startAnim();
                    }
                }
            }
        }
    }

    //Actualizacion de la puntuacion
    public void addPoint(){
        _points++;
        int aux = _points;
        int i=0;
        if(_points ==0){
            GameObject number = new GameObject("number", new Sprite(_game.getGraphics().newImage("Sprites/scoreFont.png"),
                    new Rect(125 * (0 + 7), 160 * 3, 125, 160), 255),
                    ((GetGameWidth()-GetGameWidth()/7) + 105) - (210/2 * i) + (60 * (i - 1)), GetGameHeight() / 8, 210 / 2, 140);
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
                                ((GetGameWidth()-GetGameWidth()/7) + 105) - (210/2 * i) + (60 * (i - 1)), GetGameHeight() / 8, 210 / 2, 140);
                        _pointsGo.add(number);
                        gameObjects.add(number);
                    } else {
                        GameObject number = new GameObject("number"+i, new Sprite(_game.getGraphics().newImage("Sprites/scoreFont.png"),
                                new Rect(125 * (n - 8), 160 * 4, 125, 160), 255),
                                ((GetGameWidth()-GetGameWidth()/7) + 105) - (210/2 * i) + (60 * (i - 1)), GetGameHeight() / 8, 210, 140 * 2);
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

    //Arrancar una bola
    public void throwBall(boolean first)
    {
        _ballTimer=0;
        int i=0;
        while(i<nBalls && balls[i].gameObject.getActive()){
            i++;
        }
        balls[i].gameObject.setActive(true);
        if(first) balls[i].gameObject.setY(-(_player.getY()/2)-100);//Si es la primera bola aparece en una posicion especial
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
    //Animacion de las flechas
    public void animateArrows(double deltaTime){
            double y =(150*deltaTime)*_speedDelta;
            _flechas1.setY(_flechas1.getY()+y);
            _flechas2.setY(_flechas2.getY()+y);

            if(_flechas1.getY()>GetGameHeight())_flechas1.setY((_flechas2.getY()-GetGameHeight()*2)-1);
            if(_flechas2.getY()>GetGameHeight())_flechas2.setY((_flechas1.getY()-GetGameHeight()*2)-1);
    }

    //Render
    @Override
    public void render() {

        _game.getGraphics().clear(_colors[_rndColor]);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(_game.getGraphics());
        }

    }

    public int GetGameWidth(){return _game.getGameWidth();}
    public int GetGameHeight(){return  _game.getGameHeight();}

    //Globales de la escena
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
    int animParicles = 20;
    List<GameObject> _pointsGo;
    List<GameObject> gameObjects;
    List<Particle> particles;
    Game _game;
}