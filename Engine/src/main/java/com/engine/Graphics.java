package com.engine;


public interface Graphics{
    Image newImage(String name);
    int getWidth();
    int getHeight();
    /**
     * Dibuja completamente la imagen image en la posición x,y
     * de la pantalla. Mapea cada de la imagen en
     * un pixel de pantalla
     * @param image Imagen a pintar
     * @param x Posición x en la pantalla
     * @param y Posición y en la pantalla
     */
    void drawImage(Image image, int x, int y);
    void drawImage(Image image, Rect source, int x, int y);
    void drawImage(Image image, Rect source, Rect dest);
    void drawImage(Image image, Rect source, Rect dest, int alpha);
    void clear(int color);

}