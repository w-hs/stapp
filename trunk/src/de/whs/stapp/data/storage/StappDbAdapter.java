package de.whs.stapp.data.storage;

//CHECKSTYLE:OFF
import static de.whs.stapp.data.storage.DatabaseConnector.*;
//CHECKSTYE:ON
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Christoph Inhestern und Marcus Büscher
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
    public void openDatabase() {
    	stappDb = dbConn.getWritableDatabase();
    }
    
    
    /**
    * @author Christoph Inhestern
    * Schließt die Verbindung zur Datenbank nach der Benutzung.
    */
	public void closeDatabase() {
    	stappDb.close();
    }
    
    
	/**
	 * @author Christoph Inhestern
	 * Erstellt eine neue {@link TrainingSession} in der Datenbank.
	 * 
	 * @return TrainingsSession-Instanz mit Werten des in der DB neu angelegten Tupels. 
	 */
    @Override
	public TrainingSession newTrainingSession() {	
    	TrainingSession resultSession;
    	ContentValues val = new ContentValues();
    	long date = System.currentTimeMillis();
    	
    	val.put(TS_DISTANCE_IN_METERS, 0);
    	val.put(TS_DURATION_IN_MS, 0);
    	val.put(TS_DATE, date);
    	
    	if (stappDb.insert(REL_TRAINING_SESSIONS, null, val) != -1){
    		String sql = "SELECT  " +
    				TS_SESSION_ID + ", " +
    				TS_DATE + ", " +
    				TS_DISTANCE_IN_METERS + ", " +
    				TS_DURATION_IN_MS +    				
    				" FROM " + REL_TRAINING_SESSIONS + 
    				" WHERE " +
    				TS_DATE + " = " + date;
    		
    		Cursor cr = stappDb.rawQuery(sql, null);
    		if (cr.moveToFirst()){
    			//CHECKSTYLE:OFF
    			resultSession = 
    					new TrainingSession
    					(cr.getInt(0), new Date(cr.getLong(1)), cr.getInt(2), cr.getLong(3));
    			//CHECKSTYLE:ON
    			return resultSession;
    		}
    		
    	}
    	
    	return null;
	}


	/**
	 * @author Marcus Büscher
	 * Die Methode updatet ein vorhandenens Tupel in der Relation TrainingSessions. 
	 * 
	 * @param session ist die zu updatende TrainingSession
	 * @param trainingSessionID ist der PK des Tupels der Session
	 */
	public void updateTrainingSession(TrainingSession session) {
		ContentValues val = new ContentValues();
		val.put(TS_DURATION_IN_MS, session.getDurationInMs());
		val.put(TS_DISTANCE_IN_METERS, session.getDistanceInMeters());
		
		stappDb.update(REL_TRAINING_SESSIONS, val, 
							TS_SESSION_ID + " = " + session.getSessionId(), null);
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
	public void storeSessionDetail(SessionDetail detail) {
		ContentValues val = new ContentValues();
		val.put(SD_TRAINING_SESSIONS_ID_AS_FK, detail.getTrainingSessionId());
		val.put(SD_TIMESTAMP, detail.getTimestamp().getTime());
		val.put(SD_HEARTRATE, detail.getHeartRateInBpm());
		val.put(SD_DISTANCE_IN_METERS, detail.getDistanceInMeter());
		val.put(SD_SPEED_IN_METERS_PER_SECOND, detail.getSpeedInMeterPerSecond());
		val.put(SD_NUMBER_OF_STRIDES, detail.getNumberOfStrides());
		
		stappDb.insert(REL_SESSION_DETAILS, null, val);		
	}


	/**
	 * @author Marcus Büscher
	 * Die Methode entfernt zur ID den Eintrag aus der TrainingSessions-Relation, sowie alle
	 * Einträge aus der SessionDetails-Relation.
	 * 
	 * @param trainingSessionId ist die ID der zu löschenden Trainingseinheit.	
	 */
	@Override
	public void deleteTrainingSession(int trainingSessionId) {
		
		stappDb.delete(REL_SESSION_DETAILS, 
				SD_TRAINING_SESSIONS_ID_AS_FK + " = " + trainingSessionId, null);
	
		stappDb.delete(REL_TRAINING_SESSIONS, 
				TS_SESSION_ID + " = " + trainingSessionId, null);		
		
	}
	
	/**
	 * @author Christoph Inhestern
	 * Die Methode löscht alle Datensätze aus den Tabellen TRAINING_SESSIONS und SESSION_DEATAIL
	 */
	public void deleteAllTrainingSessions(){
		
		stappDb.delete(REL_SESSION_DETAILS, null, null);
		stappDb.delete(REL_TRAINING_SESSIONS, null, null);
		
	}
	
    /**
     * @author Christoph Inhestern
     * Liefert eine Liste aller TrainingSessions zurück.
     * @return Liste aller Einträge aus der Relation TrainingSessions.
     */
    public List<TrainingSession> getSessionHistory() {
    	List<TrainingSession> resultSessions = new ArrayList<TrainingSession>();
    	Cursor cr;
		String sql = "SELECT " +
				TS_SESSION_ID + ", " +
				TS_DATE + ", " +
				TS_DISTANCE_IN_METERS + ", " +
				TS_DURATION_IN_MS +    				
				" FROM " + REL_TRAINING_SESSIONS;
		
		cr = stappDb.rawQuery(sql, null);
		
		while(cr.moveToNext()){
			TrainingSession tmpSession;
			//CHECKSTYLE:OFF
			tmpSession = new TrainingSession(
					cr.getInt(0), new Date(cr.getLong(1)), cr.getInt(2), cr.getLong(3));
			//CHECKSTYLE:ON
			resultSessions.add(tmpSession);
		}
		
		return resultSessions;
    }

    
    /**
     * @author Christoph Inhestern
     * Die Methode eine Liste mit allen SessionDetails zur angegeben traininingSessionId zurück.
     * @param trainingSessionId Die Id der entsprechenden {@link TrainingSession}.
     * @return Die {@link SessionDetail}s zu einer TrainingSession.
     */
	@Override
	public List<SessionDetail> getSessionDetails(int trainingSessionId) {
		List<SessionDetail> resultDetails = new ArrayList<SessionDetail>();
		Cursor cr;
		String sql = "SELECT " +
				SD_DETAILS_ID + ", " +
				SD_TRAINING_SESSIONS_ID_AS_FK + ", " +
				SD_TIMESTAMP + ", " +
				SD_HEARTRATE + ", " +
				SD_DISTANCE_IN_METERS + ", " +
				SD_SPEED_IN_METERS_PER_SECOND + ", " +
				SD_NUMBER_OF_STRIDES +
				" FROM " + REL_SESSION_DETAILS +
				" WHERE " + 
				SD_TRAINING_SESSIONS_ID_AS_FK +	" = " + trainingSessionId +
				" ORDER BY " + SD_DETAILS_ID + " ASC";
		
		cr = stappDb.rawQuery(sql, null);
		
		while (cr.moveToNext()) {
			SessionDetail tmpSession;
			// CHECKSTYLE:OFF
			tmpSession = new SessionDetail(cr.getInt(0), cr.getInt(1),
					new Timestamp(cr.getInt(2)), cr.getInt(3), cr.getInt(4),
					cr.getInt(5), cr.getInt(6));
			// CHECKSTYLE:ON
			resultDetails.add(tmpSession);
		}
		
		return resultDetails;
	}
}
