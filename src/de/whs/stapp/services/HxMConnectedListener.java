package de.whs.stapp.services;

import java.util.ArrayList;

import java.util.List;

import de.whs.stapp.helper.Constants;

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

	private List<BTCommunicationListener> listeners = new ArrayList<BTCommunicationListener>();

	
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
					
					if (msg.getMsgID() == Constants.HR_SPD_DIST_PACKET){
					
						byte[] dataArray = msg.getBytes();
						
						BTCommunicationEvent dataCarrier = new BTCommunicationEvent(this);
						
						dataCarrier.setDistance(heartRateSpeedDistancePacket.
								GetDistance(dataArray));
						dataCarrier.setBatteryChargeInd(heartRateSpeedDistancePacket.
								GetBatteryChargeInd(dataArray));
						dataCarrier.setFirmwareId(heartRateSpeedDistancePacket.
								GetFirmwareID(dataArray));
						dataCarrier.setFirmwareVersion(heartRateSpeedDistancePacket.
								GetFirmwareVersion(dataArray));
						dataCarrier.setHardwareId(heartRateSpeedDistancePacket.
								GetHardwareID(dataArray));
						dataCarrier.setHardwareVersion(heartRateSpeedDistancePacket.
								GetHardwareVersion(dataArray));
						dataCarrier.setHeartBeatNumber(heartRateSpeedDistancePacket.
								GetHeartBeatNum(dataArray));
						dataCarrier.setHeartBeatTimestamps(heartRateSpeedDistancePacket.
								GetHeartBeatTS(dataArray));
						dataCarrier.setHeartRate(heartRateSpeedDistancePacket.
								GetHeartRate(dataArray));
						dataCarrier.setSpeed(heartRateSpeedDistancePacket.
								GetInstantSpeed(dataArray));
						dataCarrier.setStrides(heartRateSpeedDistancePacket.
								GetStrides(dataArray));
						
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
	public synchronized void registerListener(BTCommunicationListener listener) {
		listeners.add(listener);
	}

	/**
	 * 
	 * Diese Methode löscht einen Listener aus der Zuhörerliste.
	 * 
	 * @param listener - BTCommunicationListener
	 */
	public synchronized void unregisterListener(BTCommunicationListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}
	
	/**
	 * 
	 * Diese Methode sendet die vom Sensor erhaltenen Daten an die Listener.
	 * 
	 * @param dataCarrier - BTCommunicationEvent
	 */
	private synchronized void notifyBTCommunicationListeners(BTCommunicationEvent dataCarrier) {
		for (BTCommunicationListener listener : listeners) {
			if (listener == null)
				continue;
			listener.getHxMData(dataCarrier);
		}		
	}

}
