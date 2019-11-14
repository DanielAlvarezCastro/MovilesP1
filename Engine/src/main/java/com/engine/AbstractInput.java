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
        //Preprocesamient que dura un rato, no bloqueado por el semáforo
        ///..
        //..Bloqueamos el monitor el menor tiempo posible
        synchronized(this){

            _list.add(e);
        }
    }

    protected List<TouchEvent> _list = new ArrayList<>();
}
