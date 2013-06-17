package de.whs.stapp.data.bluetooth;

import java.lang.reflect.Method;
import java.util.Set;

import zephyr.android.HxMBT.BTClient;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

/**
 * Diese Klasse dient zum Verbindungsaufbau und zur Kommunikation mit dem
 * HxM-Sensor von Zephyr.
 * 
 * @author Dennis Miller
 * */
public class BluetoothConnection {	
	private static final String LOG_TAG = "Bluetooth";
	private static final String HXM_DEVICE_NAME_PREFIX = "HXM";
	public static final int REQUEST_CODE = 1337;
	
	private BluetoothAdapter mAdapter;
	private BTClient mClient;
	private HxMConnectedListener mConnListener = new HxMConnectedListener();
	private String mDeviceName;
	private Context mContext;
	
	private BTPairingReceiver mPairingReceiver = new BTPairingReceiver();
	private BTBondReceiver mBondReceiver = new BTBondReceiver();
	private BTStateChangedReceiver mStateChangedReceiver = new BTStateChangedReceiver();
	private BTDeviceFoundReceiver mDeviceFoundReceiver = new BTDeviceFoundReceiver();
	private BTDiscoveryFinishedReceiver mDiscoveryFinishedReceiver = new BTDiscoveryFinishedReceiver();
	
	/**
	 * Erstellt eine Instanz dieser Klasse.
	 * Es wird noch keine Verbindung aufgebaut.
	 * 
	 * @param context Anwendungskontext, in dem die Verbindung aufgebaut werden soll.
	 */
	public BluetoothConnection(Context context) {
		this.mContext = context;
		this.mAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	/**
	 * Prüfe, ob ein Bluetoothgerät verbunden ist.
	 */
	public boolean isOpen() {
		if (mClient == null)
			return false;
		else
			return mClient.IsConnected();
	}

	/**
	 * Diese Methode versucht einen Verbindung zum Bluetoothgerät aufzubauen.
	 * 
	 * @throws BluetoothException 
	 */
	public void open() throws BluetoothException {
		if (isOpen())
			return;

		registerBroadcastReceivers();
		
		if (mAdapter == null)
			throw new BluetoothNoAdapterException();		
		if (!mAdapter.isEnabled())
			throw new BluetoothAdapterDisabledException();
		
		String deviceAddress = findDeviceAddress();
		if (deviceAddress != null)
			if (connectDevice(deviceAddress))
				return;
		
		if (!mAdapter.startDiscovery())
			throw new BluetoothConnectionFailedException();
	}
	
	/**
	 * Diese Methode baut die Verbindung zum Bluetoothgerät ab.
	 */
	public void close() {
		if (mClient != null && mConnListener != null) {
			mClient.removeConnectedEventListener(mConnListener);
			
			mContext.unregisterReceiver(mBondReceiver);
			mContext.unregisterReceiver(mPairingReceiver);
			mContext.unregisterReceiver(mStateChangedReceiver);
			mContext.unregisterReceiver(mDiscoveryFinishedReceiver);
			mContext.unregisterReceiver(mDeviceFoundReceiver);
			
			try{
				mClient.Close();
			}catch(Exception e)
			{
				Log.e("Could not close bluetooth client", "");
			}
		}
		if (mAdapter.isEnabled()){
			//TODO: Ask for turning off bluetooth
			//turnOffBt();			
		}
			
	}
	
	/**
	 * 
	 * Diese Methode gibt den Gerätenamen wieder.
	 * 
	 * @return deviceName - Gerätename
	 */
	public String getDeviceName() {
		return mDeviceName;
	}
	
	/**
	 * Gibt ein DataTracker Objekt zurück, über das die Datenpakete
	 * vom Sensor empfangen werden können.
	 * 
	 * @return DataTracker für den Sensor.
	 */
	public DataTracker getDataTracker() {
		return mConnListener;
	}

	/**
	 * Diese Methode schaltet Bluetooth ein.
	 * 
	 * @param activity - Die main-activity
	 */
	public void turnOnBt(Activity activity) {
		if (mAdapter == null)
		{
			Log.e(LOG_TAG, "No Bluetooth adapter found");
			return;
		}
		
		if (!mAdapter.isEnabled()){
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
	 */
	public void turnOffBt() {
		if (mAdapter == null)
		{
			Log.e(LOG_TAG, "No Bluetooth adapter found");
			return;
		}
		
		if (mAdapter.isEnabled()){
			boolean isDisabled = mAdapter.disable();
			
			if (isDisabled)
				Log.d(LOG_TAG, "Bluetooth adapter disabled");
			else
				Log.e(LOG_TAG, "Could not disable Bluetooth adapter");
		}
	}
	
	/**
	 * Setzt einen Listener für den Ladezustand des Sensors.
	 * 
	 * @param listener Listener.
	 */
	public void setBatteryChargeListener(BatteryChargeListener listener) {
		mConnListener.setBatteryChargeListener(listener);
	}
	
	/**
	 * Liefert den letzten gesendeten Ladezustand des Sensors.
	 * 
	 * @return Ladezustand (0-100).
	 */
	public int getLastBatteryCharge() {
		return mConnListener.getLastBatteryCharge();
	}
	
	private boolean connectDevice(String address) throws BluetoothException{
		BluetoothDevice device = mAdapter.getRemoteDevice(address);
		mDeviceName = device.getName();
		mClient = new BTClient(mAdapter, address);
		mClient.addConnectedEventListener(mConnListener);

		if (mClient.IsConnected()){
			mClient.start();
			return true;
		}
		else
			throw new BluetoothConnectionFailedException();
	}
	
	private String findDeviceAddress() throws BluetoothException {
		Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();
		for (BluetoothDevice device : pairedDevices) {
			if (device.getName().startsWith(HXM_DEVICE_NAME_PREFIX)) {
				return device.getAddress();
			}
		}
		
		return null;
	}
	
	/**
	 * Registriert Broadcast-Receiver für bestimmte Ereignisse, die den 
	 * Bluetooth-Adapter betreffen.
	 */
	private void registerBroadcastReceivers() {

		IntentFilter pairingRequestFilter = new IntentFilter(
				"android.bluetooth.device.action.PAIRING_REQUEST");
		mContext.registerReceiver(mPairingReceiver, pairingRequestFilter);

		IntentFilter bondFilter = new IntentFilter(
				"android.bluetooth.device.action.BOND_STATE_CHANGED");
		mContext.registerReceiver(mBondReceiver, bondFilter);
		
		IntentFilter btStateChangedFilter = new IntentFilter(
				"android.bluetooth.adapter.action.STATE_CHANGED");
		mContext.registerReceiver(mStateChangedReceiver,	btStateChangedFilter);
		
		IntentFilter btDeviceFoundFilter = new IntentFilter(
				"android.bluetooth.device.action.FOUND");
		mContext.registerReceiver(mDeviceFoundReceiver, btDeviceFoundFilter);
		
		IntentFilter btDiscoveryFinishedFilter = new IntentFilter(
				"android.bluetooth.adapter.action.DISCOVERY_FINISHED");
		mContext.registerReceiver(mDiscoveryFinishedReceiver, btDiscoveryFinishedFilter);

	}
	
	/**
	 * 
	 * Dieser Broadcast-Receiver ist ausgelegt, um auf die Änderung
	 * des Paarung-Status zu reagieren.
	 * 
	 * @author Dennis Miller
	 */
	private class BTBondReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			BluetoothDevice device = mAdapter.getRemoteDevice(b.get(
					"android.bluetooth.device.extra.DEVICE").toString());
			Log.d(LOG_TAG, "BOND_STATED = " + device.getBondState());
		}
	}

	/**
	 * 
	 * Dieser Broadcast-Receiver ist ausgelegt, um auf
	 * eine Paarungs-Anfrage zu reagieren.
	 * Diese Anfrage beantwortet er direkt mit dem HxM-Device
	 * Pin "1234".
	 * 
	 * @author Dennis Miller
	 */
	private class BTPairingReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(LOG_TAG, "Pairing intent: " + intent.getAction());
			
			Bundle b = intent.getExtras();
			String pairingDevice = b.get("android.bluetooth.device.extra.DEVICE").toString();
			String pairingVariant = b.get("android.bluetooth.device.extra.PAIRING_VARIANT").toString();
			Log.d(LOG_TAG, "Pairing device: " + pairingDevice);
			Log.d(LOG_TAG, "Pairing variant: " + pairingVariant);
			
			try {
				BluetoothDevice device = mAdapter.getRemoteDevice(pairingDevice);
				Method convertPinToBytes = device.getClass()
						.getMethod("convertPinToBytes",	new Class[] { String.class });
				byte[] pin = (byte[]) convertPinToBytes.invoke(device, "1234");
				
				Method setPin = device.getClass().getMethod("setPin", new Class[] { pin.getClass() });
				Object result = setPin.invoke(device, pin);
				
				Log.d(LOG_TAG, "Setting PIN to '1234' (" + result.toString() + ")");
			} catch (Exception e) {
				Log.e(LOG_TAG, "Error while pairing devices: " + e.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * Dieser Broadcast-Receiver wird genutzt um einen Status-Wechsel
	 * des Bluetooth-Adapters zu registrieren.
	 * 
	 * @author Dennis Miller
	 */
	private class BTStateChangedReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
					
				Bundle b = intent.getExtras();
				int state = b.getInt("android.bluetooth.adapter.extra.STATE");
				int prevState = b.getInt("android.bluetooth.adapter.extra.PREVIOUS_STATE");
				btAdapterStateChanged(state, prevState);
						
		}
		
		private void btAdapterStateChanged(int state, int previousState){
			switch (state) {
			case BluetoothAdapter.STATE_TURNING_ON:
				//TODO
				break;
			case BluetoothAdapter.STATE_ON:
				
				break;
			case BluetoothAdapter.STATE_TURNING_OFF:
				
				break;
			case BluetoothAdapter.STATE_OFF:
				
				break;					
			}
		}
		
	}
	
	/**
	 * 
	 * Dieser Broadcast-Receiver wird genutzt um gefundene Bluetooth-Devices
	 * zu prüfen und gegebenenfalls eine Verbindung aufzubauen.
	 * 
	 * @author Dennis Miller
	 */
	private class BTDeviceFoundReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device.getName().startsWith(HXM_DEVICE_NAME_PREFIX)){
            	mAdapter.cancelDiscovery();
            	try {
            		Log.d(LOG_TAG, "HxM-Sensor discovered");
            		connectDevice(device.getAddress());
            	} catch (BluetoothException e) {
            		Log.e(LOG_TAG, "Error while connecting to device: " + e.getMessage());
            	}
            } else {
            	Log.d(LOG_TAG, "Other bluetooth device found: " + device.getName());
            }
		}
	}
	
	/**
	 * Dieser Broadcast-Receiver wird genutzt um bei Beendigung des
	 * Discovery-Vorgangs die Bluetooth-Verbindung zu prüfen.
	 * 
	 * @author Dennis Miller
	 */
	private class BTDiscoveryFinishedReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (mClient != null && mClient.IsConnected())
				Log.d(LOG_TAG, "Client connected after discovery");
			else
				Log.e(LOG_TAG, "Client not connected after discovery");
			
		}
		
	}
}
