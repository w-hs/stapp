package de.whs.stapp.liveDataTracking;

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

		this.getApplicationContext().registerReceiver(
				new BTBroadcastReceiver(), pairingRequestFilter);

		IntentFilter bondFilter = new IntentFilter(
				"android.bluetooth.device.action.BOND_STATE_CHANGED");

		getApplicationContext().registerReceiver(new BTBondReceiver(),
				bondFilter);

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
	 * @return Verbindungsstatus. Folgende Stati sind möglich:
	 *         <ul>
	 *         <li>{@link BTConstants.BT_CONNECTION_SUCCESS} - Verbindungsaufbau
	 *         erfolgreich.</li>
	 *         <li>{@link BTConstants.BT_NO_ADAPTER_STATUS} - kein
	 *         Bluetooth-Adapter gefunden.</li>
	 *         <li>{@link BTConstants.BT_CONNECTION_FAILURE} - Verbindungsaufbau
	 *         fehlgeschlagen.</li>
	 *         </ul>
	 */
	public int connectBT() {
		if (btClient != null && btClient.IsConnected())
			return BTConstants.BT_CONNECTION_SUCCESS;

		String hxMMacID = BTConstants.HXM_DEFAULT_MAC_ID;
		btAdapter = BluetoothAdapter.getDefaultAdapter();

		if (btAdapter == null)
			return BTConstants.BT_NO_ADAPTER_STATUS;
		else {
			Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

			if (pairedDevices.size() > 0) {
				for (BluetoothDevice device : pairedDevices) {
					if (device.getName().startsWith(BTConstants.HXM_DEVICE_NAME)) {
						BluetoothDevice btDevice = device;
						hxMMacID = btDevice.getAddress();
						break;
					}
				}
			}

			BluetoothDevice device = btAdapter.getRemoteDevice(hxMMacID);
			deviceName = device.getName();
			btClient = new BTClient(btAdapter, hxMMacID);
			btClient.addConnectedEventListener(hxMConnListener);

			if (btClient.IsConnected()) {
				btClient.start();
				return BTConstants.BT_CONNECTION_SUCCESS;
			} else {
				return BTConstants.BT_CONNECTION_FAILURE;
			}
		}
	}

	/**
	 * Diese Methode baut die Verbindung zum Bluetoothgerät ab.
	 */
	public void disconnectBT() {
		if (btClient != null && hxMConnListener != null) {
			btClient.removeConnectedEventListener(hxMConnListener);
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
	 * Diese Klasse wird benötigt um "brauchen wir die überhaupt?".
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
	 * Wozu.? Gute Frage
	 * 
	 * @author Dennis Miller
	 */
	private class BTBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("BTIntent", intent.getAction());
			Bundle b = intent.getExtras();
			Log.d("BTIntent", b.get("android.bluetooth.device.extra.DEVICE")
					.toString());
			Log.d("BTIntent", b.get(
					"android.bluetooth.device.extra." + "PAIRING_VARIANT")
					.toString());
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

}
