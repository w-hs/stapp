package de.whs.stapp.presentation.viewmodels;

/**
 * Diese Klasse enthält alle Daten, die in der HistoryView Ansicht als
 * TrainingsSession angezeigt werden.
 * 
 * @author Thomas
 * 
 */
public class Session extends StappViewModel {

	private String date;
	private String distance;
	private long duration;
	private int id;
	
	/**
	 * Standard-Konstruktor des Session-ViewModels.
	 * @param date Datum der Session
	 * @param distance Gelaufene Distanz der Session
	 * @param duration Länge der Session
	 * @param id ID der Session
	 */
	public Session(String date, String distance, long duration, int id) {
		super();
		this.date = date;
		this.distance = distance;
		this.duration = duration;
		this.id = id;
	}

	/**
	 * 
	 * @return Gibt das Datum der Session zurück.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 
	 * @param date Setzt das Datum der Session.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 
	 * @return Gibt die Distanz der Session zurück.
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * 
	 * @param distance Setzt die Distanz der Session.
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * 
	 * @return Holt die Länge der Session.
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * 
	 * @param duration Setzt die Länge der Session
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * 
	 * @return Holt die Id der Session.
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id Setzt die Id der Session.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
}
