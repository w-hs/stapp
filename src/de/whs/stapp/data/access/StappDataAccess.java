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
 * Implementierung für den Zugriff auf die protokollierten Daten.
 */
public class StappDataAccess implements DataAccess {
	
	DatabaseAdapter database;
	DataTracker serviceConnection;
	
	/**
	 * TODO.
	 * @param databaseAdapter .
	 * @param serviceConnection .
	 */
	public StappDataAccess(DatabaseAdapter databaseAdapter, 
						   DataTracker serviceConnection) {
		this.database = databaseAdapter;
		this.serviceConnection = serviceConnection;
	}
	
	@Override
	public TrainingController newTraining() {		
		return new TrainingController(database, serviceConnection);
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