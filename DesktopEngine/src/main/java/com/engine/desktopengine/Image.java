package com.engine.desktopengine;
//import com.example.engine.Image;
/**
 * implementa la interfaz Image
 */

public class Image implements com.engine.Image{

    public Image(java.awt.Image image){
            _image = image;
        }

    /**
     * Devuelve el ancho de la imagen.
     *
     * @return Ancho de la imagen.
     */

    @Override
    public int getWidth(){
        return _image.getWidth(null);
    };
    @Override
    public int getHeight(){
        return _image.getHeight(null);
    };


    public  java.awt.Image getAwtImage(){
        return  _image;
    }
    private java.awt.Image _image;
}