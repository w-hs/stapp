package de.whs.stapp.presentation.helper;

/**
 * Diese Klasse beinhaltet Konstanten für das gesamte Projekt.
 * 
 * @author Dennis Miller
 * */
public final class Constants {
	
	/**
	 * Kein Konstruktor für eine Utility-Klasse.
	 */
	private Constants()
	{		
	}
	
	//Javascript Funktionen
	public static final String JS_TRAININGSEINHEIT_UPDATE = "stapp.updateTraining"; 
	public static final String JS_TRAININGSEINHEIT_START = "stapp.startTraining";
	public static final String JS_TRAININGSEINHEIT_PAUSE = "stapp.pauseTraining";
	public static final String JS_TRAININGSEINHEIT_STOP = "stapp.stopTraining";
	
	//HTML Seiten
	public static final String HTML_LOCAL_TRAININGSEINHEIT = "file:///android_asset/www/Trainingseinheit.html";
	public static final String HTML_LOCAL_VERLAUF = "file:///android_asset/www/Verlauf.html";
}
