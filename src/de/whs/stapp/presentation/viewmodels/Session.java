package de.whs.stapp.presentation.viewmodels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Diese Klasse enthält alle Daten, die in der HistoryView Ansicht als
 * TrainingsSession angezeigt werden.
 * 
 * @author Thomas
 * 
 */
public class Session extends StappViewModel implements Comparable<Session> {

	private static final DateFormat DATE_FORMATER = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);

	private String date;
	private Date fullDate;
	private int distance;
	private long duration;
	private int id;

	/**
	 * Standard-Konstruktor des Session-ViewModels.
	 * 
	 * @param date
	 *            Datum der Session
	 * @param distance
	 *            Gelaufene Distanz der Session
	 * @param duration
	 *            Länge der Session
	 * @param id
	 *            ID der Session
	 */
	public Session(Date date, int distance, long duration, int id) {
		super();
		this.fullDate = date;
		this.date = DATE_FORMATER.format(date);
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
	 * @param date
	 *            Setzt das Datum der Session.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 
	 * @return Gibt die Distanz der Session zurück.
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * 
	 * @param distance
	 *            Setzt die Distanz der Session.
	 */
	public void setDistance(int distance) {
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
	 * @param duration
	 *            Setzt die Länge der Session
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
	 * @param id
	 *            Setzt die Id der Session.
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(Session another) {

		return another.fullDate.compareTo(fullDate);
	}

}
