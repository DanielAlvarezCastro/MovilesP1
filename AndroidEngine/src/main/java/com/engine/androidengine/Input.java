package com.engine.androidengine;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import static android.view.MotionEvent.ACTION_BUTTON_PRESS;
import static android.view.MotionEvent.ACTION_BUTTON_RELEASE;

public class Input extends com.engine.AbstractInput implements View.OnTouchListener {

	@Override
	public boolean onTouch(View v, MotionEvent event) {


		TouchEvent e = new TouchEvent();

		switch ( event.getAction()){
			case ACTION_BUTTON_PRESS:
				System.out.println("press");
				e.type = EventType.pulsado;
				break;
			case ACTION_BUTTON_RELEASE:
				System.out.println("release");
				e.type = EventType.soltado;
				break;
		}
		e.fingerId = event.getPointerCount();
		e.x = (int)event.getX(0);
		e.y = (int)event.getY(0);
		addEvent(e);
		return false;
	}
}
