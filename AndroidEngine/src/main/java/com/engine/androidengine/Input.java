package com.engine.androidengine;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class Input extends com.engine.AbstractInput implements View.OnTouchListener {

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
		return false;
	}
}
