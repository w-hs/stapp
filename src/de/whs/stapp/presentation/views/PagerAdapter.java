package de.whs.stapp.presentation.views;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
/**
 * Page Adapter wird benötigt um die Tab Fragments (Seite der Tabs) zu handhaben.
 * 
 * @author Daniel
 *
 */
public class PagerAdapter extends FragmentPagerAdapter {
 
    private List<Fragment> fragments;
   
    /**
     * 
     * @param fm Manager der Tab Fragments
     * @param fragments liste alles Tab Fragments
     */
    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    /**
     * @param position Nummer des angeforderten tabs
     * @return Das angeforderte Fragment/Tabs
     * 
     */
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }
 
    
    /**
     * @return Anzahl der Fragmente/Tabs
     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }
}