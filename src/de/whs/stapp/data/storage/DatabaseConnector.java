package de.whs.stapp.data.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Christoph
 * 
 * Die Klasse stellt eine Verbindung zur Datenbank her.
 * Existiert die Datenbank nicht, so wird sie erstellt und die Methode onCreate() gerufen.
 * 
 */

class DatabaseConnector extends SQLiteOpenHelper{

	//static, groß und mit _
	public static final String TAB_TRAINING_UNITS = "TrainingUnits";
	public final String tuClmIdTrainingUnit = "_id";
	public final String tuClmDate = "Date";
	public final String tuClmDuration = "Duration";
	public final String tuClmDistance = "Distance";
	
	public final String tabTrackedData = "TrackedData";
	public final String tdClmIdTrackedData = "_id";
	public final String tdClmIdTrainingUnit = "_idTrainingUnit";
	public final String tdClmHeartrate = "Heartrate";
	public final String tdClmDistance = "Distance";
	public final String tdClmSpeed = "Speed";
	public final String tdClmStrides = "Strides";
	
	
	public DatabaseConnector(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//Autoincrement
		String createTrainingUnits = "CREATE TABLE " + TAB_TRAINING_UNITS + " ( " +
										tuClmIdTrainingUnit 
											+" INTEGER PRIMARY KEY, "
										+tuClmDate + " LONG, " +
										tuClmDistance + " INTEGER, " +
										tuClmDuration + " LONG";
		
		String createTrackedData = "CREATE TABLE " + tabTrackedData + " ( " +
										tdClmIdTrackedData 
											+" INTEGER PRIMARY KEY, "
										+tdClmIdTrainingUnit + " INTEGER, " +
										tdClmHeartrate + " INTEGER, " +
										tdClmDistance + " INTEGER, " +
										tdClmSpeed + " INTEGER, " +
										tdClmStrides + " INTEGER, " +
										"FOREIGN KEY (" +tdClmIdTrainingUnit 
										 +") " +"REFERENCES " 
										 + TAB_TRAINING_UNITS + "(_id)";		
		
		db.execSQL(createTrainingUnits);
		db.execSQL(createTrackedData);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Was soll beim erhöhen der DB Version passieren?
		
	}


}
