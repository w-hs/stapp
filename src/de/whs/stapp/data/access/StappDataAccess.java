/**
 * 
 */
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
public class StappDataAccess implements DataAccess {
	
	DataTracker tracker;
	DatabaseAdapter database;
	
	/**
	 * Erzeugt eine neue Instanz der {@link StappDataAccess} Klasse.
	 * @param tracker Eine gültige {@link DataTracker} Instanz.
	 * @param database Zugriff auf die Datenbank.
	 */
	public StappDataAccess(DataTracker tracker, DatabaseAdapter database) {
		if (tracker == null)
			throw new IllegalArgumentException("tracker cannot be null");
		if (database == null)
			throw new IllegalArgumentException("database cannot be null");
		
		this.database = database;
		this.tracker= tracker;
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