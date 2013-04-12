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
	
	/**
	 * Baut einen Funktionsaufruf einer Javascript Funktion
	 * auf und übergibt sich selbst als JSON-String.
	 * @param functionName Name der zu rufenden Funktion.
	 * @return
	 */
	public String getJavascriptFunctionCall(String functionName){
		
		StringBuilder sb = new StringBuilder();
		sb.append("javascript:");
		sb.append(functionName);
		sb.append("(");
		sb.append(this.toJSON());
		sb.append(")");
		return sb.toString();
	}
}
