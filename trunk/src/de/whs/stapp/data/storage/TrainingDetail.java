package de.whs.stapp.data.storage;

/**
 * Details einer Trainingseinheit.
 * @author Chris
 */
public class TrainingDetail {

	private int heartRate;
	private int distanceInMeter;
	private int speedInMeterPerSecond;
	
	/**
	 * Erstell eine neue Instanz der {@link TrainingDetail} Klasse.
	 */
	public TrainingDetail() {
		
	}
	
	/**
	 * @return the heartRate
	 */
	public int getHeartRate() {
		return heartRate;
	}
	/**
	 * @param heartRate the heartRate to set
	 */
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}
	/**
	 * @return the distanceInMeter
	 */
	public int getDistanceInMeter() {
		return distanceInMeter;
	}
	/**
	 * @param distanceInMeter the distanceInMeter to set
	 */
	public void setDistanceInMeter(int distanceInMeter) {
		this.distanceInMeter = distanceInMeter;
	}
	/**
	 * @return the speedInMeterPerSecond
	 */
	public int getSpeedInMeterPerSecond() {
		return speedInMeterPerSecond;
	}
	/**
	 * @param speedInMeterPerSecond the speedInMeterPerSecond to set
	 */
	public void setSpeedInMeterPerSecond(int speedInMeterPerSecond) {
		this.speedInMeterPerSecond = speedInMeterPerSecond;
	}
	
}
