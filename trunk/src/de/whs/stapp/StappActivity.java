package de.whs.stapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import de.whs.stapp.data.access.DataAccess;
import de.whs.stapp.data.access.DataAccessFactory;
import de.whs.stapp.data.access.Training;
import de.whs.stapp.data.access.TrainingState;
import de.whs.stapp.data.bluetooth.BluetoothDevice;
import de.whs.stapp.data.bluetooth.BluetoothException;
import de.whs.stapp.data.bluetooth.ConnectionState;
import de.whs.stapp.presentation.views.HistoryFragment;
import de.whs.stapp.presentation.views.SessionFragment;
import de.whs.stapp.presentation.views.StappCollectionPagerAdapter;
import de.whs.stapp.presentation.views.TabListener;

/**
 * Standard-Einstiegspunkt für das Stapp-Projekt. Enthält die Activity, welche
 * das gesamte Produkt verwaltet.
 * O
 * @author Thomas
 * 
 */
public class StappActivity extends FragmentActivity {

	private BluetoothDevice btDevice = new BluetoothDevice();
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private StappCollectionPagerAdapter mStappCollectionPagerAdapter;
	private DataAccess mStappDataAccess;
	private Training mCurrentTraining;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_stapp);

		// Reihenfolge der Initialisierung wichtig!
		initViewPager();
		initActionBar();

		mStappDataAccess = DataAccessFactory.newDataAccess(btDevice, this);

	try {
		btDevice.connect(this);
		if (btDevice.getConnectionState() == ConnectionState.Disconnected) {

			btDevice.enableBT(this);
		}
	} catch (BluetoothException e) {

		// Vorläufig
		Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
	}

		if (savedInstanceState != null) {
			mActionBar.setSelectedNavigationItem(savedInstanceState.getInt(
					"tab", 0));
		}
	}

	private void initViewPager() {
		mStappCollectionPagerAdapter = new StappCollectionPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mStappCollectionPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {

						getActionBar().setSelectedNavigationItem(position);
						invalidateOptionsMenu();
					}
				});
	}

	private void initActionBar() {
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayShowHomeEnabled(false);

		Tab tab = mActionBar.newTab().setText("Trainingseinheit")
				.setTabListener(new TabListener<SessionFragment>(mViewPager));
		mActionBar.addTab(tab);

		tab = mActionBar.newTab().setText("Verlauf")
				.setTabListener(new TabListener<HistoryFragment>(mViewPager));
		mActionBar.addTab(tab);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater menuInflater = getMenuInflater();
//		menuInflater.inflate(R.menu.main, menu);
//
//		return super.onCreateOptionsMenu(menu);
//	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		Fragment fragment = mStappCollectionPagerAdapter.getItem(getActionBar()
				.getSelectedNavigationIndex());
		
		MenuItem start = null;
		MenuItem stop = null;
		MenuItem pause = null;
		for (int i = 0; i < menu.size(); i++) {

			MenuItem curMenuItem = menu.getItem(i);
			switch (curMenuItem.getItemId()) {
			case R.id.action_start:
				start = curMenuItem;
				start.setVisible(true);
				break;
			case R.id.action_pause:
				pause = curMenuItem;
				pause.setVisible(true);
				break;
			case R.id.action_stop:
				stop = curMenuItem;
				stop.setVisible(true);
				break;
			}
		}
		//menu.clear();
		if (fragment.getClass() == SessionFragment.class) {
			// Änderbare Menupunkte
			

			if (mCurrentTraining == null && pause != null && stop != null) {
				stop.setVisible(false);
				pause.setVisible(false);
			}
			else
			{
				TrainingState state = mCurrentTraining.getState();
				if(state == TrainingState.RUNNING)
				{
					if(start != null){

						start.setVisible(true);
					}
				}
				if(state == TrainingState.FINISHED || state == TrainingState.NEW){
					if(pause != null && stop != null){
						stop.setVisible(false);
						pause.setVisible(false);
					}
				}
				if(state == TrainingState.PAUSED){
					if(pause != null)
						pause.setVisible(false);
				}
			}
		}
		else if(fragment.getClass() == HistoryFragment.class){
			start.setVisible(false);
			pause.setVisible(false);
			stop.setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Fragment fragment = mStappCollectionPagerAdapter.getItem(getActionBar()
				.getSelectedNavigationIndex());
				
		switch (item.getItemId()) {
		case R.id.action_start:
			if (fragment.getClass() == SessionFragment.class) {

				mCurrentTraining = mStappDataAccess.newTraining();
				
				//Führt noch zu einer Exception
				mCurrentTraining.start();
				((SessionFragment) fragment).startTraining();
			}
			return true;
		case R.id.action_pause:
			if (fragment.getClass() == SessionFragment.class) {

				((SessionFragment) fragment).pauseTraining();
			}
			return true;
		case R.id.action_stop:
			if (fragment.getClass() == SessionFragment.class) {

				if (mCurrentTraining != null)
					mCurrentTraining.stop();
				((SessionFragment) fragment).stopTraining();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}
}
