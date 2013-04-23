package de.whs.stapp.presentation.views;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import de.whs.stapp.R;
import de.whs.stapp.presentation.TrainingSessionWebView;

/**
 * Das Trainingseinheiten Fragment/Tab welches ein Web View über Trainingseinheiten Informationen beinhaltet.
 * @author DanielW7
 *
 */
public class SessionFragment extends Fragment {
	
	private TrainingSessionWebView mTrainingseinheitWebview;
	private Button mbtnStart;
	/**
	 * Konstruktor des Tabs in dem Das Layout geladen wird.
	 * @param inflater inflater
	 * @param container container
	 * @param savedInstanceState savedInstanceState
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
            return null;
        }
		return (RelativeLayout)inflater.inflate(R.layout.tab_trainingseinheit_layout, container, false);
	}
	
	/**
	 * Wird aufgerufen wenn alle Layouts und Activitys geladen sind.
	 * @param savedInstanceState Saved State
	 */
	@Override 
	public void onActivityCreated(Bundle savedInstanceState) { 
	    super.onActivityCreated(savedInstanceState);   
	    
	    
	    mTrainingseinheitWebview = new TrainingSessionWebView(this.getActivity());
        RelativeLayout relativeLayout = 
                        (RelativeLayout) this.getActivity().findViewById(R.id.WrapperTrainingseinheit);
        RelativeLayout.LayoutParams relParams = 
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        relativeLayout.addView(mTrainingseinheitWebview, relParams);
	}
}
