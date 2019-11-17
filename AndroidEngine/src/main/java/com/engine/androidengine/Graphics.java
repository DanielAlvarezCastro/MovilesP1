package com.engine.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Surface;
import android.view.SurfaceView;
import java.io.IOException;
import java.io.InputStream;

import com.engine.Rect;

public class Graphics extends com.engine.AbstractGraphics{
    public Graphics(SurfaceView surfaceView, AssetManager assetManager, int gameWidth, int gameHeight){
        _surfaceView = surfaceView;
        _assetManager = assetManager;
        _gameWidth=gameWidth;
        _gameHeight = gameHeight;

    }
    @Override
    public int getWidth() {
        return _surfaceView.getWidth();
    }
	
	public void clear(int color){
        //Limpia la pantalla pintándola de un color
        _canvas.drawColor(color);


	}

    @Override
    public int getHeight() {
        return _surfaceView.getHeight();
    }

    @Override
    protected void drawImagePrivate(com.engine.Image image, Rect source, Rect dest, int alpha) {

        android.graphics.Rect src = new android.graphics.Rect(source.x, source.y, source.x + source.w, source.y+source.h);

        android.graphics.Rect dst = new android.graphics.Rect(dest.x, dest.y, dest.x + dest.w, dest.y+dest.h);

        //Solo intenta pintar con alpha si el valor es menor que el máximo
        if(alpha!=255) {
            Paint paint = new Paint();
            paint.setAlpha(alpha);
            _canvas.drawBitmap(((com.engine.androidengine.Image)image).getBitmapImage(), src, dst, paint );
        }
        else {
            _canvas.drawBitmap(((com.engine.androidengine.Image)image).getBitmapImage(), src, dst, null );
        }

    }

    @Override
    public Image newImage(String name){


        InputStream inputStream = null;
        Image image = null;
        try {
            inputStream = _assetManager.open(name);
            Bitmap _sprite = BitmapFactory.decodeStream(inputStream);
            image = new Image(_sprite);

        }
        catch (IOException e) {
            android.util.Log.e("MainActivity", "Error leyendo el sprite");
        }
        finally {
            // Haya pasado lo que haya pasado, cerramos el stream.
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch(Exception e) {
                    // Esto no debería ocurrir nunca... y si ocurre, el
                    // usuario tampoco tiene mucho de qué preocuparse,
                    // ¿para qué molestarle?
                }
                return image;
            } // if (inputStream != null)
            else return null;
        } // try-catch-finally
    }

    public void setCanvas(Canvas c){ _canvas=c;}

    protected SurfaceView _surfaceView;
    protected  AssetManager _assetManager;
    protected Canvas _canvas;

//protected (tipo de Graphics de android) _graphics
}
