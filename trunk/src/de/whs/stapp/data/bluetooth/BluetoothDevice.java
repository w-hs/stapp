package de.whs.stapp.data.bluetooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Repräsentiert das Bluetooth Device.
 * @author Chris
 */
public class BluetoothDevice implements DataTracker {
	
	// TODO Bluetooth State einfügen!
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
	 */
	public void connect(Activity activity) {
				
		bindBluetoothService(activity);
    }
	
	private void bindBluetoothService(Activity activity) {
		if (activity == null)
			throw new IllegalArgumentException("activity cannot be null");
		
		Intent intent = new Intent(activity, BTCommunicationService.class);
		activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);	
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
}
