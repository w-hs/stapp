package de.whs.stapp.data.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Christoph, Marcus Büscher
 * 
 * Die Klasse stellt eine Verbindung zur Datenbank her.
 * Existiert die Datenbank nicht, so wird sie erstellt und die Methode onCreate() gerufen.
 * 
 */

class DatabaseConnector extends SQLiteOpenHelper{

	public static final String REL_TRAINING_SESSIONS = "TrainingSessions";
	public static final String TS_SESSION_ID = "_id";
	public static final String TS_DATE = "Date";
	public static final String TS_DURATION_IN_MINUTES = "DurationInMinutes";
	public static final String TS_DISTANCE_IN_METERS = "DistanceInMeters";
	
	public static final String REL_SESSION_DETAILS = "SessionDetails";
	public static final String SD_DETAILS_ID = "_id";
	public static final String SD_TIMESTAMP = "Timestampt";
	public static final String SD_TRAINING_SESSIONS_ID_AS_FK = "TrainingSessionIdAsFK";
	public static final String SD_HEARTRATE = "Heartrate";
	public static final String SD_DISTANCE_IN_METERS = "DistanceInMeters";
	public static final String SD_SPEED_IN_METERS_PER_SECOND = "SpeedInMetersPerSecond";
	public static final String SD_NUMBER_OF_STRIDES = "NumberOfStrides";
	
	
	public DatabaseConnector(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String createTrainingSessions = "CREATE TABLE " + REL_TRAINING_SESSIONS 
						+ " ( " +TS_SESSION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ TS_DATE + " LONG NOT NULL, " 
						+ TS_DISTANCE_IN_METERS + " INTEGER NOT NULL, "
						+ TS_DURATION_IN_MINUTES + " LONG NOT NULL)";
		
		String createSessionDetails = "CREATE TABLE " + REL_SESSION_DETAILS 
						+ " ( " +SD_DETAILS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ SD_TRAINING_SESSIONS_ID_AS_FK + " INTEGER NOT NULL, " 
						+ SD_TIMESTAMP + "LONG NOT NULL, "
						+ SD_HEARTRATE + " INTEGER NOT NULL, "
						+ SD_DISTANCE_IN_METERS + " INTEGER NOT NULL, "
						+ SD_SPEED_IN_METERS_PER_SECOND + " INTEGER NOT NULL, " 
						+ SD_NUMBER_OF_STRIDES + " INTEGER NOT NULL, "
						+ "FOREIGN KEY (" +SD_TRAINING_SESSIONS_ID_AS_FK +") " 
						+ "REFERENCES " + REL_TRAINING_SESSIONS 
							+ "(" +TS_SESSION_ID + "))";		
		
		db.execSQL(createTrainingSessions);
		db.execSQL(createSessionDetails);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Was soll beim erhöhen der DB Version passieren?
		
	}


}
