package de.whs.stapp.liveDataTracking;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Diese Klasse ist von Nöten um den BTService zu binden.
 **/
public class BTServiceConnection implements ServiceConnection,
			BTServiceConnectionRegisterable {
	
	private BTCommunicationService btService;
	private BTState connectionState = BTState.Disconnected; 
	
	
	/**
	 * Diese Methode wird aufgerufen, wenn der Service gebunden wurde.
	 * 
	 * @param className - Klassenname
	 * @param service - Service Binder
	 */
	public void onServiceConnected(ComponentName className, IBinder service) {
		BTServiceBinder serviceBinder = (BTServiceBinder) service; 
		btService = serviceBinder.getService();
	}
	
	 /**
	  * Diese Methode wird aufgerufen, wenn der Verbund mit 
	  * dem Service aufgelöst wurde.
	  * 
	  * @param arg0 - Komponentenname
	  */
	public void onServiceDisconnected(ComponentName arg0) {
	}
	
	/**
	 * Diese Methode versucht eine Bluetoothverbindung herzustellen
	 * und meldet den anschließenden BT-Status.
	 */
	public void connect() {
		int status;
		
		if (btService != null) {
			connectionState = BTState.Connecting;
			status = btService.connectBT();
		} 
		else
			status = -1;
		
		switch (status) {
		case BTConstants.BT_NO_ADAPTER_STATUS:
			connectionState = BTState.Disconnected;
			break;
		case BTConstants.BT_CONNECTION_SUCCESS:
			connectionState = BTState.Connected;
			break;
		case BTConstants.BT_CONNECTION_FAILURE:
			connectionState = BTState.Disconnected;
			break;
		default:
			connectionState = BTState.Disconnected;
			break;	
		}
	}
	
	/**
	 * Diese Methode registriert einen Listener,  der die Sensor-Daten erhalten möchte.
	 * @param listener - Ein Listener, der die Datenpakete des Sensors empfangen möchte.
	 */
	public void registerListener(TrackedDataListener listener){
		if (btService != null) 
			btService.registerHxMListener(listener);
	}

	/**
	 * Get-Methode für den Verbindungsstatus.
	 * @return connectionState - Verbindungsstatus
	 */
	public BTState getConnectionState() {
		return connectionState;
	}
}