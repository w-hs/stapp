package de.whs.stapp.data.bluetooth;

/**
 * Diese Klasse dient als Datencontainer für 
 * die vom nsor gelieferten Daten.
 * 
 * @author Dennis Miller
 *
 */
public class TrackedDataItem {

	private String firmwareId;
	private String firmwareVersion;
	private String hardwareId;
	private String hardwareVersion;
	private byte batteryChargeInPercent;
	private byte heartRateInBpm;
	private byte heartBeatNumber;
	private int[] heartBeatTimestamps;
	private double distanceInOne16thsMeter;
	private double speedInOne256thsMeterPerSecond;
	private byte strides;
	
	
	/**
	 * Parameterloser Konstruktor.
	 */	
	public TrackedDataItem() {
	}

	/**
	 * Konstruktor, welcher sofort alle Variablen befüllt.
	 *  
	 * @param firmwareId -
	 * @param firmwareVersion -
	 * @param hardwareId -
	 * @param hardwareVersion -
	 * @param batteryChargeInd -
	 * @param heartRate -
	 * @param heartBeatNumber -
	 * @param heartBeatTimestamps -
	 * @param distance - 
	 * @param speed -
	 * @param strides -
	 */
	//CHECKSTYLE:OFF
	public TrackedDataItem(String firmwareId, String firmwareVersion,
			String hardwareId, String hardwareVersion, byte batteryChargeInd,
			byte heartRate, byte heartBeatNumber, int[] heartBeatTimestamps,
			double distance, double speed, byte strides) 
	//CHECKSTYLE:ON
	{
		this.firmwareId = firmwareId;
		this.firmwareVersion = firmwareVersion;
		this.hardwareId = hardwareId;
		this.hardwareVersion = hardwareVersion;
		this.batteryChargeInPercent = batteryChargeInd;
		this.heartRateInBpm = heartRate;
		this.heartBeatNumber = heartBeatNumber;
		this.heartBeatTimestamps = heartBeatTimestamps;
		this.distanceInOne16thsMeter = distance;
		this.speedInOne256thsMeterPerSecond = speed;
		this.strides = strides;
	}

	/**
	 * 
	 * Getter-Methode für firmwareId.
	 * 
	 * @return firmwareId - FirmwareID
	 */
	public String getFirmwareId() {
		return firmwareId;
	}

	/**
	 * Setter-Methode für firmwareId.
	 * @param firmwareId - Firmware ID
	 */
	public void setFirmwareId(String firmwareId) {
		this.firmwareId = firmwareId;
	}

	/**
	 * Getter-Methode für.
	 * @return firmwareVersion - Firmware Version
	 */
	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	/**
	 * Setter-Methode für firmwareVersion.
	 * @param firmwareVersion - Firmware Version
	 */
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	/**
	 * Getter-Methode für HardwareId.
	 * @return hardwareId - Hardware ID
	 */
	public String getHardwareId() {
		return hardwareId;
	}

	/**
	 * 
	 * Setter-Methode für hardwareId.
	 * 
	 * @param hardwareId
	 *            - Hardware ID
	 */
	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	/**
	 * 
	 * Getter-Methode für hardwareVersion.
	 * 
	 * @return hardwareVersion - Hardware Version
	 */
	public String getHardwareVersion() {
		return hardwareVersion;
	}

	/**
	 * 
	 * Setter-Methode für hardwareVersion.
	 * 
	 * @param hardwareVersion
	 *            - Hardware Version
	 */
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	/**
	 * 
	 * Getter-Methode für batteryChargeInd.
	 * 
	 * @return batteryChargeInd - Batterie Status
	 */
	public byte getBatteryChargeInd() {
		return batteryChargeInPercent;
	}

	/**
	 * 
	 * Setter-Methode für batteryChargeInd.
	 * 
	 * @param batteryChargeInd
	 *            - Batterie Status
	 */
	public void setBatteryChargeInd(byte batteryChargeInd) {
		this.batteryChargeInPercent = batteryChargeInd;
	}

	/**
	 * 
	 * Getter-Methode für heartRate.
	 * 
	 * @return heartRate - Herzfrequenz
	 */
	public byte getHeartRate() {
		return heartRateInBpm;
	}

	/**
	 * 
	 * Setter-Methode für heartRate.
	 * 
	 * @param heartRate
	 *            - Herzfrequenz
	 */
	public void setHeartRate(byte heartRate) {
		this.heartRateInBpm = heartRate;
	}

	/**
	 * 
	 * Getter-Methode für heartBeatNumber.
	 * 
	 * @return heartBeatNumber - Herzschlag-Nummer
	 */
	public byte getHeartBeatNumber() {
		return heartBeatNumber;
	}

	/**
	 * 
	 * Setter-Methode für heartBeatNumber.
	 * 
	 * @param heartBeatNumber
	 *            - Herzschlag-Nummer
	 */
	public void setHeartBeatNumber(byte heartBeatNumber) {
		this.heartBeatNumber = heartBeatNumber;
	}

	/**
	 * 
	 * Getter-Methode für heartBeatTimestamps.
	 * 
	 * @return heartBeatTimestamps[] - Zeitstempel von Herzschlägen
	 */
	public int[] getHeartBeatTimestamps() {
		return heartBeatTimestamps;
	}

	/**
	 * 
	 * Setter-Methode für heartBeatTimestamps.
	 * 
	 * @param heartBeatTimestamps
	 *            - Zeitstempel von Herzschlägen
	 */
	public void setHeartBeatTimestamps(int[] heartBeatTimestamps) {
		this.heartBeatTimestamps = heartBeatTimestamps;
	}

	/**
	 * 
	 * Getter-Methode für distance.
	 * 
	 * @return distance- Distanz
	 */
	public double getDistance() {
		return distanceInOne16thsMeter;
	}

	/**
	 * 
	 * Setter-Methode für distance.
	 * 
	 * @param distance
	 *            - Distanz
	 */
	public void setDistance(double distance) {
		this.distanceInOne16thsMeter = distance;
	}

	/**
	 * 
	 * Getter-Methode für speed.
	 * 
	 * @return speed - Geschwindigkeit
	 */
	public double getSpeed() {
		return speedInOne256thsMeterPerSecond;
	}

	/**
	 * 
	 * Setter-Methode für speed.
	 * 
	 * @param speed
	 *            - Geschwindigkeit
	 */
	public void setSpeed(double speed) {
		this.speedInOne256thsMeterPerSecond = speed;
	}

	/**
	 * 
	 * Getter-Methode für strides.
	 * 
	 * @return strides - Schritte
	 */
	public byte getStrides() {
		return strides;
	}

	/**
	 * 
	 * Setter-Methode für strides.
	 * 
	 * @param strides
	 *            - Schritte
	 */
	public void setStrides(byte strides) {
		this.strides = strides;
	}
	
}
