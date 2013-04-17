package de.whs.stapp.data.bluetooth;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Diese Klasse ist von Nöten um den BTService zu binden.
 **/
class BTServiceConnection implements ServiceConnection {
	
	private BTCommunicationService btService;
	private ConnectionState connectionState = ConnectionState.Disconnected; 
	
	
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
			connectionState = ConnectionState.Connecting;
			status = btService.connectBT();
		} 
		else
			status = -1;
		
		switch (status) {
		case Constants.BT_NO_ADAPTER_STATUS:
			connectionState = ConnectionState.Disconnected;
			break;
		case Constants.BT_CONNECTION_SUCCESS:
			connectionState = ConnectionState.Connected;
			break;
		case Constants.BT_CONNECTION_FAILURE:
			connectionState = ConnectionState.Disconnected;
			break;
		default:
			connectionState = ConnectionState.Disconnected;
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
	public ConnectionState getConnectionState() {
		return connectionState;
	}

	/**
	 * Deregistrierung listener.
	 * @param listener .
	 */
	public void unregisterListener(TrackedDataListener listener) {
		btService.unregisterListener(listener);		
	}
}