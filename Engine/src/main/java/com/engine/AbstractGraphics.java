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

        //ratio entre las anchuras
        float ratioW = (float)getWidth()/ (float)_gameWidth;
        //ratio entre las alturas
        float ratioH = (float)getHeight()/ (float)_gameHeight;

        //Escogemos el ratio menor entre la anchura y altura para usar siempre el mismo valor y
        //que los sprites no se deformen
        float ratio = Math.min(ratioW, ratioH);

        //Obtenemos el offset restando la medida que sale con el ratio menor con la que sale con el ratio de su dimensión
        float xOffset =  (Math.abs(_gameWidth*ratio - _gameWidth*ratioW))/2.0f;
        float yOffset = (Math.abs(_gameHeight*ratio - _gameHeight*ratioH))/2.0f;

        Rect screenRect = new Rect();

        screenRect.x = (int)(dest.x * ratio + xOffset);
        screenRect.y = (int)(dest.y * ratio + yOffset);
        screenRect.w = (int)(dest.w * ratio);
        screenRect.h = (int)(dest.h * ratio);
        //Pasar de cóordenadas lógicas a coordenadas de pantalla y dibujar
        drawImagePrivate(image, source, screenRect, alpha);

        //Pinta las bandas negras
        Rect leftBar = new Rect (0,0,(int)xOffset, getHeight());
        drawRectPrivate(leftBar, 0);

        Rect rightBar = new Rect (getWidth()-(int)xOffset,0,(int)xOffset, getHeight());
        drawRectPrivate(rightBar, 0);

        Rect upBar = new Rect (0,0, getWidth(), (int)yOffset);
        drawRectPrivate(upBar, 0);

        Rect downBar = new Rect (0,getHeight() - (int)yOffset, getWidth(), (int)yOffset);
        drawRectPrivate(downBar, 0);



    }

    protected abstract void drawImagePrivate(Image image, Rect source, Rect dest, int alpha);
    protected abstract void drawRectPrivate(Rect dest, int color);

    protected int _gameWidth;
    protected int _gameHeight;
}
