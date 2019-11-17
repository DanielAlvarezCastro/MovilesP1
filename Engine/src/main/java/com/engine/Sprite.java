package com.engine;

public class Sprite{


    //Crea un sprite usando la imagen source entera
    public Sprite(Image image){
        _image=image;
        _source = new Rect(0,0, image.getWidth(), image.getHeight());
        _alpha=255;
    }
    //Crea un sprite usando la imagen source entera y un alpha indicado
    public Sprite(Image image, int alpha){
        _image=image;
        _source = new Rect(0,0, image.getWidth(), image.getHeight());
        _alpha=alpha;
    }

    //Crea un sprite con un source rect indicado
    public Sprite(Image image, Rect rect){
        _image=image;
        _source=rect;
        _alpha=255;
    }
    //Crea un sprite con un source rect indicado y con un alpha
    public Sprite(Image image, Rect rect, int alpha){
        _image=image;
        _source=rect;
        _alpha = alpha;
    }

    public void draw(Graphics g, int x, int y){

        g.drawImage(_image, _source,x,y);

    }

    public void draw(Graphics g, Rect dest){
        g.drawImage(_image, _source, dest, _alpha);
    }

    /*public void drawCentered(Graphics g, int x, int y){

    }*/

    public int getWidth(){
        return _image.getWidth();
    }
    public int getHeight(){
        return _image.getHeight();
    }

    private int _alpha;

    private Image _image;

    private Rect _source;
}