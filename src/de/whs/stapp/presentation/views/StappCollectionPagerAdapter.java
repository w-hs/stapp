package de.whs.stapp.presentation.views;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Bietet eine Kollektion aller verfügbaren Fragmente der Stapp Anwendung an.
 * 
 * @author Thomas
 * 
 */
public class StappCollectionPagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

	/**
	 * Initialisiert alle verfügbaren Fragmente und fügt diese dem
	 * FragmentManager hinzu.
	 * 
	 * @param fm
	 *            Der FragmentManger zu dem die Fragmente der Stapp-Anwendung
	 *            hinzugefügt werden.
	 */
	public StappCollectionPagerAdapter(FragmentManager fm) {
		super(fm);

		mFragments.add(new SessionFragment());
		mFragments.add(new HistoryFragment());
	}

	@Override
	public Fragment getItem(int index) {
		if (index < 0 && index > mFragments.size())
			throw new IndexOutOfBoundsException();
		
		Fragment activFragment = mFragments.get(index);
		
		if (activFragment.getClass() == HistoryFragment.class) {
			((HistoryFragment)activFragment).setHistory();
		}
		
		return activFragment;
	}

	@Override
	public int getCount() {

		return mFragments.size();
	}

}
