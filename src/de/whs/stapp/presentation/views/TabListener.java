package de.whs.stapp.presentation.views;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

/**
 * Einfacher Listener, welcher auf das Ändern von Tabs reagiert.
 * 
 * @author Thomas
 * 
 * @param <T>
 *            Der TabListener kann nur mit Fragmenten genutzt werden.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {

	private final ViewPager mViewPager;

	/**
	 * Standard-Konstruktor für die TabListener Klasse.
	 * @param viewPager
	 *            ViewPager, der das Swipen innerhalb der Fragmente unterstützt.
	 */
	public TabListener(ViewPager viewPager) {
		mViewPager = viewPager;
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
	}
}
