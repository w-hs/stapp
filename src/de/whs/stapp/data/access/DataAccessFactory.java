package de.whs.stapp.data.access;

import android.content.Context;
import de.whs.stapp.data.bluetooth.DataTracker;
import de.whs.stapp.data.storage.DatabaseAdapter;
import de.whs.stapp.data.storage.DatabaseAdapterFactory;

/**
 * Factory zum Erzeugen von {@link DataAccess} Instanzen.
 * @author Chris
 */
//CHECKSTYLE:OFF
public class DataAccessFactory {
//CHECKSTYLE:OFF
	
	private DataAccessFactory() {
		
	}
	
	/**
	 * Liefert eine neue Instanz des {@link DataAccess} Interface.
	 * @param tracker Eine gültige {@link DataTracker} Instanz.
	 * @param context Der Kontext, in dem die Instanz erzeugt wird.
	 */
	public static DataAccess newDataAccess(DataTracker tracker, Context context) {
		if (tracker == null) 
			throw new IllegalArgumentException("tracker cannot be null!");
		if (context == null)
			throw new IllegalArgumentException("context cannot be null!");
		
		DatabaseAdapter db = DatabaseAdapterFactory.newDatabaseAdapter(context);	
		return new StappDataAccess(db, tracker);
	}
}