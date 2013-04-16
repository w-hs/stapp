package de.whs.stapp.dataAccess;

/**
 * Definiert die verschiedenen Stati, in der sich 
 * eine Trainingseinheit befinden kann.
 * @author Chris
 */
public enum TrainingState {
	/**
	 * Angelegt, aber noch nicht gestartet.
	 */
	NEW,
	/**
	 * Training läuft / findet aktiv statt.
	 */
	RUNNING,
	/**
	 * Training ist pausiert / unterbrochen.
	 */
	PAUSED,
	/**
	 * Training ist abgeschlossen.
	 */
	FINISHED
}
