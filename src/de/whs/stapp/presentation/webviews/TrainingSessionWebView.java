package de.whs.stapp.presentation.webviews;

import android.content.Context;
import de.whs.stapp.presentation.viewmodels.TrainingSession;

/**
 * Bietet die Funktionen einer WebView an. Zus�tzlich gibt es eine
 * Spezialisierung auf die Bed�rfnisse der Trainginseinheit-Seite.
 * 
 * @author Thomas
 * 
 */
public class TrainingSessionWebView extends StappWebView {

	/**
	 * Standardkonstruktor, der die Trainingseinheit- Seite l�dt.
	 * 
	 * @param context
	 *            Kontext der zu rendernden WebView.
	 */
	public TrainingSessionWebView(Context context) {
		super(context, new TrainingSessionWebAppInterface(context));

		//TODO Laden der Webseite von einem Server
		// Abh�ngigkeit von der Internetverbindung einbauen
		this.loadUrl(Constants.HTML_LOCAL_TRAININGSEINHEIT);
	}

	/**
	 * �bergibt die ge�nderten Trainingsdaten an das Javascript, 
	 * sodass dieses sich updaten kann.
	 * 
	 * @param trainingData
	 *            Die ge�nderten Daten der Trainingseinheit.
	 */
	public void updateTrainingData(TrainingSession trainingData) {

		String functionCall = 
				Javascript.getFunctionCall(Constants.JS_TRAININGSEINHEIT_UPDATE, trainingData);
		this.loadUrl(functionCall);
	}
	
	/**
	 * Startet eine neue Trainingseinheit.
	 */
	public void startTraining(){
		
		this.loadUrl(Javascript.getFunctionCall(Constants.JS_TRAININGSEINHEIT_START));
	}
	
	/**
	 * Aufruf der Javascript-Funktion um das Training zu pausieren.
	 */
	public void pauseTraining(){

		this.loadUrl(Javascript.getFunctionCall(Constants.JS_TRAININGSEINHEIT_PAUSE));
	}
	
	/**
	 * Aufruf der Javascript-Funktion um das Training zu stoppen.
	 */
	public void stopTraining(){

		this.loadUrl(Javascript.getFunctionCall(Constants.JS_TRAININGSEINHEIT_STOP));
	}

}
