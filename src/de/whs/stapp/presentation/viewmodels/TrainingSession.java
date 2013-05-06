package de.whs.stapp.presentation.viewmodels;


/**
 * ViewModel für die Ansicht Trainingseinheit.
 * @author Thomas
 *
 */
public class TrainingSession extends StappViewModel {
	
	private String distance;
	private String heartfrequence;
	
	/**
	 * Konstruktor zum Erzeugen einer Trainingseinheit.
	 * Enthält nur die Daten, die auf dem Gerät angezeigt werden.
	 * @param distance Zurückgelegte Distanz.
	 * @param heartfrequence Aktuelle Herzfrequenz.
	 */
	public TrainingSession(int distance, int heartfrequence) {
		super();
		this.distance = Integer.toString(distance);
		this.heartfrequence = Integer.toString(heartfrequence);
	}
	
	/**
	 * Gibt die zurückgelegte Distanz der Trainingseinheit zurück.
	 * @return Zurückgelegte Distanz.
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * Gibt die Herzfrequenz der Trainingseinheit zurück.
	 * @return Aktuelle Herzfrequenz.
	 */
	public String getHeartfrequence() {
		return heartfrequence;
	}
}
