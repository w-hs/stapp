package de.whs.stapp;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import de.whs.stapp.presentation.TrainingseinheitWebView;

/**
 * The applications MainActivity.
 * 
 * @author Chris
 */
public class MainActivity extends Activity {

	private TrainingseinheitWebView mTrainingseinheitWebview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTrainingseinheitWebview = new TrainingseinheitWebView(this);

		RelativeLayout relativeLayout = 
				(RelativeLayout) findViewById(R.id.Wrapper);

		RelativeLayout.LayoutParams relParams = 
			new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		relParams.addRule(RelativeLayout.BELOW, R.id.btnSettings);
		
		relativeLayout.addView(mTrainingseinheitWebview, relParams);
		
		//OnClick Event Handler zu Testzwecken. Kann später wieder entfernt werden.
		Button startTraining = (Button)findViewById(R.id.btnStartRun);
		startTraining.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {

				TrainingseinheitWebView trainingseinheitWebView = 
					((MainActivity)v.getContext()).getmTrainingseinheitWebview();
				if(trainingseinheitWebView != null)
					trainingseinheitWebView.startTraining();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action
		// bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Gibt die WebView der Trainingseinheit-Seite
	 * zurück.
	 * @return Trainingseinheit WebView
	 */
	public TrainingseinheitWebView getmTrainingseinheitWebview() {
		return mTrainingseinheitWebview;
	}
}
