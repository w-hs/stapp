package de.whs.stapp.presentation.views;

import android.app.Activity;
import android.os.Bundle;
import de.whs.stapp.R;
import de.whs.stapp.presentation.Constants;
import de.whs.stapp.presentation.StappWebView;

/**
 * 
 * @author Christoph
 * Die Klasse/Activity zeigt die Webview mit dem Impressum der App.
 */
public class ImpressumView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impressum_view);
		
		StappWebView swv = (StappWebView) findViewById(R.id.stappWebView1);
		
		swv.loadUrl(Constants.HTML_LOCAL_IMPRESSUM);
	}
}
