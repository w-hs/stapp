package de.whs.stapp.data.access;

import java.util.List;

import de.whs.stapp.data.bluetooth.DataTracker;
import de.whs.stapp.data.storage.DatabaseAdapter;
import de.whs.stapp.data.storage.SessionDetail;
import de.whs.stapp.data.storage.TrainingSession;

/**
 * @author Chris
 * Implementierung für den Zugriff auf die Daten und Funktionen von Trainingseinheiten.
 */
class StappDataAccess implements DataAccess {
	
	DatabaseAdapter database;
	DataTracker tracker;
	
	/**
	 * Erzeugt eine neue Instanz der {@link StappDataAccess} Klasse.
	 * @param database Zugriff auf die Datenbank.
	 * @param tracker Eine gültige {@link DataTracker} Instanz.
	 */
	public StappDataAccess(DatabaseAdapter database, DataTracker tracker) {
		if (database == null)
			throw new IllegalArgumentException("database cannot be null");
		if (tracker == null)
			throw new IllegalArgumentException("tracker cannot be null");
	
		this.tracker = tracker;
		this.database = database;
		
		this.database.openDatabase();
	}
	
	@Override
	public Training newTraining() {		
		return new Training(tracker, database);
	}

	@Override
	public List<TrainingSession> getSessionHistory() {
		return database.getSessionHistory();
	}

	@Override
	public List<SessionDetail> getSessionDetails(int trainingSessionId) {
		return database.getSessionDetails(trainingSessionId);
	}
}