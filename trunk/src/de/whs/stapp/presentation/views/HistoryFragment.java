/**
 * 
 */
package de.whs.stapp.presentation.views;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import de.whs.stapp.R;
import de.whs.stapp.StappActivity;
import de.whs.stapp.data.access.DataAccess;
import de.whs.stapp.data.storage.TrainingSession;
import de.whs.stapp.presentation.HistoryWebView;
import de.whs.stapp.presentation.viewmodels.History;

/**
 * Das History Fragment/Tab welches ein Web View über History Informationen beinhaltet.
 * @author DanielW7
 *
 */
public class HistoryFragment extends Fragment {

	private HistoryWebView mVerlaufWebview;
	/**
	 * Konstruktor des Tabs in dem Das Layout geladen wird.
	 * @param inflater inflater
	 * @param container container
	 * @param savedInstanceState savedInstanceState
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
            return null;
        }
		return (RelativeLayout)inflater.inflate(R.layout.tab_history_layout, container, false);
	}
	
	/**
	 * Wird aufgerufen wenn alle Layouts und Activitys geladen sind.
	 * @param savedInstanceState Saved State
	 */
	@Override 
	public void onActivityCreated(Bundle savedInstanceState) { 
	    super.onActivityCreated(savedInstanceState);       
	    
	    mVerlaufWebview = new HistoryWebView(this.getActivity());
        RelativeLayout relativeLayout = 
                        (RelativeLayout) this.getActivity().findViewById(R.id.WrapperHistory);
        RelativeLayout.LayoutParams relParams = 
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        
        relativeLayout.addView(mVerlaufWebview, relParams);
	}

	/**
	 * Lädt die History-Daten in die Webseite.
	 */
	public void setHistory()
	{
		StappActivity sa = (StappActivity) this.getActivity();
		DataAccess dataAccess = sa.getStappDataAccess();
		ArrayList<TrainingSession> sessions = (ArrayList<TrainingSession>) dataAccess
				.getSessionHistory();
		History h = new History(sessions);
		mVerlaufWebview.setHistoryData(h);
	}
}
