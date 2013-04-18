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
	 * Gibt die FirmwareId zurück.
	 * 
	 * @return Die FirmwareId
	 */
	public String getFirmwareId() {
		return firmwareId;
	}

	/**
	 * Setzt die FirmwareId.
	 * 
	 * @param firmwareId - die FirmwareId
	 */
	public void setFirmwareId(String firmwareId) {
		this.firmwareId = firmwareId;
	}

	/**
	 * Gibt die Firmware-Version zurück.
	 * 
	 * @return die FirmwareVersion
	 */
	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	/**
	 * Setzt die Firmware-Version.
	 * 
	 * @param firmwareVersion - die FirmwareVersion
	 */
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	/**
	 * Gibt die HardwareId zurück.
	 * 
	 * @return die HardwareId
	 */
	public String getHardwareId() {
		return hardwareId;
	}

	/**
	 * Setzt die HardwareId.
	 * 
	 * @param hardwareId - die HardwareId
	 */
	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	/**
	 * Gibt die Hardware-Version zurück.
	 * 
	 * @return die HardwareVersion
	 */
	public String getHardwareVersion() {
		return hardwareVersion;
	}

	/**
	 * Setzt die Hardware-Version.
	 * 
	 * @param hardwareVersion - dir HardwareVersion
	 */
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	/**
	 * Gibt den Batterie-Ladestatus zurück.
	 * Die Angabe ist in Prozent. 
	 * Der zulässige Bereich ist somit von 0 bis 100%.
	 * 
	 * @return der Batterie-Ladestatus
	 */
	public byte getBatteryChargeInPercent() {
		return batteryChargeInPercent;
	}

	/**
	 * Setzt den Batterie-Ladestatus.
	 * 
	 * @param batteryChargeInPercent - der Batterie-Ladestatus
	 */
	public void setBatteryChargeInPercent(byte batteryChargeInPercent) {
		this.batteryChargeInPercent = batteryChargeInPercent;
	}

	/**
	 * Gibt die Herzfrequenz zurück.
	 * 
	 * @return die Herzfrequenz
	 */
	public byte getHeartRateInBpm() {
		return heartRateInBpm;
	}

	/**
	 * Setzt die Herzfrequenz.
	 * 
	 * @param heartRateInBpm - die Herzfrequenz
	 */
	public void setHeartRateInBpm(byte heartRateInBpm) {
		this.heartRateInBpm = heartRateInBpm;
	}

	/**
	 * Gibt die Herzschlag-Nummer zurück.
	 * Jeder Herzschlag inkrementiert diese Zahl um 1.
	 * Der zulässige Höchstwert ist 255. Danach wird der Wert
	 * auf 0 zurückgesetzt.
	 * 
	 * @return Herzschlag-Nummer
	 */
	public byte getHeartBeatNumber() {
		return heartBeatNumber;
	}

	/**
	 * Setzt die Herzschlag-Nummer.
	 * 
	 * @param heartBeatNumber - die Herzschlag-Nummer
	 */
	public void setHeartBeatNumber(byte heartBeatNumber) {
		this.heartBeatNumber = heartBeatNumber;
	}

	/**
	 * Gibt ein Array von Timestamps zurück.
	 * Neue Timestamps gibt es so viele wie die Zahl "Herzschlag-Nummer"
	 * groß ist minus die Herzschlag-Nummer des letzten Paketes. 
	 * Die neuen Timestamps beginnen bei dem Index 0. Restliche Timestamps 
	 * - maximal gibt es 16 - gehören den letzten Datenpakete an. 
	 * 
	 * @return Herzschlag-Timestamps
	 */
	public int[] getHeartBeatTimestamps() {
		return heartBeatTimestamps;
	}

	/**
	 * Setzt die Timestamps.
	 * 
	 * @param heartBeatTimestamps - Herzschlag-Timestamps
	 */
	public void setHeartBeatTimestamps(int[] heartBeatTimestamps) {
		this.heartBeatTimestamps = heartBeatTimestamps;
	}

	/**
	 * Gibt die Distanz in 1/16 Meter zurück.
	 * Der gültige Bereich ist 0 bis 4095.
	 * Somit wird der Wert alle 256 Meter zurückgesetzt.
	 * 
	 * @return die Distanz in 1/16 Meter
	 */
	public double getDistanceInOne16thsMeter() {
		return distanceInOne16thsMeter;
	}

	/**
	 * Setzt die Distanz.
	 * 
	 * @param distanceInOne16thsMeter - Distanz in 1/16 Meter
	 */
	public void setDistanceInOne16thsMeter(double distanceInOne16thsMeter) {
		this.distanceInOne16thsMeter = distanceInOne16thsMeter;
	}

	/**
	 * Gibt die momentane Geschwindigkeit in 1/256 m/s zurück.
	 * Der gültige Bereich umfasst 0 bis 4695 bzw. 0 bis 15,966 m/s.
	 * 
	 * @return die Momentangeschwindigkeit
	 */
	public double getSpeedInOne256thsMeterPerSecond() {
		return speedInOne256thsMeterPerSecond;
	}

	/**
	 * Setzt die Momentangeschwindigkeit.
	 * 
	 * @param speedInOne256thsMeterPerSecond - Momentangeschwindigkeit
	 */
	public void setSpeedInOne256thsMeterPerSecond(
			double speedInOne256thsMeterPerSecond) {
		this.speedInOne256thsMeterPerSecond = speedInOne256thsMeterPerSecond;
	}

	//TODO
	/**
	 * Gibt die Anzahl der Schritt zurück.
	 * der gültige Bereich ist 0 bis 255 Schritte.
	 * Der Wert wird alle 128 Schritte zurückgesetzt.
	 * 
	 * @return Die Schrittanzahl
	 */
	public byte getStrides() {
		return strides;
	}

	/**
	 * Setzt die Schrittanzahl.
	 * 
	 * @param strides - die Schrittanzahl
	 */
	public void setStrides(byte strides) {
		this.strides = strides;
	}	
	
}