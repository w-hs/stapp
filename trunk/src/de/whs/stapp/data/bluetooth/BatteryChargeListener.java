package de.whs.stapp.data.bluetooth;

/**
 * Listener-Schnittstelle, um die Veränderung des Batterie-Ladezustandes
 * zu verfolgen.
 * 
 * @author Fabian
 *
 */
public interface BatteryChargeListener {
	/**
	 * Wird aufgerufen, wenn sich der Ladezustand ändert.
	 * 
	 * @param charge Aktueller Ladezustand.
	 */
	void onChange(int charge);
}
