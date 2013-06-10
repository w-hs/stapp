package de.whs.stapp.presentation.webviews;

import android.content.Context;

/**
 * Stellt alle Funktionen zur Verfügung, die aus dem Javascript im Android
 * Container aufgerufen werden können.
 * 
 * @author Thomas
 * 
 */
public class ChartWebAppInterface extends StappWebAppInterface {
	
	/**
	 * Default Konstruktor. Instantiiert ein neues WebApp Interface auf Basis {@link StappWebAppInterface}.
	 * @param mContext Kontext des WebApp Interface.
	 */
	public ChartWebAppInterface(Context mContext) {
		super(mContext);
		
	}
}
