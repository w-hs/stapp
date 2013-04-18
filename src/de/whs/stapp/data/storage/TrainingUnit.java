package de.whs.stapp.data.storage;

import java.util.Date;


/**
 * @author Chris
 * Repräsentiert die Daten einer Trainingseinheit.
 */
public class TrainingUnit {
	
	protected int trainingUnitId;	
	protected int distanceInMeters;
	protected long durationInMinutes;
	protected Date trainingDate;
	
	/**
	 * @return the trainingUnitId
	 */
	public int getTrainingUnitId() {
		return trainingUnitId;
	}
	/**
	 * @param trainingUnitId the trainingUnitId to set
	 */
	public void setTrainingUnitId(int trainingUnitId) {
		this.trainingUnitId = trainingUnitId;
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
	public long getDurationInMinutes() {
		return durationInMinutes;
	}
	/**
	 * @param durationInMinutes the durationInMinutes to set
	 */
	public void setDurationInMinutes(long durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
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
}