package com.engine;

public abstract class AbstractGraphics implements Graphics {
    //Recibe coordenadas lógicas del canvas juego y las transforma en coordenadas de la pantalla
    Image _image;


    public Image newImage(String name){
        return _image;
    };
    public abstract int getWidth();
    public abstract int getHeight();
    /**
     * Dibuja completamente la imagen image en la posición x,y
     * de la pantalla. Mapea cada de la imagen en
     * un pixel de pantalla
     * @param image Imagen a pintar
     * @param x Posición x en la pantalla
     * @param y Posición y en la pantalla
     */
    public void drawImage(Image image, int x, int y){
        //Pasar de cóordenadas juego a coordenadas de pantalla y dibujar
        Rect source = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect dest = new Rect(x, y, image.getWidth(), image.getHeight());
        drawImagePrivate(image, source, dest, 255);
    }
    public void drawImage(Image image, Rect source, int x, int y){
        //Pasar de cóordenadas lógicas a coordenadas de pantalla y dibujar
        Rect dest = new Rect(x, y, image.getWidth(), image.getHeight());
        drawImagePrivate(image, source, dest, 255);
    }

    public void drawImage(Image image, Rect source, Rect dest){
        //Pasar de cóordenadas lógicas a coordenadas de pantalla y dibujar
        drawImagePrivate(image, source, dest, 255);
    }
    public void drawImage(Image image, Rect source, Rect dest, int alpha){

        //Escogemos el ratio menor entre la anchura y altura para usar siempre el mismo valor y
        //que los sprites no se deformen
        float ratio = Math.min((float)getWidth()/ (float)_gameWidth, (float)getHeight()/ (float)_gameHeight);
        //System.out.println("Screen width " + getWidth());
        //System.out.println("Game width " + _gameWidth);
        //System.out.println(ratio);
        Rect screenRect = new Rect();

        float xOffset = (Math.max(getWidth()-_gameWidth, 0))/2;
        float yOffset = (Math.max(getHeight()-_gameHeight, 0))/2;
        //System.out.println("xOffset " + xOffset);
        screenRect.x = (int)(dest.x * ratio + xOffset);
        screenRect.y = (int)(dest.y * ratio + yOffset);
        screenRect.w = (int)(dest.w * ratio);
        screenRect.h = (int)(dest.h * ratio);
        //Pasar de cóordenadas lógicas a coordenadas de pantalla y dibujar
        drawImagePrivate(image, source, screenRect, alpha);
    }

    protected abstract void drawImagePrivate(Image image, Rect source, Rect dest, int alpha);

    protected int _gameWidth;
    protected int _gameHeight;
}
