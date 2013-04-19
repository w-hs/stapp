/**
 * 
 */
package de.whs.stapp.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import de.whs.stapp.R;
import de.whs.stapp.presentation.HistoryWebView;

/**
 * Das History Fragment/Tab welches ein Web View über History Informationen beinhaltet.
 * @author DanielW7
 *
 */
public class TabHistoryFragment extends Fragment {

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
                new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        relativeLayout.addView(mVerlaufWebview, relParams);
	}

}
