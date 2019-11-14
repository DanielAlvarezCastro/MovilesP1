package com.logic;

import com.engine.Graphics;
import com.engine.Rect;
import com.engine.Sprite;

public class GameObject {

    public GameObject(String name, Sprite sprite, int x, int y, int w, int h){
        _name=name;
        _sprite=sprite;
        _dest = new Rect(x, y, w, h);
        _active = true;
    }

    public GameObject(String name, Sprite sprite, int x, int y){
        _name=name;
        _sprite=sprite;
        _dest = new Rect(x, y, _sprite.getWidth(), _sprite.getHeight());
    }

    public void draw(Graphics g){

        if(_active) _sprite.draw(g, _dest);
    }
    public int getX(){
        return  _dest.x;
    }
    public int getY(){
        return _dest.y;
    }
    public void setX(int x){
        _dest.x=x;
    }
    public void setY(int y){
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
