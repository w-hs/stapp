package de.whs.stapp.data.bluetooth;

import java.lang.reflect.Method;
import java.util.Set;



import zephyr.android.HxMBT.BTClient;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Dieser Service dient zum Verbindungsaufbau und zur Kommunikation mit dem
 * HxM-Sensor von Zephyr.
 * 
 * @author Dennis Miller
 * */
public class BluetoothCommunication {
	private final String hxMDeviceName = "HXM";
	
	private BluetoothAdapter btAdapter = null;
	private BTClient btClient = null;
	private HxMConnectedListener hxMConnListener = new HxMConnectedListener();
	private String deviceName;
	private Context context;
	
	private BTPairingReceiver mPairingReceiver = new BTPairingReceiver();
	private BTBondReceiver mBondReceiver = new BTBondReceiver();
	private BtStateChangedReceiver mStateChangedReceiver = new BtStateChangedReceiver();
	private BtDeviceFoundReceiver mDeviceFoundReceiver = new BtDeviceFoundReceiver();
	private BtDiscoveryFinishedReceiver mDiscoveryFinishedReceiver = new BtDiscoveryFinishedReceiver();
	
	public BluetoothCommunication(Context context) {
		this.context = context;
	}

	
	public void registerBroadcastReceivers() {

		IntentFilter pairingRequestFilter = new IntentFilter(
				"android.bluetooth.device.action.PAIRING_REQUEST");
		context.registerReceiver(mPairingReceiver, pairingRequestFilter);

		IntentFilter bondFilter = new IntentFilter(
				"android.bluetooth.device.action.BOND_STATE_CHANGED");
		context.registerReceiver(mBondReceiver, bondFilter);
		
		IntentFilter btStateChangedFilter = new IntentFilter(
				"android.bluetooth.adapter.action.STATE_CHANGED");
		context.registerReceiver(mStateChangedReceiver,	btStateChangedFilter);
		
		IntentFilter btDeviceFoundFilter = new IntentFilter(
				"android.bluetooth.device.action.FOUND");
		context.registerReceiver(mDeviceFoundReceiver, btDeviceFoundFilter);
		
		IntentFilter btDiscoveryFinishedFilter = new IntentFilter(
				"android.bluetooth.adapter.action.DISCOVERY_FINISHED");
		context.registerReceiver(mDiscoveryFinishedReceiver, btDiscoveryFinishedFilter);

	}

	public DataTracker getDataTracker() {
		return hxMConnListener;
	}

	/**
	 * Prüfe, ob ein Bluetoothgerät verbunden ist.
	 */
	public boolean isConnected() {
		if (btClient == null)
			return false;
		else
			return btClient.IsConnected();
	}

	/**
	 * Diese Methode versucht einen Verbindung zum Bluetoothgerät aufzubauen.
	 * 
	 * @return Verbindungsstatus
	 * @throws BluetoothException 
	 */
	public void initiateBtConnection() throws BluetoothException {
		if (btClient != null && btClient.IsConnected())
			return;

		String hxMMacID = null;
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null)
			throw new NoBluetoothAdapterException();
				
		if (!btAdapter.isEnabled())
			throw new BluetoothAdapterDisabledException();
			
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				if (device.getName().startsWith(hxMDeviceName)) {
					hxMMacID = device.getAddress();
					connectBt(hxMMacID);					
				}
			}
		}
		
		if (!btAdapter.startDiscovery())
			throw new BluetoothConnectionFailedException();
		//TODO
	}
	
	private void connectBt(String hxMMacID) throws BluetoothException{
		BluetoothDevice device = btAdapter.getRemoteDevice(hxMMacID);
		deviceName = device.getName();
		btClient = new BTClient(btAdapter, hxMMacID);
		btClient.addConnectedEventListener(hxMConnListener);

		if (btClient.IsConnected()) {
			btClient.start();
		} 
		else {
			throw new BluetoothConnectionFailedException();
		}
	}

	/**
	 * Diese Methode baut die Verbindung zum Bluetoothgerät ab.
	 */
	public void disconnectBT() {
		if (btClient != null && hxMConnListener != null) {
			btClient.removeConnectedEventListener(hxMConnListener);
			
			context.unregisterReceiver(mBondReceiver);
			context.unregisterReceiver(mPairingReceiver);
			context.unregisterReceiver(mStateChangedReceiver);
			context.unregisterReceiver(mDiscoveryFinishedReceiver);
			context.unregisterReceiver(mDeviceFoundReceiver);
			
			btClient.Close();
		}
	}
	
	/**
	 * 
	 * Diese Methode gibt lediglich den Gerätenamen wieder.
	 * 
	 * @return deviceName - Gerätename
	 */
	public String getDeviceName() {
		return deviceName;
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
			BluetoothDevice device = btAdapter.getRemoteDevice(b.get(
					"android.bluetooth.device.extra.DEVICE").toString());
			Log.d("Bond state", "BOND_STATED = " + device.getBondState());
			// TODO
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
			Log.d("BTIntent", intent.getAction());
			Bundle b = intent.getExtras();
			Log.d("BTIntent", b.get("android.bluetooth.device.extra.DEVICE")
					.toString());
			Log.d("BTIntent", b.get("android.bluetooth.device.extra." 
					+ "PAIRING_VARIANT").toString());
			try {
				BluetoothDevice device = btAdapter.getRemoteDevice(b.get(
						"android.bluetooth.device.extra.DEVICE").toString());
				Method m = BluetoothDevice.class.getMethod("convertPinToBytes",
						new Class[] { String.class });
				byte[] pin = (byte[]) m.invoke(device, "1234");
				m = device.getClass().getMethod("setPin",
						new Class[] { pin.getClass() });
				Object result = m.invoke(device, pin);
				Log.d("BTTest", result.toString());
			} catch (Exception e1) {
				Log.e("BTCommunicationService", e1.toString());
				e1.printStackTrace();
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
	private class BtStateChangedReceiver extends BroadcastReceiver {

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
	private class BtDeviceFoundReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device.getName().equals(hxMDeviceName)){
            	btAdapter.cancelDiscovery();
            	try {
            		connectBt(device.getAddress());
            	} catch (BluetoothException e) {
            		Log.e("BTCommunicationService", e.getMessage());
            	}
            }
		}
	}
	
	/**
	 * 
	 * Dieser Broadcast-Receiver wird genutzt um bei Beendigung des
	 * Discovery-Vorgangs die Bt-Verbindung zu prüfen.
	 * 
	 * @author Dennis Miller
	 */
	private class BtDiscoveryFinishedReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (btClient == null || !btClient.IsConnected())
				Log.e("BTCommunicationService", "Bluetooth Client not connected");
			
		}
		
	}
}
