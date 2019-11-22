package com.engine;

public abstract class AbstractGraphics implements Graphics {

    /**
     * Genera un Image dada una ruta con el nombre de la imagen
     * @param name nombre del archivo
     * @return image
     */
    public abstract Image newImage(String name);

    /**
     * Limpia la pantalla pintándola con el color indicado
     * @param color valor del color a pintar
     */
    public abstract void clear(int color);



    /** Devuelve la anchura de la ventana
     * @return anchura de la ventana
     */
    public abstract int getWidth();

    /** Devuelve la altura de la ventana
     * @return altura de la ventana
     */
    public abstract int getHeight();

    /**
     * Dibuja el cuadrado definido por source la imagen en el segmento indicado por source.
     * Mapea directamente los píxeles de la lógica del juego en píxeles de la pantalla
     * @param image Imagen a pintar
     * @param source Rect de la imagen fuente a pintar
     * @param dest Rect con las dimensiones donde se pintará la imagen
     * @param alpha valor de transparencia de la imagen entre 0 y 255
     */
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

    /**
     * Método propio de cada plataforma para pintar una imagen con las coordenadas de la pantalla
     *
     * @param image imagen a pintar
     * @param source Rect de la imagen fuente a pintar
     * @param dest Rect con las dimensiones donde se pintará la imagen
     * @param alpha valor de transparencia de la imagen entre 0 y 255
     */
    protected abstract void drawImagePrivate(Image image, Rect source, Rect dest, int alpha);

    /**
     * Método propio de cada plataforma que dibuja un rectángulo con el color indicado en las coordenadas
     * de la pantalla
     * @param dest Rect donde se pintará el rectángulo
     * @param color Valor del color del rectángulo
     */
    protected abstract void drawRectPrivate(Rect dest, int color);

    protected int _gameWidth;
    protected int _gameHeight;
}
