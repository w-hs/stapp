package de.whs.stapp.presentation.helper;

import de.whs.stapp.presentation.viewmodels.StappViewModel;

/**
 * Die Javascript Klasse bietet Funktionen um das Arbeiten mit Javascript
 * aus Android zu erleichtern.
 * @author Thomas
 *
 */
public class Javascript {

	private static String getFunctionCall(String functionName,
			String jsonString) {

		StringBuilder sb = new StringBuilder();
		sb.append("javascript:");
		sb.append(functionName);
		sb.append("(");
		sb.append(jsonString);
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Erstellt einen Javascript Funktionsaufruf ohne Übergabeparameter.
	 * 
	 * @param functionName
	 *            Name der aufzurufenden Funktion
	 * @return Name der zusammengebauten Funktion.
	 */
	public static String getFunctionCall(String functionName) {
		return getFunctionCall(functionName, "");
	}

	/**
	 * Baut einen Funktionsaufruf einer Javascript Funktion auf und übergibt
	 * sich selbst als JSON-String.
	 * 
	 * @param functionName
	 *            Name der zu rufenden Funktion.
	 * @return Name der zusammengebauten Funktion.
	 */
	public static String getFunctionCall(String functionName,
			StappViewModel viewModel) {

		return getFunctionCall(functionName, viewModel.toJSON());
	}

}
