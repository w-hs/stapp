package de.whs.stapp.data.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;

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
		{
			Log.e("Bluetooth", "No Bluetooth adapter found");
			return;
		}
		
		if (!btAdapter.isEnabled()){
			Intent enableIntent =  new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			activity.startActivityForResult(enableIntent, REQUEST_CODE);
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
		if (btAdapter == null)
		{
			Log.e("Bluetooth", "No Bluetooth adapter found");
			return;
		}
		
		if (btAdapter.isEnabled()){
			boolean isDisabled = btAdapter.disable();
			
			if (isDisabled)
				Log.d("Bluetooth", "Bluetooth adapter disabled");
			else
				Log.e("Bluetooth", "Could not disable Bluetooth adapter");
		}
	}
	
	
}
