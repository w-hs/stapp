package de.whs.stapp.data.bluetooth;


/**
 * @author Chris
 * Definiert eine Schnittstelle um sich an TrackedDataEvent
 * registrieren zukönnen.
 */
public interface DataTracker {
	
	/**
	 * Diese Methode muss implementiert werden um Listener 
	 * registrieren zu können.
	 * @param listener Empfänger der Datenpakete.
	 */
	void registerListener(TrackedDataListener listener);
	
	/**
	 * Entfernt einen bereits registrierten Listener.
	 * @param listener Der listener.
	 */
	void unregisterListener(TrackedDataListener listener);	
}
