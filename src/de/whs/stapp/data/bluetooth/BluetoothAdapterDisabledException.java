/**
 * 
 */
package de.whs.stapp.data.bluetooth;

/**
 * 
 * .
 * @author Dennis
 *
 */
public class BluetoothAdapterDisabledException extends BluetoothException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BluetoothAdapterDisabledException() {
		super("Bluetooth adapter is disabled");
	}

	/**
	 * @param detailMessage .
	 * @param throwable .
	 */
	public BluetoothAdapterDisabledException(String detailMessage,
			Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detailMessage .
	 */
	public BluetoothAdapterDisabledException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param throwable .
	 */
	public BluetoothAdapterDisabledException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

}
