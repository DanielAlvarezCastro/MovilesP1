package com.engine.desktopengine;
import com.engine.Image;
import com.engine.Rect;

import javax.swing.JFrame;
import java.awt.*;

public class Graphics extends com.engine.AbstractGraphics{

    public Graphics(JFrame window, int gameWidth, int gameHeight){
        _window=window;
        _gameWidth = gameWidth;
        _gameHeight = gameHeight;

    }



    /**
     * Limpia la pantalla pintándola con el color indicado
     * @param color valor del color a pintar
     */
    @Override
	public void clear(int color){
        //Rellenándola con el color recibido
        _graphics.setColor(new Color(color));
        _graphics.fillRect(0, 0, getWidth(), getHeight());

	}

    /** Devuelve la anchura de la ventana
     * @return anchura de la ventana
     */
    @Override
    public int getWidth(){
        return _window.getWidth();
        }

    /** Devuelve la altura de la ventana
     * @return altura de la ventana
     */
    @Override
    public int getHeight(){
        return _window.getHeight();
    }

    /**
     * Método propio de cada plataforma para pintar una imagen con las coordenadas de la pantalla
     *
     * @param image imagen a pintar
     * @param source Rect de la imagen fuente a pintar
     * @param dest Rect con las dimensiones donde se pintará la imagen
     * @param alpha valor de transparencia de la imagen entre 0 y 255
     */
    @Override
    protected void drawImagePrivate(Image image, Rect source, Rect dest, int alpha) {

        if(alpha!=255){
            float newAlpha = alpha/255.0f;
            Graphics2D g2d = (Graphics2D) _graphics.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(newAlpha));
            g2d.drawImage(((com.engine.desktopengine.Image)image).getAwtImage(), (int)(dest.x), (int)(dest.y), (int)(dest.x)+dest.w,
                    (int)(dest.y)+dest.h, (int)(source.x),(int)(source.y), (int)(source.x) + source.w,(int) (source.y) + source.h, null);
            g2d.dispose();
        }
        else _graphics.drawImage(((com.engine.desktopengine.Image)image).getAwtImage(), (int)(dest.x), (int)(dest.y), (int)(dest.x)+dest.w, (int)(dest.y)+dest.h,
                (int)(source.x),(int)(source.y), (int)(source.x) + source.w,(int)(source.y) + source.h, null);
    }

    /**
     * Método propio de cada plataforma que dibuja un rectángulo con el color indicado en las coordenadas
     * de la pantalla
     * @param dest Rect donde se pintará el rectángulo
     * @param color Valor del color del rectángulo
     */
    @Override
    protected void drawRectPrivate(Rect dest, int color) {
        _graphics.setColor(new Color(color));
        _graphics.fillRect((int)dest.x, (int)dest.y, dest.w, dest.h);
    }

    /**
     * Genera un Image dada una ruta con el nombre de la imagen
     * @param name nombre del archivo
     * @return image
     */
    @Override
    public Image newImage(String name){
        try {
            //Crea una image de java con el nombre del archivo
            //Pasa esta image a la image del desktopengine en la constructora y lo devuelve
            java.awt.Image javaImage = javax.imageio.ImageIO.read(new java.io.File(name));
            com.engine.desktopengine.Image image = new com.engine.desktopengine.Image(javaImage);
            return image;
        }
        catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    //Métodos auxiliares que necesitara el desktopengine

    public void setGraphics(java.awt.Graphics p)
    {
        _graphics=p;
    }

    protected java.awt.Graphics _graphics;
    protected JFrame _window;

}//Graphics