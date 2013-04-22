package de.whs.stapp.data.storage;

import java.util.List;

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

class StappDbAdapter implements DatabaseAdapter{

    private static String dbName = "stappDatabase.db";
    private static int dbVersion = 1;
    private DatabaseConnector dbConn;
    private SQLiteDatabase stappDb; 
    /**
     * Der Konstruktor legt eine Instantz des DatabaseConnectors an. 
     * @param context Der Context in dem die Klasse instantiiert wird.
     */
    public StappDbAdapter(Context context){
    	dbConn = new DatabaseConnector(context, dbName, null, dbVersion);
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
	public TrainingUnit createNewTrainingUnit() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @author Marcus Büscher
	 * Die Methode legt einen neuen Eintrag für eine Trainingseinheit
	 * in der Datenbank (in der Tabelle "TrainingUnits") an.
	 * 
	 * @param trainingUnitId ist der PK zu der TrainingUnit
	 * @param unit ist das zu speichernde Tupel
	 */
	public void insertNewTrainingUnit(TrainingUnit unit) {
		ContentValues val = new ContentValues();
		// TODO date
		// val.put(dbConn.tuClmDate, unit.getDate());
		val.put(dbConn.tuClmDuration, unit.getDurationInMinutes());
		val.put(dbConn.tuClmDistance, unit.getDistanceInMeters());
		stappDb.insert(dbConn.tabTrainingUnits, null, val);
	}


	@Override
	/**
	 * @author Marcus Büscher
	 * Die Methode speichert zu einer angegebenen ID die TrainingDetails in die
	 * TrackedData-Relation.
	 * 
	 * @param trainingUnitId ist die ID zu den zu speichernden Details.
	 * @param detail sind die Messwerte, die es zu speichern gilt.
	 */
	public void insertTrainingDetail(int trainingUnitId, TrainingDetail detail) {
		ContentValues val = new ContentValues();
		val.put(dbConn.tdClmIdTrainingUnit, trainingUnitId);
		val.put(dbConn.tdClmHeartrate, detail.getHeartRate());
		val.put(dbConn.tuClmDistance, detail.getDistanceInMeter());
		val.put(dbConn.tdClmSpeed, detail.getSpeedInMeterPerSecond());
		val.put(dbConn.tdClmStrides, detail.getNumberOfStrides());
		stappDb.insert(dbConn.tabTrackedData, null, val);		
	}


	/**
	 * @author Marcus Büscher
	 * Die Methode entfernt zur ID den Eintrag aus der TrainingUnit-Relation, sowie alle
	 * Einträge aus der TrackedData-Relation.
	 * 
	 * @param trainingsUnitId ist die ID der zu löschenden Trainingseinheit.	
	 */
	@Override
	public void removeTrainingUnit(int trainingsUnitId) {
		
		stappDb.delete(dbConn.tabTrackedData, 
				dbConn.tdClmIdTrainingUnit +"=" +trainingsUnitId, null);
	
		stappDb.delete(dbConn.tabTrainingUnits, 
				dbConn.tuClmIdTrainingUnit + "=" +trainingsUnitId, null);		
		
	}


	/**
     * @author Christoph Inhestern
     * Gibt alle Daten einer Trainingseinheit zurück. Dies enthält
     * alle vorhandenen Messwerte zu einer Einheit.
     * 
     * @param id , ID der detailliert anzuzeigenden Trainingseinheit.
     * @return
     */
    public DetailedTrainingUnit getTrainingUnitDetail(int id) {
    	throw new UnsupportedOperationException("getTrainingUnitDetail not yet implemented");
    }
    
    /**
     * @author Christoph Inhestern
     * Liefert eine Liste aller Trainingseinheiten zurück.
     * @return
     */
    public List<TrainingUnit> getTrainingUnitsOverview() {
    	throw new UnsupportedOperationException("getTrainingUnitsOverview not yet implemented");
    }
}
