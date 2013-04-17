/**
 * 
 */
package de.whs.stapp.data.access;

import java.util.List;

import de.whs.stapp.data.bluetooth.DataTracker;
import de.whs.stapp.database.DatabaseAdapter;
import de.whs.stapp.database.TrainingUnit;
import de.whs.stapp.database.TrainingUnitDetail;

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
	public TrainingUnitDetail getTrainingUnitWithDetails(int trainingUnitId) {
		return database.getTrainingUnitDetail(trainingUnitId);
	}
}