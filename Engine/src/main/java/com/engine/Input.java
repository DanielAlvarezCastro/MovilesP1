package com.engine;

import java.util.List;

/**
 * Interfaz input
 */
public interface Input{

	//Clase que representa la información de un toque sobre la pantalla
	//Su tipo, su posición y su identificador
    public class TouchEvent{
		
	}
	
	
	public List<TouchEvent> getTouchEvents();

}