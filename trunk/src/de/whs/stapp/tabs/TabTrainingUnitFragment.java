package de.whs.stapp.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import de.whs.stapp.R;
import de.whs.stapp.presentation.TrainingUnitWebView;

/**
 * Das Trainingseinheiten Fragment/Tab welches ein Web View über Trainingseinheiten Informationen beinhaltet.
 * @author DanielW7
 *
 */
public class TabTrainingUnitFragment extends Fragment {
	
	private TrainingUnitWebView mTrainingseinheitWebview;
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
	    
	    
	    mTrainingseinheitWebview = new TrainingUnitWebView(this.getActivity());
        RelativeLayout relativeLayout = 
                        (RelativeLayout) this.getActivity().findViewById(R.id.WrapperTrainingseinheit);
        RelativeLayout.LayoutParams relParams = 
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        relativeLayout.addView(mTrainingseinheitWebview, relParams);
        
        //Hinzufügen eines Test Buttons in den Footer
        RelativeLayout footer = 
                (RelativeLayout) this.getActivity().findViewById(R.id.footer);
        
        mbtnStart = new Button(getActivity());
        
        mbtnStart.setText("Start");
        mbtnStart.setLayoutParams(new LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        footer.addView(mbtnStart);
        
        
        //OnClick Event Handler zu Testzwecken. Kann später wieder entfernt werden.
        mbtnStart.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(mTrainingseinheitWebview != null)
					mTrainingseinheitWebview.startTraining();
			}
		});
		 
	}
	
	/**
	 * macht alle buttons dieses Tab im Footer unsichtbar.
	 */
	public void hideButtons()
	{
		mbtnStart.setVisibility(View.GONE);
	}
	
	/**
	 * Zeiggt alle Buttons des Tabs im Footer wider an.
	 */
	public void showButtons()
	{
		mbtnStart.setVisibility(View.VISIBLE);
	}
}
