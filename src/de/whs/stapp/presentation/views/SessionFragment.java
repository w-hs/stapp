package de.whs.stapp.presentation.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import de.whs.stapp.R;
import de.whs.stapp.StappActivity;
import de.whs.stapp.data.access.SessionDetailListener;
import de.whs.stapp.data.storage.SessionDetail;
import de.whs.stapp.presentation.viewmodels.TrainingSession;
import de.whs.stapp.presentation.webviews.TrainingSessionWebView;

/**
 * Das Trainingseinheiten Fragment/Tab welches ein Web View �ber
 * Trainingseinheiten Informationen beinhaltet.
 * 
 * @author DanielW7
 * 
 */
public class SessionFragment extends Fragment implements SessionDetailListener {

	private TrainingSessionWebView mTrainingseinheitWebview;

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
		return (RelativeLayout) inflater.inflate(
				R.layout.tab_trainingsession_layout, container, false);
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

		mTrainingseinheitWebview = new TrainingSessionWebView(
				this.getActivity());
		RelativeLayout relativeLayout = (RelativeLayout) this.getActivity()
				.findViewById(R.id.WrapperTrainingseinheit);
		RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		relativeLayout.addView(mTrainingseinheitWebview, relParams);
	}

	/**
	 * F�hrt die startTrainings-Methode der WebView aus.
	 */
	public void startTraining() {
		mTrainingseinheitWebview.startTraining();
	}

	/**
	 * F�hrt die stopTraining-Methode der WebView aus.
	 */
	public void stopTraining() {
		mTrainingseinheitWebview.stopTraining();
	}


	/**
	 * F�hrt die pauseTraining-Funktion der WebView aus.
	 */
	public void pauseTraining() {
		mTrainingseinheitWebview.pauseTraining();
	}

	@Override
	public void listen(SessionDetail detail) {
		
		StappActivity activity = (StappActivity)getActivity();
		TrainingSession trainingData = new TrainingSession(
				(int)activity.getCurrentTraining().getCurrentSession().getDistanceInMeters(),
				detail.getHeartRateInBpm());
		mTrainingseinheitWebview.updateTrainingData(trainingData);
	}
}
