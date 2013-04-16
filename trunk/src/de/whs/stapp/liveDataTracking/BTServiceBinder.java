package de.whs.stapp.liveDataTracking;

import android.os.Binder;

/**
 * @author Dennis Miller
 * Diese Klasse stellt den vom BTCommunicationService ben�tigten 
 * Binder dar.
 */
public class BTServiceBinder extends Binder {
	
	private BTCommunicationService serviceInstance; 
	
	/**
	 * Dies ist der Konstruktor des Binders. 
	 * @param serviceInstance - Instanz des dazugeh�rigen Services.
	 */
	public BTServiceBinder (BTCommunicationService serviceInstance){
		this.serviceInstance = serviceInstance;
	}
	
	/**
	 * Gibt die Instanz des BTCommunicationServices zur�ck.
	 * @return serviceInstance
	 */
	public BTCommunicationService getService() {
		return serviceInstance;
	}
}