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
public class BTCommunicationService extends Service {

	private final IBinder btServiceBinder = new BTServiceBinder(this);
	private final String hxMDeviceName = "HXM";
	
	private BluetoothAdapter btAdapter = null;
	private BTClient btClient = null;
	private HxMConnectedListener hxMConnListener = new HxMConnectedListener();
	private String deviceName;
	

	@Override
	public IBinder onBind(Intent intent) {
		return btServiceBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		IntentFilter pairingRequestFilter = new IntentFilter(
				"android.bluetooth.device.action.PAIRING_REQUEST");
		getApplicationContext().registerReceiver(
				new BTPairingReceiver(), pairingRequestFilter);

		IntentFilter bondFilter = new IntentFilter(
				"android.bluetooth.device.action.BOND_STATE_CHANGED");
		getApplicationContext().registerReceiver(new BTBondReceiver(),
				bondFilter);
		
		IntentFilter btStateChangedFilter = new IntentFilter(
				"android.bluetooth.adapter.action.STATE_CHANGED");
		getApplicationContext().registerReceiver(new BtStateChangedReceiver(),
				btStateChangedFilter);
		
		IntentFilter btDeviceFoundFilter = new IntentFilter(
				"android.bluetooth.device.action.FOUND");
		getApplicationContext().registerReceiver(new BtDeviceFoundReceiver(),
				btDeviceFoundFilter);
		
		IntentFilter btDiscoveryFinishedFilter = new IntentFilter(
				"android.bluetooth.adapter.action.DISCOVERY_FINISHED");
		getApplicationContext().registerReceiver(new BtDiscoveryFinishedReceiver(),
				btDiscoveryFinishedFilter);

	}

	@Override
	public void onDestroy() {
		disconnectBT();
	}

	/**
	 * Diese Methode registriert einen Listener bei diesem Service (
	 * {@link BTCommunicationService}). Jeder Client, der Nachrichten des
	 * HxM-Sensors empfangen möchte, muss sich bei diesem Service registrieren.
	 * 
	 * @param l - Listener, der bei diesem Service registriert werden soll.
	 * 
	 * */
	public void registerHxMListener(TrackedDataListener l) {
		if (hxMConnListener != null)
			hxMConnListener.registerListener(l);
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
	 */
	public ConnectionState initiateBtConnection() {
		if (btClient != null && btClient.IsConnected())
			return ConnectionState.Connected;

		String hxMMacID = null;
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null)
			throw new IllegalArgumentException("There is no bluetooth adapter!");
		
		if (!btAdapter.isEnabled())
			throw new IllegalArgumentException("The bluetooth adapter is disabled");
			
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				if (device.getName().startsWith(hxMDeviceName)) {
					hxMMacID = device.getAddress();
					return connectBt(hxMMacID);					
				}
			}
		}
		
		if (btAdapter.startDiscovery())
			return ConnectionState.Connecting;
		else
			throw new IllegalArgumentException("Bluetooth discovery failed");
	}
	
	private ConnectionState connectBt(String hxMMacID){
		BluetoothDevice device = btAdapter.getRemoteDevice(hxMMacID);
		deviceName = device.getName();
		btClient = new BTClient(btAdapter, hxMMacID);
		btClient.addConnectedEventListener(hxMConnListener);

		if (btClient.IsConnected()) {
			btClient.start();
			return ConnectionState.Connected;
		} 
		else {
			return ConnectionState.Disconnected;
		}
	}

	/**
	 * Diese Methode baut die Verbindung zum Bluetoothgerät ab.
	 */
	public void disconnectBT() {
		if (btClient != null && hxMConnListener != null) {
			btClient.removeConnectedEventListener(hxMConnListener);
			
			getApplicationContext().unregisterReceiver(new BTBondReceiver());
			getApplicationContext().unregisterReceiver(new BTPairingReceiver());
			getApplicationContext().unregisterReceiver(new BtStateChangedReceiver());
			getApplicationContext().unregisterReceiver(new BtDiscoveryFinishedReceiver());
			getApplicationContext().unregisterReceiver(new BtDeviceFoundReceiver());
			
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
	 * Deregistriert den listener.
	 * @param listener .
	 */
	public void unregisterListener(TrackedDataListener listener) {
		hxMConnListener.unregisterListener(listener);		
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
            	connectBt(device.getAddress());
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
			if (!btClient.IsConnected())
				throw new IllegalArgumentException("There is no HxM device in range");
			
		}
		
	}
}
