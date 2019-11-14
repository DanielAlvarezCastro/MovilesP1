package com.engine;

import java.util.List;

/**
 * Interfaz input
 */
public interface Input{
	/**
	 * Devuelve la lista de eventos recibidos desde
	 * la última invocación al método
	 */
	List<TouchEvent> getTouchEvents();

	enum EventType {
		pressed,
		released,
        clicked,
        drag
	}

	//La clase no guarda nada de la plataforma
	class TouchEvent {
		public EventType type;
		public int x;
		public int y;
		public int fingerId;
	}//class TouchEvent

} //interface Input