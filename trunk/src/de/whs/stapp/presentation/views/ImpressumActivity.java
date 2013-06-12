package de.whs.stapp.presentation.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import de.whs.stapp.R;
import de.whs.stapp.presentation.webviews.Constants;
import de.whs.stapp.presentation.webviews.StappWebView;

/**
 * 
 * @author Christoph
 * Die Klasse/Activity zeigt die Webview mit dem Impressum der App.
 */
public class ImpressumActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impressum_view);
		
		StappWebView swv = new StappWebView(this);
		swv.loadUrl(Constants.HTML_LOCAL_IMPRESSUM);
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.ImpressumRelativeLayout);
		RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layout.addView(swv, relParams);
	}
}
