package de.whs.stapp.data.storage;

import java.util.Date;


/**
 * @author Chris
 * Repräsentiert die Daten einer TrainingSession.
 */
public class TrainingSession {	
	private int sessionId;
	private Date trainingDate;
	private int distanceInMeters;
	private int durationInMinutes;
	
	/**
	 * Erstellt eine neue Instanz der {@link TrainingSession} Klasse.
	 */
	public TrainingSession() {
		
	}
	
	/**
	 * Erstellt eine neue Instanz der {@link TrainingSession} Klasse.
	 * @param sessionId Die Session Id.
	 * @param trainingDate Datum der Session.
	 * @param distanceInMeters Die Distanz in Metern.
	 * @param durationInMinutes Die Dauer in Minuten
	 */
	public TrainingSession(int sessionId, Date trainingDate,
			int distanceInMeters, int durationInMinutes) {
		super();
		this.sessionId = sessionId;
		this.trainingDate = trainingDate;
		this.distanceInMeters = distanceInMeters;
		this.durationInMinutes = durationInMinutes;
	}

	/**
	 * @return the sessionId
	 */
	public int getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the trainingDate
	 */
	public Date getTrainingDate() {
		return trainingDate;
	}

	/**
	 * @param trainingDate the trainingDate to set
	 */
	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	/**
	 * @return the distanceInMeters
	 */
	public int getDistanceInMeters() {
		return distanceInMeters;
	}

	/**
	 * @param distanceInMeters the distanceInMeters to set
	 */
	public void setDistanceInMeters(int distanceInMeters) {
		this.distanceInMeters = distanceInMeters;
	}

	/**
	 * @return the durationInMinutes
	 */
	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	/**
	 * @param durationInMinutes the durationInMinutes to set
	 */
	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
}