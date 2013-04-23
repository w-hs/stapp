package de.whs.stapp.data.access;

/**
 * Definiert die verschiedenen Stati 
 * für eine {@link TrainingSession}.
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
