package de.whs.stapp.presentation.views;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import de.whs.stapp.data.access.SessionDataInterpreter;
import de.whs.stapp.data.access.StorageAccess;
import de.whs.stapp.data.access.StorageAccessFactory;
import de.whs.stapp.data.storage.SessionDetail;
import de.whs.stapp.presentation.viewmodels.Chart;

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
	public Chart getChartData(int amountOfDetails) {
		Chart cd = new Chart();
		cd.setHeartRateData(interpreter.getHeartratePerTime(amountOfDetails));
		cd.setSpeedPerTime(interpreter.getSpeedPerTime(amountOfDetails));
		return cd;
	}
}
