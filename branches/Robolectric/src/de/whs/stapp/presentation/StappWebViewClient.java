package de.whs.stapp.presentation;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Bietet die Möglichkeit auf Aktionen innerhalb der WebView zu reagieren.
 * 
 * @author Thomas
 * 
 */
public class StappWebViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		
		return true;
	}
}
