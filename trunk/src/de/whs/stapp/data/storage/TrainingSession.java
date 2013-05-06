package de.whs.stapp.data.storage;

import java.util.Date;


/**
 * @author Chris
 * Repräsentiert die Daten einer TrainingSession.
 */
public class TrainingSession {	
	private int sessionId;
	private Date trainingDate;
	private double distanceInMeters;
	private long durationInMs;
	
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
	 * @param durationInMs Die Dauer in Minuten
	 */
	public TrainingSession(int sessionId, Date trainingDate,
			double distanceInMeters, long durationInMs) {
		super();
		this.sessionId = sessionId;
		this.trainingDate = trainingDate;
		this.distanceInMeters = distanceInMeters;
		this.durationInMs = durationInMs;
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
	public double getDistanceInMeters() {
		return distanceInMeters;
	}

	/**
	 * @param distanceInMeters the distanceInMeters to set
	 */
	public void setDistanceInMeters(double distanceInMeters) {
		this.distanceInMeters = distanceInMeters;
	}

	/**
	 * @return the durationInMinutes
	 */
	public long getDurationInMs() {
		return durationInMs;
	}

	/**
	 * @param durationInMs the durationInMinutes to set
	 */
	public void setDurationInMs(long durationInMs) {
		this.durationInMs = durationInMs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TrainingSession [sessionId=" + sessionId + ", trainingDate="
				+ trainingDate + ", distanceInMeters=" + distanceInMeters
				+ ", durationInMs=" + durationInMs + "]";
	}
}