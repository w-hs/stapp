package de.whs.stapp.data.access;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import de.whs.stapp.data.bluetooth.DataTracker;
import de.whs.stapp.data.bluetooth.TrackedDataItem;
import de.whs.stapp.data.bluetooth.TrackedDataListener;
import de.whs.stapp.data.storage.DatabaseAdapter;
import de.whs.stapp.data.storage.SessionDetail;
import de.whs.stapp.data.storage.TrainingSession;

/**
 * Verwaltet eine Trainingseinheit.
 * @author Chris
 */
public class Training {
	
	private static final String LOG_TAG = "Training";
	
	private DataTracker tracker;
	private DatabaseAdapter database;	
	private TrackedDataListener dataListener;

	private StopWatch stopWatch = new StopWatch();
	private TrainingState state = TrainingState.NEW;
	private TrainingSession currentSession = new TrainingSession();
	private TrackedDataItemConverter converter = new TrackedDataItemConverter();
	
	private List<SessionDetailListener> sessionDetailListener =
			new ArrayList<SessionDetailListener>();


	/**
	 * Erzeugt eine neue Instanz der {@link Training} Klasse.
	 * @param tracker Eine {@link DataTracker} Implementierung.
	 * @param database Eine {@link DatabaseAdapter} Implementierung.
	 */
	public Training(DataTracker tracker, DatabaseAdapter database) {		
		if (database == null)
			throw new IllegalArgumentException("databaseAdapter cannot be null");
		if (tracker == null)
			throw new IllegalArgumentException("serviceConnection cannot be null");

		this.database = database;
		this.tracker = tracker;
		
		initializeDataListener();
	}
	
	/**
	 * @return Die aktuelle {@link TrainingSession}.
	 */
	public TrainingSession getCurrentSession() {
		return currentSession;
	}

	/**
	 * @return Der {@link TrainingState} des Trainings.
	 */
	public TrainingState getState() {
		return state;
	}

	/**
	 * Startet das {@link Training}.
	 */
	public void start() {	
		if (state != TrainingState.NEW)
			return;

		state = TrainingState.RUNNING;
		currentSession = database.newTrainingSession();
		converter.prepareForFirstPackage();
		tracker.registerListener(dataListener);
		stopWatch.start();
		
		Log.i(LOG_TAG, "Training is started. " + this.toString());
	}
	
	/**
	 * Pausiert das {@link Training}.
	 */
	public void pause() {
		if (state == TrainingState.PAUSED)
			return;
		
		stopWatch.stop();
		state = TrainingState.PAUSED;
		
		Log.i(LOG_TAG, "Training is paused. " + this.toString());
	}
	
	/**
	 * Setzt ein pausiertes {@link Training} fort.
	 */
	public void resume() {
		if (state != TrainingState.PAUSED)
			return;
		
		// Während der Pause hat sich der interne Zustand des Sensors u.U. geändert,
		// deshalb dürfen die Berechnungen nicht auf dem letzten Paket basieren
		converter.prepareForFirstPackage();
		
		state = TrainingState.RUNNING;
		stopWatch.start();
		
		Log.i(LOG_TAG, "Training is resumed. " + this.toString());
	}	

	/**
	 * Beendet das {@link Training}.
	 */
	public void stop() {
		if (state == TrainingState.FINISHED) 
			return;
		
		stopWatch.stop();	
		
		tracker.unregisterListener(dataListener);
		state = TrainingState.FINISHED;
		
		currentSession.setDurationInMs(stopWatch.getElapsedMilliseconds());
		database.updateTrainingSession(currentSession);
		
		Log.i(LOG_TAG, "Training is stopped. " + this.toString());
	}
	
	/**
	 * Verwirft die aktuelle {@link TrainingSession}.
	 * (Der Datensatz wird das der Datenbank gelöscht)
	 */
	public void discardSession() {
		try {
			Log.d(LOG_TAG, "Try to discard the currentSession: " + currentSession);
			database.deleteTrainingSession(currentSession.getSessionId());
		}
		catch (Exception e) {
			Log.e(LOG_TAG, "Error while discarding the currentSession: " + currentSession, e);
		}
	}

	/**
	 * Registriert einen listener, der daraufhin auf
	 * {@link SessionDetail}s hören kann.
	 * @param listener Der {@link SessionDetailListener}.
	 */
	public void registerListener(SessionDetailListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");
		
		if (!sessionDetailListener.contains(listener)) {
			sessionDetailListener.add(listener);
			Log.d(LOG_TAG, "A new SessionDetailListener is registered.");
		}
	}

	/**
	 * Entfernt einen registrierten listener wieder.
	 * @param listener Der registrierte {@link SessionDetailListener}.
	 */
	public void unregisterListener(SessionDetailListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");

		if (sessionDetailListener.contains(listener)) {
			sessionDetailListener.remove(listener);
			Log.d(LOG_TAG, "A SessionDetailListener is unregistered.");
		}
	}

	private void initializeDataListener() {
		dataListener = new TrackedDataListener() {
			@Override
			public void trackData(TrackedDataItem dataItem) {
				assert dataItem != null : "dateItem cannot be null";
				Log.d(LOG_TAG, "Recieved a new trackedDataItem " + dataItem.toString());
				if (state == TrainingState.RUNNING)
					processTrackedData(dataItem);
			}
		};
	}

	private void processTrackedData(TrackedDataItem dataItem) {		
		Log.d(LOG_TAG, "The recieved trackedDataItem will be processed now.");
		final int trainingSessionId = currentSession.getSessionId();
		
		SessionDetail detail = converter.toSessionDetail(dataItem);
		detail.setTrainingSessionId(trainingSessionId);
				
		notifyTrainingSessionListeners(detail);
		database.storeSessionDetail(detail);
		
		Log.d(LOG_TAG, "Created a new SessionDetail instance: " + detail.toString());
		
		double distanceInMeters = currentSession.getDistanceInMeters() + detail.getDistanceInMeter();
		currentSession.setDistanceInMeters(distanceInMeters);
	}

	private void notifyTrainingSessionListeners(SessionDetail sessionDetail) {
		for (SessionDetailListener listener : sessionDetailListener)
			listener.listen(sessionDetail);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Training [state=" + state + ", currentSession="
				+ currentSession + "]";
	}
}
