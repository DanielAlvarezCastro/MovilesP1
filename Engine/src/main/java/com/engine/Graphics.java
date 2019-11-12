package com.engine;


public interface Graphics{
    public Image newImage(String name);
    public int getWidth();
    public int getHeight();
    /**
     * Dibuja completamente la imagen image en la posición x,y
     * de la pantalla. Mapea cada de la imagen en
     * un pixel de pantalla
     * @param image Imagen a pintar
     * @param x Posición x en la pantalla
     * @param y Posición y en la pantalla
     */
    public void drawImage(Image image, int x, int y);
    public void drawImage(Image image, Rect source, int x, int y);
    public void drawImage(Image image, Rect source, Rect dest);
    public void drawImage(Image image, Rect source, Rect dest, int alpha);
    public void clear(int color);

}