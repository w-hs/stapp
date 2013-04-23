package de.whs.stapp.data.storage;

import java.util.List;

import static de.whs.stapp.data.storage.DatabaseConnector.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Christoph Inhestern
 * 
 * Klasse für den Zugriff auf die Datenbank und die zugehörigen Funktionen
 * 
 */

class StappDbAdapter implements DatabaseAdapter {

    private static final String DBNAME = "stappDatabase.db";
    private static final int DBVERSION = 1;
    private DatabaseConnector dbConn;
    private SQLiteDatabase stappDb; 
    /**
     * Der Konstruktor legt eine Instantz des DatabaseConnectors an. 
     * @param context Der Context in dem die Klasse instantiiert wird.
     */
    public StappDbAdapter(Context context){
    	dbConn = new DatabaseConnector(context, DBNAME, null, DBVERSION);
    }

    
    /**
    * @author Christoph Inhestern
    * Öffnet die Datenbank und legt Sie an falls erforderlich. Dies geschieht in der Methode
    * getWritableDatabase().
    */
    public void open() {
    	stappDb = dbConn.getWritableDatabase();
    }
    
    
    /**
    * @author Christoph Inhestern
    * Schließt die Verbindung zur Datenbank nach der Benutzung.
    */
	public void close() {
    	stappDb.close();
    }
    
    
    @Override
	public TrainingSession newTrainingSession() {
    	
    	throw new UnsupportedOperationException("createNewTrainingUnit not yet implemented");
	}


	/**
	 * @author Marcus Büscher
	 * Die Methode updatet ein vorhandenens Tupels in der Relation
	 * TrainingSessions. 
	 * 
	 * @param session ist die zu updatende TrainingSession
	 * @param trainingSessionID ist der PK des Tupels der Session
	 */
	public void updateTrainingUnit(TrainingSession session, int trainingSessionID) {
		ContentValues val = new ContentValues();
		// TODO date
		// val.put(dbConn.tuClmDate, unit.getDate());
		val.put(TS_DURATION_IN_MINUTES, session.getDurationInMinutes());
		val.put(TS_DISTANCE_IN_METERS, session.getDistanceInMeters());
		stappDb.insert(REL_TRAINING_SESSIONS, null, val);
	}


	@Override
	/**
	 * @author Marcus Büscher
	 * Die Methode speichert zu einer angegebenen ID die SessionDetails in die
	 * SessionDetails-Relation.
	 * 
	 * @param trainingSessionID ist Foreign-Key, refernziert Tupel aus TrainingSessions.
	 * @param detail sind die Messwerte, die zu speichern sind.
	 */
	public void storeSessionDetail(int trainingSessionID, SessionDetail detail) {
		ContentValues val = new ContentValues();
		val.put(SD_TRAINING_SESSIONS_ID_AS_FK, trainingSessionID);
		// TODO timestamp
		val.put(SD_HEARTRATE, detail.getHeartRateInBpm());
		val.put(SD_DISTANCE_IN_METERS, detail.getDistanceInMeter());
		val.put(SD_SPEED_IN_METERS_PER_SECOND, detail.getSpeedInMeterPerSecond());
		val.put(SD_NUMBER_OF_STRIDES, detail.getNumberOfStrides());
		stappDb.insert(REL_SESSION_DETAILS, null, val);		
	}


	/**
	 * @author Marcus Büscher
	 * Die Methode entfernt zur ID den Eintrag aus der TrainingUnit-Relation, sowie alle
	 * Einträge aus der TrackedData-Relation.
	 * 
	 * @param trainingsUnitId ist die ID der zu löschenden Trainingseinheit.	
	 */
	@Override
	public void deleteTrainingSession(int trainingsUnitId) {
		
		stappDb.delete(REL_SESSION_DETAILS, 
				SD_TRAINING_SESSIONS_ID_AS_FK +"=" +trainingsUnitId, null);
	
		stappDb.delete(REL_TRAINING_SESSIONS, 
				TS_SESSION_ID + "=" +trainingsUnitId, null);		
		
	}
	
    /**
     * @author Christoph Inhestern
     * Liefert eine Liste aller Trainingseinheiten zurück.
     * @return
     */
    public List<TrainingSession> getSessionHistory() {
    	throw new UnsupportedOperationException("getTrainingUnitsOverview not yet implemented");
    }


	@Override
	public List<SessionDetail> getSessionDetails(int trainingSessionId) {
		// TODO Auto-generated method stub
		return null;
	}
}
