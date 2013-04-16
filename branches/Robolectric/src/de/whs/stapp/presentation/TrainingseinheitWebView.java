package de.whs.stapp.presentation;

import android.content.Context;
import de.whs.stapp.presentation.helper.Constants;
import de.whs.stapp.presentation.helper.Javascript;
import de.whs.stapp.presentation.viewmodels.Trainingseinheit;

/**
 * Bietet die Funktionen einer WebView an. Zusätzlich gibt es eine
 * Spezialisierung auf die Bedürfnisse der Trainginseinheit-Seite.
 * 
 * @author Thomas
 * 
 */
public class TrainingseinheitWebView extends StappWebView {

	/**
	 * Standardkonstruktor, der die Trainingseinheit- Seite lädt.
	 * 
	 * @param context
	 *            Kontext der zu rendernden WebView.
	 */
	public TrainingseinheitWebView(Context context) {
		super(context);

		//TODO Laden der Webseite von einem Server
		// Abhängigkeit von der Internetverbindung einbauen
		this.loadUrl(Constants.HTML_LOCAL_TRAININGSEINHEIT);
	}

	/**
	 * Übergibt die geänderten Trainingsdaten an das Javascript, 
	 * sodass dieses sich updaten kann.
	 * 
	 * @param trainingData
	 *            Die geänderten Daten der Trainingseinheit.
	 */
	public void updateTrainingData(Trainingseinheit trainingData) {

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
