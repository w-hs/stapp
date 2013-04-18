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
	
	DatabaseAdapter database;
	
	/**
	 * Erzeugt eine neue Instanz der {@link StappDataAccess} Klasse.
	 * @param database Zugriff auf die Datenbank.
	 */
	public StappDataAccess(DatabaseAdapter database) {
		if (database == null)
			throw new IllegalArgumentException("database cannot be null");
		
		this.database = database;
	}
	
	@Override
	public TrainingController newTraining(DataTracker tracker) {		
		if (tracker == null)
			throw new IllegalArgumentException("tracker cannot be null");
	
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