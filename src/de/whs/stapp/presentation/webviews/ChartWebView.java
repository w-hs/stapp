package de.whs.stapp.presentation.webviews;

import de.whs.stapp.presentation.viewmodels.Chart;
import android.content.Context;

/**
 * Stellt eine WebView für die Charts zur Verfügung. Enthält Funktionen zum
 * Laden der Webseite und enthält die Funktionen zum Javascript.
 * 
 * @author Thomas
 * 
 */
public class ChartWebView extends StappWebView {

	/**
	 * Standardkonstruktor der ChartWebView.
	 * 
	 * @param context
	 *            Kontext der WebView
	 */
	public ChartWebView(Context context) {
		super(context);

		this.loadUrl(Constants.HTML_LOCAL_CHARTS);
	}

	/**
	 * Lädt zusätzlich die WebView mit der passenden URL.
	 * 
	 * @param context
	 *            Kontext der WebView.
	 * @param stappWebAppInterface
	 *            Interface zur Kommunikation aus Javascript mit Android.
	 */
	public ChartWebView(Context context,
			StappWebAppInterface stappWebAppInterface) {
		super(context, stappWebAppInterface);
		this.loadUrl(Constants.HTML_LOCAL_CHARTS);
	}

	/**
	 * Ruft die setCharts Funktion im Javascript auf und übergibt die Daten aus
	 * der Datenbank.
	 * 
	 * @param chart
	 *            Daten einer bestimmten Datenreihe aus der Datenbank.
	 */
	public void setChart(Chart chart) {

		String functionCall = Javascript.getFunctionCall(
				Constants.JS_CHARTS_SET, chart); 
		this.loadUrl(functionCall);
	}
}
