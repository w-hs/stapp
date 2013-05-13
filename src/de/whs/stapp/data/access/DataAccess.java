package de.whs.stapp.data.access;


/**
 * @author Chris
 * Definiert den Zugriff auf Date.
 */
public interface DataAccess extends StorageAccess {
	
	/**
	 * @return Eine neue {@link Training} Instanz.
	 */
	Training newTraining();
}
