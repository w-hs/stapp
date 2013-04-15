package de.whs.stapp.liveDataTracking;

/**
 * .
 * @author Dennis
 */
public interface BTServiceConnectionRegisterable {
	/**
	 * Diese Methode muss implementiert werden um 
	 * Listener registrieren zu können.
	 * @param listener - Empfänger der Datenpakete.
	 */
	void registerListener(TrackedDataListener listener);
}
