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

    /**
     * Limpia la pantalla pintándola con el color indicado
     * @param color valor del color a pintar
     */
    @Override
    public void clear(int color){
        _canvas.drawColor(color);
    }
    /** Devuelve la anchura de la ventana
     * @return anchura de la ventana
     */
    @Override
    public int getWidth() {
        return _surfaceView.getWidth();
    }


    /** Devuelve la altura de la ventana
     * @return altura de la ventana
     */
    @Override
    public int getHeight() {
        return _surfaceView.getHeight();
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
    protected void drawImagePrivate(com.engine.Image image, Rect source, Rect dest, int alpha) {

        android.graphics.Rect src = new android.graphics.Rect((int) (source.x), (int) (source.y), (int)(source.x) + source.w, (int)(source.y)+source.h);

        android.graphics.Rect dst = new android.graphics.Rect((int) (dest.x), (int) (dest.y), (int)(dest.x) + dest.w, (int)(dest.y)+dest.h);

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

    /**
     * Método propio de cada plataforma que dibuja un rectángulo con el color indicado en las coordenadas
     * de la pantalla
     * @param dest Rect donde se pintará el rectángulo
     * @param color Valor del color del rectángulo
     */
    @Override
    protected void drawRectPrivate(Rect dest, int color) {
        android.graphics.Rect dst = new android.graphics.Rect((int)(dest.x), (int)(dest.y), dest.w, dest.h);
        Paint paint = new Paint();
        paint.setColor(color);
        _canvas.drawRect(dst,paint);
    }

    /**
     * Genera un Image dada una ruta con el nombre de la imagen
     * @param name nombre del archivo
     * @return image
     */
    @Override
    public Image newImage(String name){
        InputStream inputStream = null;
        Image image = null;

        try {
            //Intenta crear una imagen abriéndola del assetManager
            inputStream = _assetManager.open(name);
            Bitmap _sprite = BitmapFactory.decodeStream(inputStream);
            image = new Image(_sprite);

        }
        catch (IOException e) {
            android.util.Log.e("MainActivity", "Error leyendo el sprite");
        }
        finally {
            // Cerramos el stream.
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch(Exception e) {
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

}
