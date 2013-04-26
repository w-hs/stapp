package de.whs.stapp.presentation.views;

import de.whs.stapp.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Das Fragment kapselt die Einstellungs-XML in sich.
 * @author Thomas
 *
 */
public class StappPreferenceFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.prefs);
	}
}
