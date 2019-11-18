package com.engine.desktopengine;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import static java.awt.event.MouseEvent.BUTTON1;

public class Input extends com.engine.AbstractInput implements MouseListener {

	public Input(Game game){
		_game = game;
	}
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		TouchEvent e = new TouchEvent();

		e.type = EventType.clicked;
		e.fingerId = mouseEvent.getButton();
		e.x = mouseEvent.getX();
		e.y = mouseEvent.getY();
		addEvent(e);
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		TouchEvent e = new TouchEvent();

		e.type = EventType.pressed;
		e.fingerId = mouseEvent.getButton();
		e.x = mouseEvent.getX();
		e.y = mouseEvent.getY();
		addEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		TouchEvent e = new TouchEvent();

		e.type = EventType.released;
		e.fingerId = mouseEvent.getButton();
		e.x = mouseEvent.getX();
		e.y = mouseEvent.getY();
		addEvent(e);
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}


}
