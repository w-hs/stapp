package de.whs.stapp.presentation.views;

import java.util.List;

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

	private List<SessionDetail> mSessionDetails;

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
		mSessionDetails = mStappStorageAccess.getSessionDetails(mSessionID);
	}

	/**
	 * Getter für die SessionDetails der Session.
	 * 
	 * @return Gibt eine Liste der Session Details zurück.
	 */
	public List<SessionDetail> getSessionDetails() {
		return mSessionDetails;
	}
}
