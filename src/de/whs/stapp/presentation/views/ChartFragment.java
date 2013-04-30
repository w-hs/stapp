package de.whs.stapp.presentation.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import de.whs.stapp.R;
import de.whs.stapp.presentation.ChartWebAppInterface;
import de.whs.stapp.presentation.ChartWebView;

/**
 * Das Chart Fragment/Tab welches ein Web View über Chart Informationen
 * beinhaltet.
 * 
 * @author Thomas
 * 
 */
public class ChartFragment extends Fragment {
	private ChartWebView mChartWebView;

	/**
	 * Konstruktor des Tabs in dem Das Layout geladen wird.
	 * 
	 * @param inflater
	 *            inflater
	 * @param container
	 *            container
	 * @param savedInstanceState
	 *            savedInstanceState
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		return inflater.inflate(R.layout.chart_layout, container, false);
	}

	/**
	 * Wird aufgerufen wenn alle Layouts und Activitys geladen sind.
	 * 
	 * @param savedInstanceState
	 *            Saved State
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mChartWebView = new ChartWebView(this.getActivity(),
				new ChartWebAppInterface(getActivity().getApplicationContext()));
		RelativeLayout relativeLayout = (RelativeLayout) this.getActivity()
				.findViewById(R.id.ChartWrapper);
		RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		relativeLayout.addView(mChartWebView, relParams);
	}
}
