package de.whs.stapp.liveDataTracking;

import java.util.EventObject;

/**
 * 
 * Diese Klasse dient zum Transport der Daten vom Sensor zu allen registrierten
 * Listenern.
 * 
 * @author Dennis Miller
 */
public class TrackedDataEvent extends EventObject {
	
	private TrackedDataItem dataItem;

	private static final long serialVersionUID = -1722444854946271633L;

	/**
	 * 
	 * Konstruktor.
	 * 
	 * @param source - das Objekt, welches das Event feuert.
	 * @param dataContainer - Container, welcher die Daten enthält.
	 */
	public TrackedDataEvent(Object source, TrackedDataItem dataContainer) {
		super(source);
		dataItem =  dataContainer;
	}

	/**
	 * 
	 * Getter-Methode für den Daten-Container.
	 * 
	 * @return conti - Live-Daten-Container
	 */
	public TrackedDataItem getDataItem() {
		return dataItem;
	}

}
