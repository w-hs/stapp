package de.whs.stapp;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import de.whs.stapp.data.access.DataAccess;
import de.whs.stapp.data.access.DataAccessFactory;
import de.whs.stapp.data.access.Training;
import de.whs.stapp.data.access.TrainingState;
import de.whs.stapp.data.bluetooth.BatteryChargeListener;
import de.whs.stapp.data.bluetooth.BluetoothAdapterDisabledException;
import de.whs.stapp.data.bluetooth.BluetoothConnection;
import de.whs.stapp.data.bluetooth.BluetoothException;
import de.whs.stapp.data.storage.TrainingSession;
import de.whs.stapp.presentation.views.AppInfoActivity;
import de.whs.stapp.presentation.views.GuidedTourActivity;
import de.whs.stapp.presentation.views.HistoryFragment;
import de.whs.stapp.presentation.views.ImpressumActivity;
import de.whs.stapp.presentation.views.SessionFragment;
import de.whs.stapp.presentation.views.StappCollectionPagerAdapter;
import de.whs.stapp.presentation.views.TabListener;

/**
 * Standard-Einstiegspunkt f�r das Stapp-Projekt. Enth�lt die Activity, welche
 * das gesamte Produkt verwaltet.
 * 
 * @author Thomas
 * 
 */
public class StappActivity extends FragmentActivity {

	private static final String LOG_TAG = "StappActivity";
	
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private StappCollectionPagerAdapter mStappCollectionPagerAdapter;

	private BluetoothConnection mBluetooth;
	private DataAccess mStappDataAccess;
	private Training mCurrentTraining;
	
	public Context mContext;
	
	private SharedPreferences prefs = null;

	/**
	 * 
	 * @return Das aktuelle Training.
	 */
	public Training getCurrentTraining() {
		return mCurrentTraining;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_stapp);

		// Reihenfolge der Initialisierung wichtig!
		initViewPager();
		initActionBar();

		initBluetoothConnection();
		initDataAccess();
		
		mContext = this;
		
		List<TrainingSession> sessions = mStappDataAccess.getSessionHistory();
		for (TrainingSession session : sessions) {
			Log.d("Test", session.toString());
		}

		if (savedInstanceState != null) {
			mActionBar.setSelectedNavigationItem(savedInstanceState.getInt(
					"tab", 0));
		}
		
		prefs = getSharedPreferences("de.whs.stapp", MODE_PRIVATE);
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
			// Vorl�ufig
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
		// Anzeige des Ladezustandes im Men�
		mBluetooth.setBatteryChargeListener(new BatteryChargeListener() {
			@Override
			public void onChange(int charge) {
				Log.i(LOG_TAG, "Battey charge: " + charge);
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						invalidateOptionsMenu();
					}
				});
			}
		});
	}

	@Override
	protected void onResume(){
		super.onResume();
		
		if(prefs.getBoolean("firstrun", true)){

			prefs.edit().putBoolean("firstrun", false).commit();
			Intent intent = new Intent(this, GuidedTourActivity.class);
			startActivity(intent);			
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
				// Vorl�ufig
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
		MenuItem battery = null;
		MenuItem bluetooth = null;
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
			case R.id.action_bluetooth:
				bluetooth = curMenuItem;
				if(mBluetooth.isOpen())
					bluetooth.setIcon(R.drawable.access_bluetooth_connected);
				else
					bluetooth.setIcon(R.drawable.access_bluetooth);
				break;
			case R.id.action_power:
				battery = curMenuItem;
				battery.setIcon(getBatteryIcon());
				break;
			}
		}
		
		if (fragment.getClass() == SessionFragment.class) {
			
			// �nderbare Menupunkte
			if (mCurrentTraining == null && pause != null && stop != null) {
				stop.setVisible(false);
				pause.setVisible(false);
			} else {
				TrainingState state = mCurrentTraining.getState();
				if (state == TrainingState.RUNNING && start != null)
					start.setVisible(false);

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
			
			boolean showBattery = mBluetooth.isOpen() && mBluetooth.getLastBatteryCharge() > -1;
			battery.setVisible(showBattery);
			
		} else if (fragment.getClass() == HistoryFragment.class) {
			start.setVisible(false);
			pause.setVisible(false);
			stop.setVisible(false);
			bluetooth.setVisible(false);
			battery.setVisible(false);
		}
		
		
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Liefert ein zum Ladezustand passendes Icon.
	 * 
	 * @return ID des passenden Icons.
	 */
	private int getBatteryIcon() {
		int charge = mBluetooth.getLastBatteryCharge();
		
		if (charge < 5)
			return R.drawable.battery_zero;
		else if (charge < 30)
			return R.drawable.battery_low;
		else if (charge < 60)
			return R.drawable.battery_mid;
		else if (charge < 90)
			return R.drawable.battery_high;
		else
			return R.drawable.battery_full;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Fragment fragment = mStappCollectionPagerAdapter.getItem(getActionBar()
				.getSelectedNavigationIndex());

		boolean returnValue = false;

		switch (item.getItemId()) {
		case R.id.action_start:
			returnValue = onStartOption(fragment);
			break;
		case R.id.action_pause:
			returnValue = onPauseOption(fragment);
			break;
		case R.id.action_stop:
			returnValue = onStopOption(fragment);
			break;
		case R.id.action_bluetooth:
			if(mBluetooth.isOpen())
			{}
			else
				try {
					mBluetooth.open();
				} catch (BluetoothException e) {
					// Vorl�ufig
					Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			break;
		case R.id.action_power:
			Toast.makeText(this, getResources().getString(R.string.battery_charge) 
					+ ": " + mBluetooth.getLastBatteryCharge() + "%", 
					Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.about:
			Intent impressumIntent = new Intent(this, ImpressumActivity.class);
			startActivity(impressumIntent);
			break;
		case R.id.help:
			Intent helpIntent = new Intent(this, GuidedTourActivity.class);
			startActivity(helpIntent);
			break;
		case R.id.info:
			Intent appInfoIntent = new Intent(this, AppInfoActivity.class);
			startActivity(appInfoIntent);
			break;
	
		default:
			returnValue = super.onOptionsItemSelected(item);
			break;
		}
		
		this.invalidateOptionsMenu();
		
		return returnValue;
	}

	private boolean onStopOption(Fragment fragment) {
		if (fragment.getClass() == SessionFragment.class) {

			if (mCurrentTraining != null)
				mCurrentTraining.stop();
			((SessionFragment) fragment).stopTraining();
			mCurrentTraining.unregisterListener((SessionFragment) fragment);
			
			AlertDialog dialog = createSaveTrainingDialog();
			dialog.show();
		}
		
		return true;
	}

	private boolean onPauseOption(Fragment fragment) {
		if (fragment.getClass() == SessionFragment.class) {

			((SessionFragment) fragment).pauseTraining();
			mCurrentTraining.pause();
		}

		return true;
	}

	private boolean onStartOption(Fragment fragment) {
		if (fragment.getClass() == SessionFragment.class) {

			if(mCurrentTraining != null && mCurrentTraining.getState() == TrainingState.PAUSED)
				mCurrentTraining.resume();
			else  {
				mCurrentTraining = mStappDataAccess.newTraining();
				mCurrentTraining.start();
			}
			
			((SessionFragment) fragment).startTraining();
			mCurrentTraining.registerListener((SessionFragment) fragment);
		}

		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}

	/**
	 * Stellt einen getter bereit, damit andere Fragmente auch auf den
	 * DataAccess zugreifen k�nnen.
	 * 
	 * @return Gibt den DataAccess zur�ck.
	 */
	public DataAccess getStappDataAccess() {
		return mStappDataAccess;
	}
	
	private AlertDialog createSaveTrainingDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setMessage(R.string.dialog_save_training_message)
			.setTitle(R.string.dialog_save_training_title)
			.setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i(LOG_TAG, "Training was saved");
				}
			})
			.setNegativeButton(R.string.dialog_discard, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i(LOG_TAG, "Training is going to be discarded");
					mCurrentTraining.discardSession();
					Log.i(LOG_TAG, "Training was discarded");
				}
			});
		
		return builder.create();
	}
}
