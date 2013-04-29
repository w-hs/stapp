package de.whs.stapp.data.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Repräsentiert das Bluetooth Device.
 * @author Chris
 */
public class BluetoothDevice {
	public static final int REQUEST_CODE = 1337;
	
	/**
	 * Diese Methode schaltet Bluetooth ein.
	 * 
	 * @param activity - Die main-activity
	 */
	public void enable(Activity activity) {
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null)
			throw new IllegalArgumentException("There is no bluetooth adapter");
		
		if (!btAdapter.isEnabled()){
			Intent enableBtIntent =  new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			//activity.startActivity(enableBtIntent);	
			activity.startActivityForResult(enableBtIntent, REQUEST_CODE);
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
	public void disable(Activity activity) {
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if (btAdapter.isEnabled()){
			boolean isDisabling = btAdapter.disable();
			
			if (!isDisabling)
				throw new IllegalArgumentException("Error during turning off the bluetooth adapter");
		}
	}
	
	
}
