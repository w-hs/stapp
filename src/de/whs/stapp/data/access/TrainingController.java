package de.whs.stapp.data.access;

import java.util.ArrayList;
import java.util.List;

import de.whs.stapp.data.bluetooth.DataTracker;
import de.whs.stapp.data.bluetooth.TrackedDataItem;
import de.whs.stapp.data.bluetooth.TrackedDataListener;
import de.whs.stapp.data.storage.DatabaseAdapter;
import de.whs.stapp.data.storage.TrainingUnit;

/**
 * Verwaltet eine Trainingseinheit.
 * @author Chris
 */
public class TrainingController {

	private List<TrackedDataListener> trackedDataListeners =
			new ArrayList<TrackedDataListener>();

	private DataTracker serviceConnection;
	private DatabaseAdapter databaseAdapter;
	
	private TrainingState state;
	private TrainingUnit trainingUnit;

	/**
	 * @return Die aktuelle {@link TrainingUnit}.
	 */
	public TrainingUnit getTrainingUnit() {
		return trainingUnit;
	}

	/**
	 * Erzeugt eine neue Instanz der {@link TrainingController} Klasse.
	 * @param databaseAdapter Eine {@link DatabaseAdapter} Implementierung.
	 * @param serviceConnection Eine {@link DataTracker} Implementierung.
	 */
	public TrainingController(DatabaseAdapter databaseAdapter,
			DataTracker serviceConnection) {
		
		if (databaseAdapter == null)
			throw new IllegalArgumentException("databaseAdapter cannot be null.");
		if (serviceConnection == null)
			throw new IllegalArgumentException("serviceConnection cannot be null.");

		this.databaseAdapter = databaseAdapter;
		this.serviceConnection = serviceConnection;

		state = TrainingState.NEW;
		trainingUnit = new TrainingUnit();
	}

	/**
	 * Liefert den aktuellen Status der Trainingseinheit.
	 * @return Der aktuelle Trainingsstatus.
	 */
	public TrainingState getState() {
		return state;
	}

	/**
	 * Startet die aktuelle Trainingseinheit.
	 */
	public void start() {
		if (state != TrainingState.NEW)
			return;

		state = TrainingState.RUNNING;
		trainingUnit = databaseAdapter.createNewTrainingUnit();

		listenToTrackedData();
	}

	/**
	 * Beendet die aktuelle Trainingseinheit.
	 */
	public void stop() {
		if (state == TrainingState.FINISHED)
			return;
		state = TrainingState.FINISHED;
		// TODO unregisterListener!
	}

	/**
	 * Registriert einen listener, am "TrackedDataEvent", dass gefeuert wird,
	 * wenn die aktuelle Trainingseinheit aktiv ist und Daten empfangen bzw.
	 * protokolliert werden.
	 * @param listener Der {@link TrackedDataListener}.
	 */
	public void registerTrackedDataListener(TrackedDataListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");

		if (!trackedDataListeners.contains(listener))
			trackedDataListeners.add(listener);
	}

	/**
	 * Entfernt einen registrierten listener vom "TrackedDataEvent".
	 * @param listener Der {@link TrackedDataListener}.
	 */
	public void unregisterTrackedDataListener(TrackedDataListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener cannot be null");

		if (trackedDataListeners.contains(listener))
			trackedDataListeners.remove(listener);
	}
	

	private void listenToTrackedData() {
		serviceConnection.registerListener(new TrackedDataListener() {
			@Override
			public void trackData(TrackedDataItem dataItem) {
				processTrackedData(dataItem);	
			}
		});
	}

	private void processTrackedData(TrackedDataItem dataItem) {
		if (state != TrainingState.RUNNING)
			return;
		
		notifyTrackedDataListerners(dataItem);
		databaseAdapter.saveTrackedDataItem(trainingUnit.getSessionID(), dataItem);
	}

	private void notifyTrackedDataListerners(TrackedDataItem dataItem) {
		for (TrackedDataListener listener : trackedDataListeners)
			listener.trackData(dataItem);
	}

	// /**
	// * @return Liefert den insgesamt gespeicherten Datensatz.
	// */
	// public TrainingUnitDetail getStoredData() {
	// // TODO, Christoph, Chris: Prüfen ob der Datensatz mit der Id noch
	// existiert,
	// // da er ja über removeStoredData bereits gelöscht worden sein könnte...
	// return
	// databaseAdapter.getTrainingUnitDetail(trainingUnit.getSessionID());
	// }
	//
	// /**
	// * Verwerfe diese Trainingseinheit wieder.
	// */
	// public void removeStoredData() {
	// // TODO Christoph, Chris: Methode zum Löschen programmieren.
	// }

	// Dadurch das wir die ganze Zeit speichern wird save
	// eigentlich überflüssig.
	// public void save();

	// Forget wird nötig, damit die Einträge wieder aus der
	// Datenbank gelöscht werden, sofern man die Trainingseinheit
	// nicht speichern sondern wieder verwerfen möchte.
	// public void forget();

	// public void pause() {
	// throw new
	// UnsupportedOperationException("pause() is not implemented yet.");
	// }
	//
	// public void proceed() {
	// throw new
	// UnsupportedOperationException("proceed() is not implemented yet.");
	// }
}
