package com.engine;

public class Rect{
    public Rect(){}
    public Rect(int x_, int y_, int w_, int h_){
        x=x_;
        y=y_;
        h=h_;
        w=w_;

    }
    public int x;
    public int y;
    public int w;
    public int h;

    public boolean within( int x_, int y_){
        if((x_ >= x && x_ <= x+w)&&(y_ >= y && y_ <= y+h)){
            return  true;
        }
        return false;
    }
}