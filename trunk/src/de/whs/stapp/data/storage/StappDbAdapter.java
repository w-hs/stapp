package de.whs.stapp.data.storage;

import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import de.whs.stapp.data.bluetooth.TrackedDataItem;

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
    
    
    /**
     * @author Christoph Inhestern
     * Die Methode schreibt Werte zu einer Trainingseinheit in die
     * Datenbank.
     * 
     * @param trainingUnitId Id der aktuell laufenden Trainingseinheit.
     * @param dataItem Die aktuellen Messwerte, die in der Datenbank gespeichert werden sollen. 
     * @return
     */
    public boolean saveTrackedDataItem (int trainingUnitId, TrackedDataItem dataItem) {   	
    	ContentValues val = new ContentValues();
    	val.put("ID", trainingUnitId);
    	
    	// TODO

    	if (stappDb.insert("TABBELLE MIT WERTEN", null, val) == -1)   	
    		return false;
    	
    	return true;
    }
    
    /**
     * @author Christoph Inhestern
     * Die Methode legt einen neuen Eintrag für eine Trainingseinheit
     * in der Datenbank (in der Tabelle "TrainingsUnit") an.
     * 
     * @return
     */
    public TrainingUnit createNewTrainingUnit() {
    	TrainingUnit result = new TrainingUnit();
    	
    	//TODO
    	
    	return result;    	
    }

	@Override
	public void saveTrainingDetail(int trainingUnitId, TrainingDetail detail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTrainingUnit(int trainingsUnitId) {
		// TODO Auto-generated method stub
		
	}
}
