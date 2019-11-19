package com.engine;


public interface Graphics{
    /**
     * Genera un Image dada una ruta con el nombre de la imagen
     * @param name nombre del archivo
     * @return image
     */
    Image newImage(String name);
    /** Devuelve la anchura de la ventana
     * @return anchura de la ventana
     */
    int getWidth();
    /** Devuelve la altura de la ventana
     * @return altura de la ventana
     */
    int getHeight();
    /*
    void drawImage(Image image, int x, int y);
    void drawImage(Image image, Rect source, int x, int y);
    void drawImage(Image image, Rect source, Rect dest);
    */
    /**
     * Dibuja el cuadrado definido por source la imagen en el segmento indicado por source.
     * Mapea directamente los píxeles de la lógica del juego en píxeles de la pantalla
     * @param image Imagen a pintar
     * @param source Rect de la imagen fuente a pintar
     * @param dest Rect con las dimensiones donde se pintará la imagen
     * @param alpha valor de transparencia de la imagen entre 0 y 255
     */
    void drawImage(Image image, Rect source, Rect dest, int alpha);

    /**
     * Limpia la pantalla pintándola con el color indicado
     * @param color valor del color a pintar
     */
    void clear(int color);

}