package de.whs.stapp.presentation.views;

import java.util.List;

import de.whs.stapp.data.access.ChartData;
import de.whs.stapp.data.access.SessionDataInterpreter;
import de.whs.stapp.data.access.StorageAccess;
import de.whs.stapp.data.access.StorageAccessFactory;
import de.whs.stapp.data.storage.SessionDetail;
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

	private StorageAccess mStappStorageAccess;
	
	private SessionDataInterpreter interpreter;

	private int mSessionID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		mSessionID = intent.getIntExtra("sessionID", 0);

		getSupportFragmentManager().beginTransaction()
				.replace(android.R.id.content, new ChartFragment()).commit();

		mStappStorageAccess = StorageAccessFactory
				.newStorageAccess(getApplicationContext());
		List<SessionDetail> mSessionDetails = mStappStorageAccess
				.getSessionDetails(mSessionID);
		interpreter = new SessionDataInterpreter(mSessionDetails);
	}

	/**
	 * @param amountOfDetails Detailgrad.
	 * @return Die Chart Daten.
	 */
	public de.whs.stapp.presentation.viewmodels.ChartData getChartData(int amountOfDetails) {
		ChartData cd = interpreter.getHeartratePerTime(amountOfDetails);
		//TODO iwie muss hier wohl noch ein viewmodel erzeugt werden?!
		return null;
	}
}
