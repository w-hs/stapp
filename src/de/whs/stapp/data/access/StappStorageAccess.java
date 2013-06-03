package de.whs.stapp.data.access;

import java.util.List;

import de.whs.stapp.data.storage.DatabaseAdapter;
import de.whs.stapp.data.storage.SessionDetail;
import de.whs.stapp.data.storage.TrainingSession;

/**
 * Implementiert den StorageAccess und bietet damit die Funktionen zum Auslesen
 * der Daten aus der Datenbank an.
 * 
 * @author Thomas
 * 
 */
public class StappStorageAccess implements StorageAccess {

	private DatabaseAdapter database;

	/**
	 * Standardkonstruktor des StorageAccess.
	 * 
	 * @param database
	 *            Erwartet den DatabaseAdapter der zur Verbindung genutzt wird.
	 */
	public StappStorageAccess(DatabaseAdapter database) {
		if (database == null)
			throw new IllegalArgumentException("database cannot be null");

		this.database = database;

		this.database.openDatabase();
	}

	@Override
	public List<TrainingSession> getSessionHistory() {
		return database.getSessionHistory();
	}

	@Override
	public List<SessionDetail> getSessionDetails(int trainingSessionId) {
		return database.getSessionDetails(trainingSessionId);
	}

	@Override
	public void deleteTrainingSession(int trainingSessionId) {
		database.deleteTrainingSession(trainingSessionId);
	}

	@Override
	public void deleteAllTrainingSessions() {
		database.deleteAllTrainingSessions();
	}
}
