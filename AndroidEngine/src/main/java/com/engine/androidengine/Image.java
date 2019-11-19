package com.engine.androidengine;

import android.graphics.Bitmap;

/**
 * Implementa en Android la interfaz Image
 */
public class Image implements com.engine.Image{

    public Image (Bitmap image){ _image=image;}

    /**
     * Devuelve el ancho de la imagen
     * @return ancho de la imagen
     */
    @Override
    public int getWidth() {
        return _image.getWidth();
    }
    /**
     * Devuelve la altura de la imagen
     * @return altura de la imagen
     */
    @Override
    public int getHeight() {
        return _image.getHeight();
    }

    public Bitmap getBitmapImage(){
        return _image;
    }

    Bitmap _image;
}
