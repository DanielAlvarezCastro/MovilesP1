package com.engine.androidengine;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class Input extends com.engine.AbstractInput implements View.OnTouchListener {

	public Input(Game game){
		_game = game;
	}

	/**
	 * Cuando Android detecta una pulsación procesa la información del evento
	 * y lo añade a la cola de eventos de input
	 * @param v vista donde se ha detectado el input
	 * @param event información del evento procesado
	 * @return devuelve true cuando añade el evento a la cola
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		TouchEvent e = new TouchEvent();

		switch ( event.getAction()){
			case ACTION_DOWN:

				e.type = EventType.clicked;
				break;
			case ACTION_UP:
				e.type = EventType.released;
				break;
		}
		e.fingerId = event.getPointerCount();
		e.x = (int)event.getX(0);
		e.y = (int)event.getY(0);
		addEvent(e);
		return true;
	}
}
