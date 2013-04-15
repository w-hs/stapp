package de.whs.stapp.dataAccess;

import de.whs.stapp.database.DatabaseAdapter;
import de.whs.stapp.liveDataTracking.BTServiceConnectionRegisterable;
import de.whs.stapp.liveDataTracking.TrackedDataEvent;
import de.whs.stapp.liveDataTracking.TrackedDataItem;
import de.whs.stapp.liveDataTracking.TrackedDataListener;

// TrainingController hat zwei Abhängigkeiten:
// - "Irgendwas" an dem ich mich registrieren kann
// - Zugriff auf Datenbank...


/*
 * 		TrackedDataListener trackedDataListener = new TrackedDataListener() {
			@Override
			public void getTrackedData(TrackedDataEvent e) {
				//TrackedDataItem item = e.getDataItem();	
				
				if (state != TrainingState.RUNNING)
					return;
				
//				notifyOthers(this, e);
//				persistDataToChristoph(unitDetails.id, item);	
			}
		};
		
 */


/**
 * Verwaltet eine Trainingseinheit.
 * @author Chris
 */
public class TrainingController {

	private TrainingState state;
	
	// Für den Zugriff auf die Bluetooth "Tracking" Events...
	// Ggf. eine bessere Implementierung möglich?!
	private BTServiceConnectionRegisterable serviceConnection;
	private DatabaseAdapter databaseAdapter;
		
	
	/**
	 *  Erzeugt eine neue Instanz der {@link TrainingController} Klasse.
	 * @param databaseAdapter Eine {@link DatabaseAdapter} Implementierung.
	 * @param serviceConnection Eine {@link BTServiceConnectionRegisterable} Implementierung.
	 */
	public TrainingController(DatabaseAdapter databaseAdapter, 
			BTServiceConnectionRegisterable serviceConnection) {
		
		this.databaseAdapter = databaseAdapter;
		this.serviceConnection = serviceConnection;		
		state = TrainingState.NEW;
		
		registerTrackedDataListener();
	}
	
	private void registerTrackedDataListener() {
		serviceConnection.registerListener(new TrackedDataListener() {
			@Override
			public void getTrackedData(TrackedDataEvent e) {
				TrackedDataItem item = e.getDataItem();	
				
				if (state != TrainingState.RUNNING)
					return;
				
//				notifyOthers(this, e);
//				databaseAdapter.saveTrackedDataItem(trainingUnitId, item);
			}
		});
		
	}

	/**
	 * Liefert den aktuellen Trainingsstatus zurück.
	 * @return Der aktuelle Trainingsstatus.
	 */
	public TrainingState getState() {
		return state;
	}
	
	/**
	 * .
	 */
	public void start() {
		// Sollte die Trainingseinheit mit Details zurück geben?!
		throw new UnsupportedOperationException("start() is not implemented yet.");
	}
	
//	/**
//	 * .
//	 */
//	public void pause() {
//		throw new UnsupportedOperationException("pause() is not implemented yet.");
//	}
//	
//	/**
//	 * .
//	 */
//	public void proceed() {
//		throw new UnsupportedOperationException("proceed() is not implemented yet.");
//	}
	
	/**
	 * .
	 */
	public void stop() {
		throw new UnsupportedOperationException("stop() is not implemented yet.");
	}
	
	// Dadurch das wir die ganze Zeit speichern wird save 
	// eigentlich überflüssig.
	// public void save();
	
	// Forget wird nötig, damit die Einträge wieder aus der
	// Datenbank gelöscht werden, sofern man die Trainingseinheit
	// nicht speichern sondern wieder verwerfen möchte.
	// public void forget();
}
