package de.whs.stapp.data.access;

import java.util.ArrayList;
import java.util.List;

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
	
	private DataTracker tracker;
	private DatabaseAdapter database;	
	private TrackedDataListener dataListener;

	private StopWatch stopWatch = new StopWatch();
	private TrainingState state = TrainingState.NEW;
	private TrainingSession currentSession = new TrainingSession();
	private TrackedDataItemConverter dataItemConverter = new TrackedDataItemConverter();
	
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
		tracker.registerListener(dataListener);
		
		stopWatch.start();
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
	}
	
	/**
	 * Pausiert das {@link Training}.
	 */
	public void pause() {
		if (state == TrainingState.PAUSED)
			return;
		
		stopWatch.stop();
		state = TrainingState.PAUSED;
	}
	
	/**
	 * Setzt ein pausiertes {@link Training} fort.
	 */
	public void resume() {
		if (state != TrainingState.PAUSED)
			return;
		
		state = TrainingState.RUNNING;
		stopWatch.start();
	}

	/**
	 * Registriert einen listener, der daraufhin auf
	 * {@link SessionDetail}s hören kann.
	 * @param listener Der {@link SessionDetailListener}.
	 */
	public void registerListener(SessionDetailListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");
		
		if (!sessionDetailListener.contains(listener))
			sessionDetailListener.add(listener);
	}

	/**
	 * Entfernt einen registrierten listener wieder.
	 * @param listener Der registrierte {@link SessionDetailListener}.
	 */
	public void unregisterListener(SessionDetailListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");

		if (sessionDetailListener.contains(listener))
			sessionDetailListener.remove(listener);
	}

	private void initializeDataListener() {
		dataListener = new TrackedDataListener() {
			@Override
			public void trackData(TrackedDataItem dataItem) {
				assert dataItem != null : "dateItem cannot be null";
				if (state == TrainingState.RUNNING)
					processTrackedData(dataItem);
			}
		};
	}

	private void processTrackedData(TrackedDataItem dataItem) {		
		final int trainingSessionId = currentSession.getSessionId();
		
		SessionDetail detail = dataItemConverter.toSessionDetail(dataItem);
		detail.setTrainingSessionId(trainingSessionId);
				
		notifyTrainingSessionListeners(detail);
		database.storeSessionDetail(detail);
		
		int distanceInMeters = currentSession.getDistanceInMeters() + detail.getDistanceInMeter();
		currentSession.setDistanceInMeters(distanceInMeters);
	}

	private void notifyTrainingSessionListeners(SessionDetail sessionDetail) {
		for (SessionDetailListener listener : sessionDetailListener)
			listener.listen(sessionDetail);
	}
}
