package com.engine.desktopengine;
import com.engine.Image;
import com.engine.Rect;

import javax.swing.JFrame;
import java.awt.*;

public class Graphics extends com.engine.AbstractGraphics{

    public Graphics(JFrame window){
        _window=window;

        window.setIgnoreRepaint((true));
        window.setVisible(true);

    }


    //Estos métodos adicionales los usará DesktopGame
    public void setGraphics(java.awt.Graphics p)
    {
        _graphics=p;
    }

    @Override
	public void clear(int color){
        //Rellenándola con el color recibido
        _graphics.setColor(new Color(color));
        _graphics.fillRect(0, 0, getWidth(), getHeight());

	}

    @Override
    public int getWidth(){
        return _window.getWidth();
        };
    @Override
    public int getHeight(){
        return _window.getHeight();
    }

    @Override
    protected void drawImagePrivate(Image image, Rect source, Rect dest, int alpha) {

        if(alpha!=255){
            float newAlpha = alpha/255.0f;
            Graphics2D g2d = (Graphics2D) _graphics.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(newAlpha));
            g2d.drawImage(((com.engine.desktopengine.Image)image).getAwtImage(), dest.x, dest.y, dest.x+dest.w, dest.y+dest.h, source.x,source.y, source.x + source.w,source.y + source.h, null);
            g2d.dispose();
        }
        else _graphics.drawImage(((com.engine.desktopengine.Image)image).getAwtImage(), dest.x, dest.y, dest.x+dest.w, dest.y+dest.h, source.x,source.y, source.x + source.w,source.y + source.h, null);
    }

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

    protected java.awt.Graphics _graphics;
    protected JFrame _window;
    java.awt.image.BufferStrategy _strategy;

}//Graphics