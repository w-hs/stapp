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

	private TrainingState state = TrainingState.NEW;
	private TrainingSession currentSession = new TrainingSession();
	
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
	}

	/**
	 * Beendet das {@link Training}.
	 */
	public void stop() {
		if (state == TrainingState.FINISHED) 
			return;
		
		state = TrainingState.FINISHED;
		tracker.unregisterListener(dataListener);
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
	public void unregisterTrackedDataListener(SessionDetailListener listener) {
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
		SessionDetail detail = createSessionDetail(dataItem);

		// TODO Werte in currentSession aufsummieren
		
		notifyTrainingSessionListeners(detail);
		database.storeSessionDetail(currentSession.getSessionId(), detail);
	}
	
	private SessionDetail createSessionDetail(TrackedDataItem dataItem) {
		SessionDetail detail = new SessionDetail();
		
		// TODO korrekt umrechnen!
		detail.setDistanceInMeter((int)dataItem.getDistanceInOne16thsMeter());
		detail.setHeartRate((int)dataItem.getHeartRateInBpm());
		detail.setNumberOfStrides((int)dataItem.getStrides());
		detail.setSpeedInMeterPerSecond((int)dataItem.getSpeedInOne256thsMeterPerSecond());
		
		return detail;
	}

	private void notifyTrainingSessionListeners(SessionDetail sessionDetail) {
		for (SessionDetailListener listener : sessionDetailListener)
			listener.listen(sessionDetail);
	}
}
