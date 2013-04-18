package de.whs.stapp.data.access;

import java.util.ArrayList;
import java.util.List;

import de.whs.stapp.data.bluetooth.DataTracker;
import de.whs.stapp.data.bluetooth.TrackedDataItem;
import de.whs.stapp.data.bluetooth.TrackedDataListener;
import de.whs.stapp.data.storage.DatabaseAdapter;
import de.whs.stapp.data.storage.TrainingDetail;
import de.whs.stapp.data.storage.TrainingUnit;

/**
 * Verwaltet eine Trainingseinheit.
 * @author Chris
 */
public class TrainingController {
	
	private DataTracker tracker;
	private DatabaseAdapter database;	
	private TrackedDataListener dataListener;

	private TrainingState state = TrainingState.NEW;
	private TrainingUnit currentTraining = new TrainingUnit();
	
	private List<TrainingDetailListener> trainingDetailListeners =
			new ArrayList<TrainingDetailListener>();


	/**
	 * Erzeugt eine neue Instanz der {@link TrainingController} Klasse.
	 * @param tracker Eine {@link DataTracker} Implementierung.
	 * @param database Eine {@link DatabaseAdapter} Implementierung.
	 */
	public TrainingController(DataTracker tracker, DatabaseAdapter database) {		
		if (database == null)
			throw new IllegalArgumentException("databaseAdapter cannot be null");
		if (tracker == null)
			throw new IllegalArgumentException("serviceConnection cannot be null");

		this.database = database;
		this.tracker = tracker;
		
		initializeDataListener();
	}
	
	/**
	 * @return Die aktuelle {@link TrainingUnit}.
	 */
	public TrainingUnit getCurrentTrainingUnit() {
		return currentTraining;
	}

	/**
	 * @return Der Status der Trainingseinheit.
	 */
	public TrainingState getState() {
		return state;
	}

	/**
	 * Startet die Trainingseinheit.
	 */
	public void start() {
		if (state != TrainingState.NEW)
			return;

		state = TrainingState.RUNNING;
		currentTraining = database.createNewTrainingUnit();

		tracker.registerListener(dataListener);
	}

	/**
	 * Beendet die aktuelle Trainingseinheit.
	 */
	public void stop() {
		if (state == TrainingState.FINISHED) 
			return;
		
		state = TrainingState.FINISHED;
		tracker.unregisterListener(dataListener);
	}

	/**
	 * Registriert den listener, so dass dieser über eingehende 
	 * {@link TrainingDetail}s informiert wird.
	 * @param listener Der {@link TrainingDetailListener}.
	 */
	public void registerListener(TrainingDetailListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");

		if (!trainingDetailListeners.contains(listener))
			trainingDetailListeners.add(listener);
	}

	/**
	 * Entfernt einen registrierten listener wieder.
	 * @param listener Der registrierte {@link TrainingDetailListener}.
	 */
	public void unregisterTrackedDataListener(TrainingDetailListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");

		if (trainingDetailListeners.contains(listener))
			trainingDetailListeners.remove(listener);
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
		TrainingDetail detail = createTrainingDetail(dataItem);

		notifyTrainingDetailListeners(detail);
		database.saveTrainingDetail(currentTraining.getTrainingUnitId(), detail);
	}
	
	private TrainingDetail createTrainingDetail(TrackedDataItem dataItem) {
		TrainingDetail detail = new TrainingDetail();
		
		// TODO korrekt umrechnen!
		detail.setDistanceInMeter((int)dataItem.getDistanceInOne16thsMeter());
		detail.setHeartRate((int)dataItem.getHeartRateInBpm());
		detail.setNumberOfStrides((int)dataItem.getStrides());
		detail.setSpeedInMeterPerSecond((int)dataItem.getSpeedInOne256thsMeterPerSecond());
		
		return detail;
	}

	private void notifyTrainingDetailListeners(TrainingDetail detail) {
		for (TrainingDetailListener listener : trainingDetailListeners)
			listener.trackTrainingDetail(detail);
	}
}
