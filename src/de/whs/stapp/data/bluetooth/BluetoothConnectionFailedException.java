package de.whs.stapp.data.bluetooth;
/**
 * .
 * @author Dennis
 *
 */
public class BluetoothConnectionFailedException extends BluetoothException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BluetoothConnectionFailedException() {
		super("Bluetooth connection failed");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detailMessage . 
	 * @param throwable .
	 */
	public BluetoothConnectionFailedException(String detailMessage,
			Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detailMessage .
	 */
	public BluetoothConnectionFailedException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param throwable .
	 */
	public BluetoothConnectionFailedException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}
	
}
