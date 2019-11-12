package com.engine.androidengine;

import android.graphics.Bitmap;

public class Image implements com.engine.Image{

    public Image (Bitmap image){ _image=image;}
    @Override
    public int getWidth() {
        return _image.getWidth();

    }

    @Override
    public int getHeight() {
        return _image.getHeight();

    }

    public Bitmap getBitmapImage(){
        return _image;
    }

    Bitmap _image;
}
