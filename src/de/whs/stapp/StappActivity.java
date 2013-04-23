package de.whs.stapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import de.whs.stapp.presentation.views.HistoryFragment;
import de.whs.stapp.presentation.views.SessionFragment;
import de.whs.stapp.presentation.views.TabListener;

/**
 * 
 * @author Thomas
 * 
 */
public class StappActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		Tab tab = actionBar.newTab()
	            .setText("Trainingseinheit")
	            .setTabListener(new TabListener<SessionFragment>(
	                    this, "training", SessionFragment.class));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	        .setText("Verlauf")
	        .setTabListener(new TabListener<HistoryFragment>(
	                this, "history", HistoryFragment.class));
	    actionBar.addTab(tab);

		if (savedInstanceState != null) {
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt(
					"tab", 0));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}
}
