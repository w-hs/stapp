package de.whs.stapp.presentation;

import android.content.Context;

/**
 * Stellt alle Funktionen zur Verfügung, die aus dem Javascript im Android
 * Container aufgerufen werden können.
 * 
 * @author Thomas
 * 
 */
public class HistoryWebAppInterface extends StappWebAppInterface {

	/**
	 * Default Konstruktor. Instantiiert ein neues WebApp Interface auf Basis {@link StappWebAppInterface}.
	 * @param mContext Kontext des WebApp Interface.
	 */
	public HistoryWebAppInterface(Context mContext) {
		super(mContext);
	}

}
