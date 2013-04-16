package de.whs.stapp.liveDataTracking;

// TODO, Chris:
// Wurde geschaffen, um eine Mock Implementierung dafür zu schreiben, 
// damit die damit verbundene Logik auch in Unit-Tests geprüft werden
// kann. Fraglich ist nur, ob das nicht einfacher / besser bzw. schöner
// geht...?!

/**
 * @author Chris
 * Definiert eine Schnittstelle um sich an TrackedDataEvent
 * registrieren zukönnen.
 */
public interface BTServiceConnectionRegisterable {
	
	/**
	 * Diese Methode muss implementiert werden um Listener 
	 * registrieren zu können.
	 * @param listener Empfänger der Datenpakete.
	 */
	void registerListener(TrackedDataListener listener);
}
