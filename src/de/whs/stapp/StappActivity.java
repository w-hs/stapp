package de.whs.stapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
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
import de.whs.stapp.data.bluetooth.BluetoothAdapterDisabledException;
import de.whs.stapp.data.bluetooth.BluetoothConnection;
import de.whs.stapp.data.bluetooth.BluetoothException;
import de.whs.stapp.presentation.views.HistoryFragment;
import de.whs.stapp.presentation.views.SessionFragment;
import de.whs.stapp.presentation.views.StappCollectionPagerAdapter;
import de.whs.stapp.presentation.views.StappPreferenceActivity;
import de.whs.stapp.presentation.views.TabListener;

/**
 * Standard-Einstiegspunkt für das Stapp-Projekt. Enthält die Activity, welche
 * das gesamte Produkt verwaltet.
 * 
 * @author Thomas
 * 
 */
public class StappActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private StappCollectionPagerAdapter mStappCollectionPagerAdapter;

	private BluetoothConnection mBluetooth;
	private DataAccess mStappDataAccess;
	private Training mCurrentTraining;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_stapp);

		// Reihenfolge der Initialisierung wichtig!
		initViewPager();
		initActionBar();

		initBluetoothConnection();
		initDataAccess();

		if (savedInstanceState != null) {
			mActionBar.setSelectedNavigationItem(savedInstanceState.getInt(
					"tab", 0));
		}
	}

	private void initDataAccess() {
		mStappDataAccess = DataAccessFactory.newDataAccess(
				mBluetooth.getDataTracker(), getApplicationContext());
	}

	private void initBluetoothConnection() {
		mBluetooth = new BluetoothConnection(getApplicationContext());
		try {
			mBluetooth.open();
		} catch (BluetoothAdapterDisabledException e) {
			mBluetooth.turnOnBt(this);
		} catch (BluetoothException e) {
			// Vorläufig
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		mBluetooth.close();
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == BluetoothConnection.REQUEST_CODE
				&& resultCode != RESULT_CANCELED) {
			try {
				mBluetooth.open();
			} catch (BluetoothException e) {
				// Vorläufig
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			}
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

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		menu.clear();
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
		
		if (fragment.getClass() == SessionFragment.class) {

			// Änderbare Menupunkte
			if (mCurrentTraining == null && pause != null && stop != null) {
				stop.setVisible(false);
				pause.setVisible(false);
			} else {
				TrainingState state = mCurrentTraining.getState();
				if (state == TrainingState.RUNNING && start != null)
					start.setVisible(true);

				if (state == TrainingState.FINISHED
						|| state == TrainingState.NEW && pause != null
						&& stop != null) {
					stop.setVisible(false);
					pause.setVisible(false);
				}
				if (state == TrainingState.PAUSED && pause != null) {
					pause.setVisible(false);
				}
			}
		} else if (fragment.getClass() == HistoryFragment.class) {
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

		boolean returnValue = false;

		switch (item.getItemId()) {
		case R.id.action_start:
			if (fragment.getClass() == SessionFragment.class) {

				mCurrentTraining = mStappDataAccess.newTraining();

				// Führt noch zu einer Exception
				mCurrentTraining.start();
				((SessionFragment) fragment).startTraining();
				mCurrentTraining.registerListener((SessionFragment) fragment);
			}
			returnValue = true;
			break;
		case R.id.action_pause:
			if (fragment.getClass() == SessionFragment.class) {

				((SessionFragment) fragment).pauseTraining();
				mCurrentTraining.pause();
			}
			returnValue = true;
			break;
		case R.id.action_stop:
			if (fragment.getClass() == SessionFragment.class) {

				if (mCurrentTraining != null)
					mCurrentTraining.stop();
				((SessionFragment) fragment).stopTraining();
				mCurrentTraining.unregisterListener((SessionFragment) fragment);
			}
			returnValue = true;
			break;
		case R.id.settings:
			Intent intent = new Intent(this, StappPreferenceActivity.class);
			startActivity(intent);
			return true;
		default:
			returnValue = super.onOptionsItemSelected(item);
			break;
		}
		
		this.invalidateOptionsMenu();
		
		return returnValue;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}

	/**
	 * Stellt einen getter bereit, damit andere Fragmente auch auf den
	 * DataAccess zugreifen können.
	 * 
	 * @return Gibt den DataAccess zurück.
	 */
	public DataAccess getStappDataAccess() {
		return mStappDataAccess;
	}
}
