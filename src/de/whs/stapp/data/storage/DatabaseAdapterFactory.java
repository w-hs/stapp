package de.whs.stapp.data.storage;

import android.content.Context;

/**
 * Factory zum Erzeugen einer neuen {@link DatabaseAdapter} Instanz.
 * @author Chris
 */
//CHECKSTYLE:OFF
public class DatabaseAdapterFactory {
//CHECKSTYLE:ON
	
	private DatabaseAdapterFactory() {
		
	}
	
	/**
	 * Erzeugt eine neue Instanz des {@link DatabaseAdapter} Interface.
	 * @param context Der Kontext, indem die Instanz erzeugt wird.
	 */
	public static DatabaseAdapter newDatabaseAdapter(Context context) {
		if (context == null)
			throw new IllegalArgumentException("context cannot be null!");
		
		return new StappDbAdapter(context);
	}
}
