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


		initWebView(context, null);
	}
	
	/***
	 * Konstruktor der WebView-Klasse.
	 * 
	 * @param context
	 *            Kontext in den das WebView gerendert wird.
	 * @param stappWebAppInterface
	 *            Web App Interface, das View spezifische Funktionen im
	 *            Android-Container bereitstellt.
	 */
	public StappWebView(Context context,
			StappWebAppInterface stappWebAppInterface) {
		super(context);
		
		initWebView(context, stappWebAppInterface);
	}
	
	private void initWebView(Context context, StappWebAppInterface stappWebAppInterface){
		
		WebSettings webSettings = this.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowFileAccess(true);

		if(stappWebAppInterface != null)
			this.addJavascriptInterface(stappWebAppInterface, "Android");

		this.setWebViewClient(new StappWebViewClient());
		this.setWebChromeClient(new StappWebChromeClient());
	}
}
