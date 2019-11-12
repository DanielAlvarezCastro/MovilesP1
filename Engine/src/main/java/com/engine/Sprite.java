package com.engine;

public class Sprite{


    //Crea un sprite usando la imagen source entera
    public Sprite(Image image){
        _image=image;
        _source = new Rect(0,0, image.getWidth(), image.getHeight());
    }

    //Crea un sprite con un source rect indicado
    public Sprite(Image image, Rect rect){
        _image=image;
        _source=rect;
    }

    public void draw(Graphics g, int x, int y){

        g.drawImage(_image, _source,x,y);

    }

    public void draw(Graphics g, Rect dest){
        g.drawImage(_image, _source, dest);
    }

    /*public void drawCentered(Graphics g, int x, int y){

    }*/

    public int getWidth(){
        return _image.getWidth();
    }
    public int getHeight(){
        return _image.getHeight();
    }

    private Image _image;

    private Rect _source;
}