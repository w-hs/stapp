package de.whs.stapp.presentation;

import de.whs.stapp.presentation.helper.Constants;
import android.content.Context;

/**
 * Bietet die Funktionen einer WebView an. Zusätzlich gibt es eine
 * Spezialisierung auf die Bedürfnisse der Verlauf-Seite.
 * @author Thomas
 *
 */
public class VerlaufWebView extends StappWebView {

	/**
	 * Standard-Konstruktor für die Verlauf WebView.
	 * @param context Kontext der WebView.
	 */
	public VerlaufWebView(Context context) {
		super(context);

		this.loadUrl(Constants.HTML_LOCAL_TRAININGSEINHEIT);
	}

}
