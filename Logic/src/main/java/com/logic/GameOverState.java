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

public class GameOverState implements GameState {
    public GameOverState(Game game, int points){
        _game=game;
        gameObjects = new ArrayList<>();
        _points = points;
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
        gameObjects.add(_backgroundOb);
        _flechas1 = new GameObject("arrows1",new Sprite(graphics.newImage("Sprites/arrowsBackground.png"),100),
                GetGameWidth()/6 , 0, (GetGameWidth()/3)*2, GetGameHeight()*2);
        _flechas2 = new GameObject("arrows2",new Sprite(graphics.newImage("Sprites/arrowsBackground.png"),100),
                GetGameWidth()/6 , (-GetGameHeight()*2)-2, (GetGameWidth()/3)*2, GetGameHeight()*2);
        _flechas1.getSprite().setAlpha(50);
        _flechas2.getSprite().setAlpha(50);

        //Logo e interfaz
        GameObject gameOverSprite = new GameObject("gameOverSprite",new Sprite(graphics.newImage("Sprites/gameOver.png"),255),
                (GetGameWidth()/2)-(252/2) , GetGameHeight()/5, 252, 208);
        GameObject muteButton = new GameObject("muteButton",new Sprite(graphics.newImage("Sprites/buttons.png"),
                new Rect(140*2, 0, 140, 140), 255),
                (GetGameWidth()/12)-(140/2) , GetGameHeight()/8, 140, 140);
        _helpButton = new GameObject("helpButton",new Sprite(graphics.newImage("Sprites/buttons.png"),
                new Rect(0, 0, 140, 140), 255),
                GetGameWidth()-((GetGameWidth()/12)+(140/2)) , GetGameHeight()/8, 140, 140);
        tapSprite = new GameObject("tapSprite",new Sprite(graphics.newImage("Sprites/playAgain.png"),255),
                (GetGameWidth()/2)-(532/2) , (GetGameHeight()/3)*2, 532, 72);

        //Inicializacion de la puntuacion
        for(int i =0; i< 3; i++){
            int n =_points%10;
            _points = (_points-n)/10;
            if(n<8){
            GameObject number = new GameObject("number"+i,new Sprite(graphics.newImage("Sprites/scoreFont.png"),
                    new Rect(125*(n+7), 160*3, 125, 160), 255),
                    ((GetGameWidth()/2)+105)-(210*i)+(60*(i-1)) , GetGameHeight()/3, 210, 140*2);
                gameObjects.add(number);
            }
            else{
                GameObject number = new GameObject("number"+i,new Sprite(graphics.newImage("Sprites/scoreFont.png"),
                        new Rect(125*(n-8), 160*4, 125, 160), 255),
                        ((GetGameWidth()/2)+105)-(210*i)+(60*(i-1)) , GetGameHeight()/3, 210, 140*2);
                gameObjects.add(number);
            }
        }

        //Objetos de la palabra POINTS
        GameObject p = new GameObject("p",new Sprite(graphics.newImage("Sprites/scoreFont.png"),
                new Rect(125*0, 160*1, 125, 160), 255),
                ((GetGameWidth()/3)+(GetGameWidth()/18)*0) , (GetGameHeight()/3)+290, GetGameWidth()/18, 210/2);
        GameObject o = new GameObject("o",new Sprite(graphics.newImage("Sprites/scoreFont.png"),
                new Rect(125*14, 160*0, 125, 160), 255),
                ((GetGameWidth()/3)+(GetGameWidth()/18)*1) , (GetGameHeight()/3)+290, GetGameWidth()/18, 210/2);
        GameObject i = new GameObject("i",new Sprite(graphics.newImage("Sprites/scoreFont.png"),
                new Rect(125*8, 160*0, 125, 160), 255),
                ((GetGameWidth()/3)+(GetGameWidth()/18)*2) , (GetGameHeight()/3)+290, GetGameWidth()/18, 210/2);
        GameObject n = new GameObject("n",new Sprite(graphics.newImage("Sprites/scoreFont.png"),
                new Rect(125*13, 160*0, 125, 160), 255),
                ((GetGameWidth()/3)+(GetGameWidth()/18)*3) , (GetGameHeight()/3)+290, GetGameWidth()/18, 210/2);
        GameObject t = new GameObject("t",new Sprite(graphics.newImage("Sprites/scoreFont.png"),
                new Rect(125*4, 160*1, 125, 160), 255),
                ((GetGameWidth()/3)+(GetGameWidth()/18)*4) , (GetGameHeight()/3)+290, GetGameWidth()/18, 210/2);
        GameObject s = new GameObject("s",new Sprite(graphics.newImage("Sprites/scoreFont.png"),
                new Rect(125*3, 160*1, 125, 160), 255),
                ((GetGameWidth()/3)+(GetGameWidth()/18)*5) , (GetGameHeight()/3)+290, GetGameWidth()/18, 210/2);


        //Añadimos los objetos a la lista de GOs
        _tapAnimUp = false;
        gameObjects.add(_flechas1);
        gameObjects.add(_flechas2);
        gameObjects.add(gameOverSprite);
        gameObjects.add(muteButton);
        gameObjects.add(_helpButton);
        gameObjects.add(tapSprite);
        gameObjects.add(p);
        gameObjects.add(o);
        gameObjects.add(i);
        gameObjects.add(n);
        gameObjects.add(t);
        gameObjects.add(s);

    }

    //Update
    @Override
    public void update(double deltaTime) {
        _fUpdateTimer+=deltaTime;
        if(_fUpdateTimer>=_fixedUpdateDelay) animateTap();
        animateArrows(deltaTime);
        handleInput();
    }

    ////Control de eventos
    public void handleInput(){
        List<Input.TouchEvent> events = _game.getInput().getTouchEvents();
        for (Input.TouchEvent event : events) {
            if(event.type == Input.EventType.clicked){
                if(_helpButton.within(event.x, event.y))_game.setGameState(new HowToState(_game));
                else if(_backgroundOb.within(event.x, event.y))_game.setGameState(new PlayState(_game));
            }
        }
    }

    //Animación del "Pulse la pantalla"
    public void animateTap(){
        _fUpdateTimer-=_fixedUpdateDelay;
        if(_tapAnimUp) {
            tapSprite.getSprite().setAlpha(tapSprite.getSprite().getAlpha()+5);//Bajar alfa
            if(tapSprite.getSprite().getAlpha() >= 255) _tapAnimUp=false;
        }
        else{
            tapSprite.getSprite().setAlpha(tapSprite.getSprite().getAlpha()-5);//Subir alfa
            if(tapSprite.getSprite().getAlpha() <= 0) _tapAnimUp=true;
        }
    }

    //Anmación de las flechas
    public void animateArrows(double deltaTime){
        double y =(150*deltaTime);
        _flechas1.setY(_flechas1.getY()+y);
        _flechas2.setY(_flechas2.getY()+y);

        if(_flechas1.getY()>GetGameHeight())_flechas1.setY((_flechas2.getY()-GetGameHeight()*2)-2);
        if(_flechas2.getY()>GetGameHeight())_flechas2.setY((_flechas1.getY()-GetGameHeight()*2)-2);
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
    int _points;
    double _fUpdateTimer=0;
    double _fixedUpdateDelay = 0.02;
    GameObject _flechas1;
    GameObject _flechas2;
    GameObject tapSprite;
    GameObject _backgroundOb;
    GameObject _helpButton;
    boolean _tapAnimUp;
    int[] _colors = { 0xFF41a85f, 0xFF00a885, 0xFF3d8eb9, 0xFF2969b0, 0xFF553982, 0xFF28324e, 0xFFf37934,
            0xFFd14b41, 0xFF75706b};
    int _rndColor;
    List<GameObject> gameObjects;
    Game _game;
}