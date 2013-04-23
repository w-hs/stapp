package de.whs.stapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import de.whs.stapp.data.bluetooth.BluetoothDevice;
import de.whs.stapp.presentation.views.HistoryFragment;
import de.whs.stapp.presentation.views.SessionFragment;
import de.whs.stapp.presentation.views.StappCollectionPagerAdapter;
import de.whs.stapp.presentation.views.TabListener;

/**
 * 
 * @author Thomas
 * 
 */
public class StappActivity extends FragmentActivity {
	
	private BluetoothDevice btDevice = new BluetoothDevice();

	ViewPager mViewPager;
	StappCollectionPagerAdapter mStappCollectionPagerAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_stapp);
		
		mStappCollectionPagerAdapter = new StappCollectionPagerAdapter(getSupportFragmentManager());
		  mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mStappCollectionPagerAdapter);
	    
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		
		Tab tab = actionBar.newTab()
	            .setText("Trainingseinheit")
	            .setTabListener(new TabListener<SessionFragment>(
	                    this, "training", SessionFragment.class, mViewPager));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	        .setText("Verlauf")
	        .setTabListener(new TabListener<HistoryFragment>(
	                this, "history", HistoryFragment.class, mViewPager));
	    actionBar.addTab(tab);

		try {
			btDevice.connect(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
        
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
