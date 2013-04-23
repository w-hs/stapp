package de.whs.stapp.data.bluetooth;

/**
 * .
 * @author Dennis
 *
 */
public class NoBluetoothAdapterException extends BluetoothException  {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NoBluetoothAdapterException() {
		super("No bluetooth adapter found");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detailMessage .
	 * @param throwable .
	 */
	public NoBluetoothAdapterException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detailMessage .
	 */
	public NoBluetoothAdapterException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param throwable .
	 */
	public NoBluetoothAdapterException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}
	
	

}
