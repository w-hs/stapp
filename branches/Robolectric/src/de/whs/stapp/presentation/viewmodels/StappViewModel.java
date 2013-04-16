package de.whs.stapp.presentation.viewmodels;

import com.google.gson.Gson;

/**
 * Grundlegendes ViewModel des Projekts.
 * Bietet die Möglichkeiten das Objekt als JSON
 * zu serialisieren.
 * @author Thomas
 *
 */
public class StappViewModel {

	/**
	 * Generiert aus dem ViewModel ein JSON-Objekt,
	 * das im Javascript ausgewertet werden kann.
	 * @return Serialisierte JSON-Objekt
	 */
	public String toJSON()
	{
		return new Gson().toJson(this);
	}
}
