package de.whs.stapp.presentation;

import java.util.ArrayList;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import de.whs.stapp.StappActivity;
import de.whs.stapp.data.access.DataAccess;
import de.whs.stapp.data.storage.TrainingSession;
import de.whs.stapp.presentation.viewmodels.History;
import de.whs.stapp.presentation.views.ChartActivity;

/**
 * Bietet die Möglichkeit auf Aktionen innerhalb der WebView zu reagieren.
 * 
 * @author Thomas
 * 
 */
public class StappWebViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {

		return true;
	}

	@Override
	public void onPageFinished(WebView view, String url) {

		if (view.getClass() == HistoryWebView.class) {
			// Testaufbau

			Context context = view.getContext();
			StappActivity sa = (StappActivity) context;
			DataAccess dataAccess = sa.getStappDataAccess();
			ArrayList<TrainingSession> sessions = (ArrayList<TrainingSession>) dataAccess
					.getSessionHistory();
			History h = new History(sessions);
			((HistoryWebView) view).setHistoryData(h);
		}
		else if (view.getClass() == ChartWebView.class){
			Context context = view.getContext();
			ChartActivity ca = (ChartActivity) context;

			((ChartWebView)view).setChart(ca.getChartData(50));
		}
	}
}
