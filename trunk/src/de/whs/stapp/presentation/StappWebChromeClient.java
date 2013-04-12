package de.whs.stapp.presentation;

import android.util.Log;
import android.webkit.WebChromeClient;

/**
 * Implementiert eine Schnittstelle, um Log-Einträge aus dem Javascript in Java
 * zu loggen.
 * 
 * @author Thomas
 * 
 */
public class StappWebChromeClient extends WebChromeClient {

	@Override
	public void onConsoleMessage(String message, 
			int lineNumber, String sourceID) {
		Log.d("MyApplication", message + 
				" -- From line " + lineNumber + " of "
				+ sourceID);
	}
}
