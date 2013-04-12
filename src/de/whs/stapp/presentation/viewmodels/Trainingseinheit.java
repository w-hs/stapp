package de.whs.stapp.presentation.viewmodels;

import java.math.BigDecimal;

/**
 * ViewModel für die Ansicht Trainingseinheit.
 * @author Thomas
 *
 */
public class Trainingseinheit extends StappViewModel {
	
	private BigDecimal distance;
	private int heartfrequence;
	
	/**
	 * Konstruktor zum Erzeugen einer Trainingseinheit.
	 * Enthält nur die Daten, die auf dem Gerät angezeigt werden.
	 * @param distance Zurückgelegte Distanz.
	 * @param heartfrequence Aktuelle Herzfrequenz.
	 */
	public Trainingseinheit(BigDecimal distance, int heartfrequence) {
		super();
		this.distance = distance;
		this.heartfrequence = heartfrequence;
	}
	
	/**
	 * Gibt die zurückgelegte Distanz der Trainingseinheit zurück.
	 * @return Zurückgelegte Distanz.
	 */
	public BigDecimal getDistance() {
		return distance;
	}

	/**
	 * Speichert die zurückgelegte Distanz der Trainingseinheit.
	 * @param distance Zurückgelegte Distanz
	 */
	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	/**
	 * Gibt die Herzfrequenz der Trainingseinheit zurück.
	 * @return Aktuelle Herzfrequenz.
	 */
	public int getHeartfrequence() {
		return heartfrequence;
	}

	/**
	 * Speichert die Herzfrequenz der aktuellen Trainingseinheit.
	 * @param heartfrequence Aktuelle Herzfrequenz.
	 */
	public void setHeartfrequence(int heartfrequence) {
		this.heartfrequence = heartfrequence;
	}
}
