package de.whs.stapp.data.storage;

import java.sql.Timestamp;

/**
 * Repr�sentiert die Details zu einer {@link TrainingSession}.
 * @author Chris
 */
public class SessionDetail {
	private int sessionDetailId;
	private int trainingSessionId;
	private Timestamp timestamp;
	private int heartRateInBpm;
	private float distanceInMeter;
	private float speedInMeterPerSecond;
	private int numberOfStrides;
	
	/**
	 * Erstellt eine neue Instanz der {@link SessionDetail} Klasse.
	 */
	public SessionDetail() {
		
	}
	
	/**
	 * Erstellt eine neue Instanz der {@link SessionDetail} Klasse.
	 * @param sessionDetailId Die SessionDetailId.
	 * @param trainingSessionId Die Id der zugeh�rigen TrainingsSession.
	 * @param timestamp Der Zeitstempel, an dem das SessionDetail aufgenommen wurde.
	 * @param heartRateInBpm Die Herzrate.
	 * @param distanceInMeter Die Distanz in Metern.
	 * @param speedInMeterPerSecond Die Geschwindigkeit in Metern pro Sekunde.
	 * @param numberOfStrides Die Anzahl der zur�ckgelegten Schritte.
	 */
	public SessionDetail(int sessionDetailId, int trainingSessionId,
			Timestamp timestamp, int heartRateInBpm, float distanceInMeter,
			int speedInMeterPerSecond, int numberOfStrides) {
		super();
		this.sessionDetailId = sessionDetailId;
		this.trainingSessionId = trainingSessionId;
		this.timestamp = timestamp;
		this.heartRateInBpm = heartRateInBpm;
		this.distanceInMeter = distanceInMeter;
		this.speedInMeterPerSecond = speedInMeterPerSecond;
		this.numberOfStrides = numberOfStrides;
	}

	/**
	 * @return the sessionDetailId
	 */
	public int getSessionDetailId() {
		return sessionDetailId;
	}

	/**
	 * @param sessionDetailId the sessionDetailId to set
	 */
	public void setSessionDetailId(int sessionDetailId) {
		this.sessionDetailId = sessionDetailId;
	}

	/**
	 * @return the trainingSessionId
	 */
	public int getTrainingSessionId() {
		return trainingSessionId;
	}

	/**
	 * @param trainingSessionId the trainingSessionId to set
	 */
	public void setTrainingSessionId(int trainingSessionId) {
		this.trainingSessionId = trainingSessionId;
	}

	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the heartRateInBpm
	 */
	public int getHeartRateInBpm() {
		return heartRateInBpm;
	}

	/**
	 * @param heartRateInBpm the heartRate to set
	 */
	public void setHeartRateInBpm(int heartRateInBpm) {
		this.heartRateInBpm = heartRateInBpm;
	}

	/**
	 * @return the distanceInMeter
	 */
	public float getDistanceInMeter() {
		return distanceInMeter;
	}

	/**
	 * @param distanceInMeter the distanceInMeter to set
	 */
	public void setDistanceInMeter(float distanceInMeter) {
		this.distanceInMeter = distanceInMeter;
	}

	/**
	 * @return the speedInMeterPerSecond
	 */
	public float getSpeedInMeterPerSecond() {
		return speedInMeterPerSecond;
	}

	/**
	 * @param speedInMeterPerSecond the speedInMeterPerSecond to set
	 */
	public void setSpeedInMeterPerSecond(float speedInMeterPerSecond) {
		this.speedInMeterPerSecond = speedInMeterPerSecond;
	}

	/**
	 * @return the numberOfStrides
	 */
	public int getNumberOfStrides() {
		return numberOfStrides;
	}

	/**
	 * @param numberOfStrides the numberOfStrides to set
	 */
	public void setNumberOfStrides(int numberOfStrides) {
		this.numberOfStrides = numberOfStrides;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SessionDetail [sessionDetailId=" + sessionDetailId
				+ ", trainingSessionId=" + trainingSessionId + ", timestamp="
				+ timestamp + ", heartRateInBpm=" + heartRateInBpm
				+ ", distanceInMeter=" + distanceInMeter
				+ ", speedInMeterPerSecond=" + speedInMeterPerSecond
				+ ", numberOfStrides=" + numberOfStrides + "]";
	}
}
