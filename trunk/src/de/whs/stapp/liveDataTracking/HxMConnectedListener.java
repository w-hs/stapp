package de.whs.stapp.liveDataTracking;

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
class HxMConnectedListener extends ConnectListenerImpl {

	private List<TrackedDataListener> listeners = new ArrayList<TrackedDataListener>();

	
	private HRSpeedDistPacketInfo heartRateSpeedDistancePacket =
			new HRSpeedDistPacketInfo();

	/**
	 * Konstruktor.
	 */
	public HxMConnectedListener() {
		super(null, null);
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
	public void Connected(ConnectedEvent<BTClient> eventArgs) {
		Log.d("HxMConnectedListener", String.format
				("Connected to HxM %s.", eventArgs.getSource().
						getDevice().getName()));
	

		// Creates a new ZephyrProtocol object and passes it the BTComms object
		ZephyrProtocol protocol = 
				new ZephyrProtocol(eventArgs.getSource().getComms());
		
		protocol.addZephyrPacketEventListener(new ZephyrPacketListener() {
			public void ReceivedPacket(ZephyrPacketEvent eventArgs) {
				if(eventArgs != null && eventArgs.getPacket() != null) {
					ZephyrPacketArgs msg = eventArgs.getPacket();
					
					byte cRCFailStatus = msg.getCRCStatus();
					byte rcvdBytes = msg.getNumRvcdBytes();
					
					if (msg.getMsgID() == BTConstants.HR_SPD_DIST_PACKET){
					
						byte[] dataArray = msg.getBytes();
						
						TrackedDataItem dataContainer = new TrackedDataItem();
						
						dataContainer.setDistance(heartRateSpeedDistancePacket.
								GetDistance(dataArray));
						dataContainer.setBatteryChargeInd(heartRateSpeedDistancePacket.
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
						dataContainer.setHeartRate(heartRateSpeedDistancePacket.
								GetHeartRate(dataArray));
						dataContainer.setSpeed(heartRateSpeedDistancePacket.
								GetInstantSpeed(dataArray));
						dataContainer.setStrides(heartRateSpeedDistancePacket.
								GetStrides(dataArray));
						
						TrackedDataEvent dataCarrier = 
								new TrackedDataEvent(this, dataContainer); 
						
						notifyBTCommunicationListeners(dataCarrier);
						
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
	private synchronized void notifyBTCommunicationListeners(TrackedDataEvent dataCarrier) {
		for (TrackedDataListener listener : listeners) {
			if (listener == null)
				continue;
			listener.getTrackedData(dataCarrier);
		}		
	}

}
