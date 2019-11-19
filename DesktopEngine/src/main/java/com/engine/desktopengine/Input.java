package com.engine.desktopengine;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class Input extends com.engine.AbstractInput implements MouseListener {

	public Input(Game game){
		_game = game;
	}

	/**
	 * Cuando el ratón ha sido clickado copia la información y la añade a la cola de eventos
	 * @param mouseEvent información del evento recibida
	 */
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		TouchEvent e = new TouchEvent();

		e.type = EventType.clicked;
		e.fingerId = mouseEvent.getButton();
		e.x = mouseEvent.getX();
		e.y = mouseEvent.getY();
		addEvent(e);
	}

	/**
	 * Cuando el ratón ha sido pulsado copia la información y la añade a la cola de eventos
	 * @param mouseEvent información del evento recibida
	 */
	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		TouchEvent e = new TouchEvent();

		e.type = EventType.pressed;
		e.fingerId = mouseEvent.getButton();
		e.x = mouseEvent.getX();
		e.y = mouseEvent.getY();
		addEvent(e);
	}

	/**
	 * Cuando el ratón ha sido soltado copia la información y la añade a la cola de eventos
	 * @param mouseEvent información del evento recibida
	 */
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
