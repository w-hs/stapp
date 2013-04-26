package de.whs.stapp.presentation;

import java.util.ArrayList;
import java.util.Date;

import de.whs.stapp.data.storage.TrainingSession;
import de.whs.stapp.presentation.viewmodels.History;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
	public void onPageFinished(WebView view, String url){
		
		if(view.getClass() == HistoryWebView.class){
			//Testaufbau
			ArrayList<TrainingSession> sessions = new ArrayList<TrainingSession>();
			sessions.add(new TrainingSession(1, new Date(), 2500, 326548));
			sessions.add(new TrainingSession(2, new Date(), 4100, 356841));
			sessions.add(new TrainingSession(3, new Date(), 32654, 7854));
			sessions.add(new TrainingSession(4, new Date(), 1598, 5648541));
			sessions.add(new TrainingSession(5, new Date(), 7845, 75753));
			sessions.add(new TrainingSession(6, new Date(), 3121, 1745));
			sessions.add(new TrainingSession(7, new Date(), 123, 78674));
			sessions.add(new TrainingSession(8, new Date(), 2500, 326548));
			sessions.add(new TrainingSession(9, new Date(), 4100, 356841));
			sessions.add(new TrainingSession(10, new Date(), 32654, 7854));
			sessions.add(new TrainingSession(11, new Date(), 1598, 5648541));
			sessions.add(new TrainingSession(12, new Date(), 7845, 75753));
			sessions.add(new TrainingSession(13, new Date(), 3121, 1745));
			sessions.add(new TrainingSession(14, new Date(), 123, 78674));
			History h = new History(sessions);
			((HistoryWebView)view).setHistoryData(h);
		}
	}
}
