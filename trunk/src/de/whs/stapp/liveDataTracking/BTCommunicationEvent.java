package de.whs.stapp.liveDataTracking;

import java.util.EventObject;

/**
 * 
 * Diese Klasse dient zum Transport der Daten vom Sensor zu allen registrierten
 * Listenern.
 * 
 * @author Dennis Miller
 */
public class BTCommunicationEvent extends EventObject {
	
	private TrackedDataItem conti;

	private static final long serialVersionUID = -1722444854946271633L;

	/**
	 * 
	 * Konstruktor.
	 * 
	 * @param source - das Objekt, welches das Event feuert.
	 * @param dataContainer - Container, welcher die Daten enthält.
	 */
	public BTCommunicationEvent(Object source, TrackedDataItem dataContainer) {
		super(source);
		conti =  dataContainer;
	}

	/**
	 * 
	 * Getter-Methode für den Daten-Container.
	 * 
	 * @return conti - Live-Daten-Container
	 */
	public TrackedDataItem getConti() {
		return conti;
	}

}
