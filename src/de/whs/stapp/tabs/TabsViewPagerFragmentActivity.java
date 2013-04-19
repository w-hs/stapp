package de.whs.stapp.tabs;

import java.util.List;
import java.util.Vector;
 
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
 
import de.whs.stapp.R;

 
/**
 * Activity zur verwaltung der Swipable Tabs.
 * @author DanielW7
 *
 */
public class TabsViewPagerFragmentActivity extends FragmentActivity 
				implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
 
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private final String mTabHistoryText = "History";
    private final String mTabTrainingseinheitText = "Trainingseinheit";
    

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        // Initialisieren des TabHosts
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
        	//Trainigseinheiten als Start Tab
            mTabHost.setCurrentTabByTag(savedInstanceState.getString(mTabTrainingseinheitText));
        }
        // Initialisieren des ViewPagers
        this.intialiseViewPager();
        
        //Optischer Trenner zwischen den Tabs hinzufügen
        //mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
        
	   	Button btnSettings = (Button) this.findViewById(R.id.btnSettings);
			
		 
		btnSettings.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				openOptionsMenu();
			}
		});
	 
	
    }
 
    
    /**
     * Initialiert den ViewPager.
     */
    private void intialiseViewPager() {
 
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, TabTrainingUnitFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, TabHistoryFragment.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }
 
    /**
     * 
     * Initialisiert den Tab Host. 
     */
    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
       
       
        //+++++++++++++++++++TAB TRAININGSEINHEITEN+++++++++++++++++++
        TabsViewPagerFragmentActivity.addTab(
        		this, 
        		this.mTabHost, 
        		this.mTabHost.newTabSpec(mTabTrainingseinheitText).setIndicator(
        				createTabView(mTabHost.getContext(), mTabTrainingseinheitText)));
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
     
        //++++++++++++++++++++++++TAB HISTORY++++++++++++++++++++++++
        TabsViewPagerFragmentActivity.addTab(
        		this, 
        		this.mTabHost, 
        		this.mTabHost.newTabSpec(mTabHistoryText).setIndicator(
        				createTabView(mTabHost.getContext(), mTabHistoryText)));
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        mTabHost.setOnTabChangedListener(this);
    }
 
    
   /**
    * Erstellt das Tab View. Hier wird das Layout der Tabs erstellt.
    * @param context Context 
    * @param text test den das Tab trägt
    * @return die erstellte View
    */
    private static View createTabView(final Context context, final String text) {
    	View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
    	TextView tv = (TextView) view.findViewById(R.id.tabsText);
    	tv.setText(text);
    	return view;
    }
    
    /**
     * Erstellt ein neuen Tab mit den angegebenen Spezifikationen 
     * und fügt es dem TabHost hinzu.
     * 
     * @param activity Die Activity
     * @param tabHost Der Tab Host
     * @param tabSpec Die Spezifikationen
     * @param tabInfo Informationen
     */
    private static void addTab(TabsViewPagerFragmentActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec) {
    	//Fügt eine Tab View Factory zu den Spezifikationen hinzu
        tabSpec.setContent(new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }
 
   /**
    * Wird aufgerufen wenn sich der Tab ändert.
    * @param tag Bezeicher des Tabs
    */
    public void onTabChanged(String tag) { 
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
        
        //es gibt nur 2 Tabs
        if(pos == 0)
        {
        	//Tab Trainingseinheiten
        	((TabTrainingUnitFragment)mPagerAdapter.getItem(0)).showButtons();
        }
        else
        {
        	//Tab History
        	((TabTrainingUnitFragment)mPagerAdapter.getItem(0)).hideButtons();
        }
        
    }
 
    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {}
 
    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
    }
 
    @Override
    public void onPageScrollStateChanged(int state) {}
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
		/*getMenuInflater().inflate(R.menu.main, menu);
		return true;*/
	}	

}