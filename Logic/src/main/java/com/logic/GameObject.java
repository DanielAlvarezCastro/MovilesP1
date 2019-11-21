package com.logic;

import com.engine.Graphics;
import com.engine.Rect;
import com.engine.Sprite;

public class GameObject {

    public GameObject(String name, Sprite sprite, double x, double y, int w, int h){
        _name=name;
        _sprite=sprite;
        _dest = new Rect(x, y, w, h);
        _active = true;
    }

    public GameObject(String name, Sprite sprite, double x, double y){
        _name=name;
        _sprite=sprite;
        _dest = new Rect(x, y, _sprite.getWidth(), _sprite.getHeight());
    }

    public void draw(Graphics g){

        if(_active) _sprite.draw(g, _dest);
    }
    public Sprite getSprite(){
        return _sprite;
    }
    public void setSprite(Sprite sprite){
        _sprite = sprite;
    }

    public boolean within( int x_, int y_){
        return _dest.within(x_,y_);
    }
    public void setActive(boolean active){_active = active;}
    public boolean getActive(){return _active;}
    public double getX(){
        return  _dest.x;
    }
    public double getY(){
        return _dest.y;
    }
    public void setX(double x){
        _dest.x=x;
    }
    public void setY(double y){
        _dest.y=y;
    }
    public void setW(int w){
        _dest.w = w;
    }
    public void setH(int h){
        _dest.h = h;
    }
    private  Boolean _active;
    private String _name;
    private Rect _dest;
    private Sprite _sprite;


}
