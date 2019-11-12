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
        drawImagePrivate(image, source, dest);
    }
    public void drawImage(Image image, Rect source, int x, int y){
        //Pasar de cóordenadas lógicas a coordenadas de pantalla y dibujar
        Rect dest = new Rect(x, y, image.getWidth(), image.getHeight());
        drawImagePrivate(image, source, dest);
    }

    public void drawImage(Image image, Rect source, Rect dest){
        //Pasar de cóordenadas lógicas a coordenadas de pantalla y dibujar
        drawImagePrivate(image, source, dest);
    }
    public void drawImage(Image image, Rect source, Rect dest, int alpha){
        //Pasar de cóordenadas lógicas a coordenadas de pantalla y dibujar
        drawImagePrivate(image, source, dest);
    }

    protected abstract void drawImagePrivate(Image image, Rect source, Rect dest);

}
