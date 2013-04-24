package de.whs.stapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
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
 * 
 * @author Thomas
 * 
 */
public class StappActivity extends FragmentActivity {

	private BluetoothDevice btDevice = new BluetoothDevice();
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private StappCollectionPagerAdapter mStappCollectionPagerAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_stapp);

		// Reihenfolge der Initialisierung wichtig!
		initViewPager();
		initActionBar();

		try {
			btDevice.connect(this);
			if(btDevice.getConnectionState() == ConnectionState.Disconnected){
				
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch (item.getItemId()) {
		case R.id.action_start:
			Log.i("action_start", "Play gedrückt");
			return true;
		case R.id.action_pause:
			Log.i("action_pause", "Pause gedrückt");
			return true;
		case R.id.action_stop:
			Log.i("action_stop", "Stop gedrückt");
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
