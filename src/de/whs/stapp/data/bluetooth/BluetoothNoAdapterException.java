package de.whs.stapp.data.bluetooth;

/**
 * .
 * @author Dennis
 *
 */
public class BluetoothNoAdapterException extends BluetoothException  {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BluetoothNoAdapterException() {
		super("No bluetooth adapter found");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detailMessage .
	 * @param throwable .
	 */
	public BluetoothNoAdapterException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detailMessage .
	 */
	public BluetoothNoAdapterException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param throwable .
	 */
	public BluetoothNoAdapterException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}
	
	

}
