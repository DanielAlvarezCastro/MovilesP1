package com.engine;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInput implements Input {

    @Override
    synchronized public List<TouchEvent> getTouchEvents(){
        //Crear una lista nueva con el contenido de _list;
        //Vaciar _list.
        //Devolver la copia
        List<TouchEvent> auxList = new ArrayList<>(_list);
        _list.clear();
        return auxList;
    }

    synchronized protected void addEvent (TouchEvent e) {
        //Pasar de coordenadas de la pantalla a coordenadas del juego
        TouchEvent auxEvent = new TouchEvent();
        auxEvent.fingerId = e.fingerId;
        auxEvent.type = e.type;

        int screenW = _game.getGraphics().getWidth();
        int gameW = _game.getGameWidth();

        int screenH = _game.getGraphics().getHeight();
        int gameH = _game.getGameHeight();

        //ratio entre las anchuras
        float ratioW = (float)screenW/ (float)gameW;
        //ratio entre las alturas
        float ratioH = (float)screenH/ (float)gameH;

        //Escogemos el ratio menor
        float ratio = Math.min(ratioW, ratioH);

        //Obtenemos el offset restando la medida que sale con el ratio menor con la que sale con el ratio de su dimensión
        float xOffset =  (Math.abs(gameW*ratio - gameW*ratioW))/2.0f;
        float yOffset = (Math.abs(gameH*ratio - gameH*ratioH))/2.0f;



        //Para obtener las coordenadas del juego restamos los offsets a las coordenadas de la pantalla
        auxEvent.x = (int)((e.x - (int)xOffset)/ratio);
        auxEvent.y = (int)((e.y - (int)yOffset)/ratio);

        synchronized (this) {
            _list.add(auxEvent);
        }

    }

    protected List<TouchEvent> _list = new ArrayList<>();
    protected Game _game;
}
