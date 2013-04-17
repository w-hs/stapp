package de.whs.stapp.data.bluetooth;

import android.os.Binder;

/**
 * @author Dennis Miller
 * Diese Klasse stellt den vom BTCommunicationService benötigten 
 * Binder dar.
 */
class BTServiceBinder extends Binder {
	
	private BTCommunicationService serviceInstance; 
	
	/**
	 * Dies ist der Konstruktor des Binders. 
	 * @param serviceInstance - Instanz des dazugehörigen Services.
	 */
	public BTServiceBinder (BTCommunicationService serviceInstance){
		this.serviceInstance = serviceInstance;
	}
	
	/**
	 * Gibt die Instanz des BTCommunicationServices zurück.
	 * @return serviceInstance
	 */
	public BTCommunicationService getService() {
		return serviceInstance;
	}
}
