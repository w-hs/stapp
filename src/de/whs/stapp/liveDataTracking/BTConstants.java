package de.whs.stapp.liveDataTracking;

/**
 * @author Dennis Miller
 * Diese Klasse beinhaltet Konstanten für das gesamte Projekt.
 * */
public final class BTConstants {
	
	/**
	 * @author Chris
	 * Utility classes should not have a public constructor.
	 */
	private BTConstants() {
		
	}
	
	public static final int HR_SPD_DIST_PACKET =0x26;
	
	//Bluetooth-Service Constants
	public static final int BT_NO_ADAPTER_STATUS = 1002;
	public static final int BT_CONNECTION_SUCCESS = 1001;
	public static final int BT_CONNECTION_FAILURE = 1003;
	public static final String HXM_DEFAULT_MAC_ID = "00:00:00:00:00:00";
	
	public static final String HXM_DEVICE_NAME = "HXM";
	
}
