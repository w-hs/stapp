package de.whs.stapp.presentation.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Activity enthält das ChartFragment und wird mit der SessionID des Trainings
 * aufgerufen.
 * 
 * @author Thomas
 * 
 */
public class ChartActivity extends FragmentActivity {

	@SuppressWarnings("unused")
	private int mSessionID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		mSessionID = intent.getIntExtra("sessionID", 0);

		getSupportFragmentManager().beginTransaction()
				.replace(android.R.id.content, new ChartFragment()).commit();
	}
}
