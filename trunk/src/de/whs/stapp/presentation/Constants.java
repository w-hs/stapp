package de.whs.stapp.presentation;

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
	//TrainingUnit
	public static final String JS_TRAININGSEINHEIT_UPDATE = "stapp.updateTraining"; 
	public static final String JS_TRAININGSEINHEIT_START = "stapp.startTraining";
	public static final String JS_TRAININGSEINHEIT_PAUSE = "stapp.pauseTraining";
	public static final String JS_TRAININGSEINHEIT_STOP = "stapp.stopTraining";
	//History
	public static final String JS_HISTORY_SET = "stapp.setHistoryData";
	
	//HTML Seiten
	public static final String HTML_LOCAL_TRAININGSEINHEIT = "file:///android_asset/www/SessionView.html";
	public static final String HTML_LOCAL_VERLAUF = "file:///android_asset/www/HistoryView.html";
}
