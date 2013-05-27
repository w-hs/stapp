package de.whs.stapp.data.bluetooth;

import java.util.ArrayList;
import java.util.List;


import zephyr.android.HxMBT.BTClient;
import zephyr.android.HxMBT.ConnectListenerImpl;
import zephyr.android.HxMBT.ConnectedEvent;
import zephyr.android.HxMBT.ZephyrPacketArgs;
import zephyr.android.HxMBT.ZephyrPacketEvent;
import zephyr.android.HxMBT.ZephyrPacketListener;
import zephyr.android.HxMBT.ZephyrProtocol;
import android.util.Log;

/**
 * Diese Klasse nimmt die Pakete des HxM-Sensors, die via 
 * Bluetooth eingehen, entgegen und verteilt sie an alle 
 * registrierten Listeners. 
 */
class HxMConnectedListener extends ConnectListenerImpl implements DataTracker {

	private final int heartRateSpeedDistancePacketIndex = 0x26;
	private List<TrackedDataListener> listeners = new ArrayList<TrackedDataListener>();
		
	private HRSpeedDistPacketInfo heartRateSpeedDistancePacket =
			new HRSpeedDistPacketInfo();
	private int batteryCharge;
	private BatteryChargeListener chargeListener;

	/**
	 * Konstruktor.
	 */
	public HxMConnectedListener() {
		super(null, null);
		batteryCharge = -1;
	}
	
	public void setBatteryChargeListener(BatteryChargeListener listener) {
		chargeListener = listener;
	}
	
	public int getLastBatteryCharge() {
		return batteryCharge;
	}

	/**
	 * Diese Methode wird aufgerufen, wenn eine 
	 * Verbindung mit dem HxM-Sensor hergestellt wurde.
	 * In der Methode wird der PacketListener hinzugefügt,
	 * um Daten vom Sensor zu empfangen.
	 * 
	 * @param eventArgs -
	 */
	@Override 
	//CHECKSTYLE:OFF
	public void Connected(ConnectedEvent<BTClient> eventArgs) {
	//CHECKSTYLE:ON
		Log.d("HxMConnectedListener", String.format
				("Connected to HxM %s.", eventArgs.getSource().
						getDevice().getName()));
	

		// Creates a new ZephyrProtocol object and passes it the BTComms object
		ZephyrProtocol protocol = 
				new ZephyrProtocol(eventArgs.getSource().getComms());
		
		protocol.addZephyrPacketEventListener(new ZephyrPacketListener() {
			//CHECKSTYLE:OFF
			public void ReceivedPacket(ZephyrPacketEvent eventArgs) {
			//CHECKSTYLE:ON
				if(eventArgs != null && eventArgs.getPacket() != null) {
					ZephyrPacketArgs msg = eventArgs.getPacket();
							
					if (msg.getMsgID() == heartRateSpeedDistancePacketIndex){
					
						byte[] dataArray = msg.getBytes();
						
						TrackedDataItem dataContainer = new TrackedDataItem();
						
						dataContainer.setDistanceInMeter(heartRateSpeedDistancePacket.
								GetDistance(dataArray));
						dataContainer.setBatteryChargeInPercent(heartRateSpeedDistancePacket.
								GetBatteryChargeInd(dataArray));
						dataContainer.setFirmwareId(heartRateSpeedDistancePacket.
								GetFirmwareID(dataArray));
						dataContainer.setFirmwareVersion(heartRateSpeedDistancePacket.
								GetFirmwareVersion(dataArray));
						dataContainer.setHardwareId(heartRateSpeedDistancePacket.
								GetHardwareID(dataArray));
						dataContainer.setHardwareVersion(heartRateSpeedDistancePacket.
								GetHardwareVersion(dataArray));
						dataContainer.setHeartBeatNumber(heartRateSpeedDistancePacket.
								GetHeartBeatNum(dataArray));
						dataContainer.setHeartBeatTimestamps(heartRateSpeedDistancePacket.
								GetHeartBeatTS(dataArray));
						
						byte heartBeat = heartRateSpeedDistancePacket.GetHeartRate(dataArray);
						dataContainer.setHeartRateInBpm(makeUnsigned(heartBeat));
						
						dataContainer.setSpeedInMeterPerSecond(
								heartRateSpeedDistancePacket.
								GetInstantSpeed(dataArray));
						
						byte strides = heartRateSpeedDistancePacket.GetStrides(dataArray);
						dataContainer.setStrides(makeUnsigned(strides));
						
						notifyBTCommunicationListeners(dataContainer);
						
						// Ladezustand des Sensors auslesen
						if (chargeListener != null)
						{
							int newCharge = heartRateSpeedDistancePacket.GetBatteryChargeInd(dataArray);
							if (newCharge != batteryCharge || batteryCharge == -1) {
								batteryCharge = newCharge;
								chargeListener.onChange(batteryCharge);
							}
						}
					}
										
				}
			}
		});
	}

	/**
	 *
	 *Diese Methode registriert einen Listener, welcher die
	 * Sensordaten empfangen möchte.
	 * 
	 * @param listener - BTCommunicationListener
	 */
	public synchronized void registerListener(TrackedDataListener listener) {
		listeners.add(listener);
	}

	/**
	 * 
	 * Diese Methode löscht einen Listener aus der Zuhörerliste.
	 * 
	 * @param listener - BTCommunicationListener
	 */
	public synchronized void unregisterListener(TrackedDataListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}
	
	/**
	 * 
	 * Diese Methode sendet die vom Sensor erhaltenen Daten an die Listener.
	 * 
	 * @param dataCarrier - BTCommunicationEvent
	 */
	private synchronized void notifyBTCommunicationListeners(TrackedDataItem dataItem) {
		for (TrackedDataListener listener : listeners) {
			if (listener == null)
				continue;
			listener.trackData(dataItem);
		}		
	}
	
	/**
	 * Interpretiert ein 8-Bit Byte als unsigned. 
	 * 
	 * Das ist notwendig, da Java keine unsigned Typen kennt.
	 * 
	 * @param signed 8-Bit Wert.
	 * @return Als unsigned interpretierter Wert.
	 */
	private static int makeUnsigned(byte signed) {
		int result = signed;
		result = result & 0xFF;
		return result;
	}

}
