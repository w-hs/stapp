package de.whs.stapp.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

/***
 * Erstellen einer angepassten WebView. In der WebView sind schon grundlegenden
 * Funktionen und Einstellungen eingestellt.
 * 
 * @author Thomas
 * 
 */
@SuppressLint("SetJavaScriptEnabled")
public class StappWebView extends WebView {


	/***
	 * Konstruktor der WebView-Klasse.
	 * 
	 * @param context
	 *            Kontext in den das WebView gerendert wird.
	 */
	public StappWebView(Context context) {
		super(context);

		WebSettings webSettings = this.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowFileAccess(true);

		this.addJavascriptInterface(
				new WebAppInterface(context), "Android");

		this.setWebViewClient(new StappWebViewClient());
		this.setWebChromeClient(new StappWebChromeClient());
	}
}
