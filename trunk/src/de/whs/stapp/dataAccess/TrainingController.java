package de.whs.stapp.dataAccess;

import de.whs.stapp.database.DatabaseAdapter;
import de.whs.stapp.database.TrainingUnit;
import de.whs.stapp.liveDataTracking.BTServiceConnectionRegisterable;
import de.whs.stapp.liveDataTracking.TrackedDataEvent;
import de.whs.stapp.liveDataTracking.TrackedDataItem;
import de.whs.stapp.liveDataTracking.TrackedDataListener;

/**
 * Verwaltet eine Trainingseinheit.
 * @author Chris
 */
public class TrainingController {

	private TrainingState state;
	private TrainingUnit trainingUnit;
	
	// Für den Zugriff auf die Bluetooth "Tracking" Events...
	// Ggf. eine bessere Implementierung möglich?!
	private BTServiceConnectionRegisterable serviceConnection;
	private DatabaseAdapter databaseAdapter;
			
	
	/**
	 * @return Die aktuelle {@link TrainingUnit}.
	 */
	public TrainingUnit getTrainingUnit() { return trainingUnit; }
	
	
	/**
	 * Erzeugt eine neue Instanz der {@link TrainingController} Klasse.
	 * @param databaseAdapter Eine {@link DatabaseAdapter} Implementierung.
	 * @param serviceConnection Eine {@link BTServiceConnectionRegisterable} Implementierung.
	 */
	public TrainingController(DatabaseAdapter databaseAdapter, 
							  BTServiceConnectionRegisterable serviceConnection) {
		
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
		if (state != TrainingState.NEW) return;
		
		state = TrainingState.RUNNING;
		trainingUnit = databaseAdapter.createNewTrainingUnit();
		
		registerTrackedDataListener();
	}
	
	/**
	 * Beendet die aktuelle Trainingseinheit.
	 */
	public void stop() {
		if (state == TrainingState.FINISHED) return;
		state = TrainingState.FINISHED;
	}
	
//	/**
//	 * @return Liefert den insgesamt gespeicherten Datensatz.
//	 */
//	public TrainingUnitDetail getStoredData() {
//		// TODO, Christoph, Chris: Prüfen ob der Datensatz mit der Id noch existiert,
//		// da er ja über removeStoredData bereits gelöscht worden sein könnte...
//		return databaseAdapter.getTrainingUnitDetail(trainingUnit.getSessionID());		
//	}
//	
//	/**
//	 * Verwerfe diese Trainingseinheit wieder.
//	 */
//	public void removeStoredData() {
//		// TODO Christoph, Chris: Methode zum Löschen programmieren.
//	}
	

	private void registerTrackedDataListener() {
		serviceConnection.registerListener(new TrackedDataListener() {
			@Override
			public void getTrackedData(TrackedDataEvent e) {
				TrackedDataItem item = e.getDataItem();	
				
				if (state != TrainingState.RUNNING)
					return;
				
//				notifyOthers(this, e);
				databaseAdapter.saveTrackedDataItem(trainingUnit.getSessionID(), item);
			}
		});
	}
	
	// Dadurch das wir die ganze Zeit speichern wird save 
	// eigentlich überflüssig.
	// public void save();
	
	// Forget wird nötig, damit die Einträge wieder aus der
	// Datenbank gelöscht werden, sofern man die Trainingseinheit
	// nicht speichern sondern wieder verwerfen möchte.
	// public void forget();
	
	//public void pause() {
	//	throw new UnsupportedOperationException("pause() is not implemented yet.");
	//}
	//
	//public void proceed() {
	//	throw new UnsupportedOperationException("proceed() is not implemented yet.");
	//}
}
