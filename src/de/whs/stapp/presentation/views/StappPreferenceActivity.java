package de.whs.stapp.presentation.views;

import android.app.Activity;
import android.os.Bundle;

/**
 * Stellt eine Activität zur Verfügung, welche das Einstellungs-Fragment
 * bereitstellt.
 * 
 * @author Thomas
 * 
 */
public class StappPreferenceActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new StappPreferenceFragment())
				.commit();
	}
}
