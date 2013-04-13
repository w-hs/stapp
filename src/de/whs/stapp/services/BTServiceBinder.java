package de.whs.stapp.services;

import android.os.Binder;

/**
 *
 * Diese Klasse stellt den vom BTCommunicationService
 * benötigten Binder dar.
 * 
 * @author Dennis Miller
 */
public class BTServiceBinder extends Binder {
	
	private BTCommunicationService serviceInstance; 
	
	/**
	 * 
	 * Dies ist der Konstruktor des Binders. 
	 * 
	 * @param serviceInstance - Instanz des dazugehörigen Services.
	 */
	public BTServiceBinder (BTCommunicationService serviceInstance){
		this.serviceInstance = serviceInstance;
	}
	
	/**
	 * Gibt die Instanz des BTCommunicationServices zurück.
	 * 
	 * @return serviceInstance
	 */
	public BTCommunicationService getService(){
		
		return serviceInstance;
	}
}
