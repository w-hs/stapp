package de.whs.stapp.data.bluetooth;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

/**
 * Repräsentiert das Bluetooth Device.
 * @author Chris
 */
public class BluetoothDevice implements DataTracker {
	
	private BTServiceConnection connection;

	/**
	 * Erstellt eine neue Instanz der {@link BluetoothDevice} Klasse.
	 */
	public BluetoothDevice() {
		connection = new BTServiceConnection();
	}
	
	/**
	 * Stellt eine Verbindung mit dem Bluetooth-Device (Sensor) her.
	 * @param activity Die Main Activity.
	 * @throws BluetoothException 
	 */
	public void connect(Activity activity) throws BluetoothException {
				
		if (isBTServiceRunning(activity))
			connection.connect();
		else
			bindBluetoothService(activity);		
    }
	
	private void bindBluetoothService(Activity activity) {
		if (activity == null)
			throw new IllegalArgumentException("activity cannot be null");
		
		Intent intent = new Intent(activity, BTCommunicationService.class);
		activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);	
	}
	
	/**
	 * Gibt den Verbindungsstatus zurück.
	 * @return connectionState - Verbindungsstatus
	 */
	public ConnectionState getConnectionState() {
		return connection.getConnectionState();
	}

	@Override
	public void registerListener(TrackedDataListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");
		
		connection.registerListener(listener);		
	}

	@Override
	public void unregisterListener(TrackedDataListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");
		
		connection.unregisterListener(listener);
	}
	
	private boolean isBTServiceRunning(Activity activity) {
	    ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if ("de.whs.stapp.data.bluetooth.BTCommunicationService"
	        		.equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * Diese Methode schaltet Bluetooth ein.
	 * 
	 * @param activity - Die main-activity
	 */
	public void enableBT(Activity activity) {
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null)
			throw new IllegalArgumentException("There is no bluetooth adapter");
		
		if (!btAdapter.isEnabled()){
			Intent enableBtIntent =  new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			activity.startActivity(enableBtIntent);			
		}		
	}
	
	/**
	 * 
	 * Diese Methode schaltet das Bluetooth ab.
	 * Es ist eindringlich empfohlen zuvor in einer Benutzer-Interaktion 
	 * eine Bestätigung einzuholen.
	 * 
	 * @param activity - Die Aufrufende Activity
	 */
	public void disableBT(Activity activity) {
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if (btAdapter.isEnabled()){
			boolean isDisabling = btAdapter.disable();
			
			if (!isDisabling)
				throw new IllegalArgumentException("Error during turning off the bluetooth adapter");
		}
	}
	
	
}
