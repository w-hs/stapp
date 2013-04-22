package de.whs.stapp.data.access;

import java.util.List;

import de.whs.stapp.data.bluetooth.DataTracker;
import de.whs.stapp.data.storage.DatabaseAdapter;
import de.whs.stapp.data.storage.TrainingUnit;
import de.whs.stapp.data.storage.DetailedTrainingUnit;

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
	}
	
	@Override
	public TrainingController newTraining() {		
		return new TrainingController(tracker, database);
	}

	@Override
	public List<TrainingUnit> getTrainingUnitsOverview() {
		return database.getTrainingUnitsOverview();
	}

	@Override
	public DetailedTrainingUnit getTrainingUnitWithDetails(int trainingUnitId) {
		return database.getTrainingUnitDetail(trainingUnitId);
	}
}