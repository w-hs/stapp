package de.whs.stapp.data.access;

import de.whs.stapp.data.storage.DatabaseAdapter;
import de.whs.stapp.data.storage.DatabaseAdapterFactory;
import android.content.Context;

/**
 * Klasse zum Erzeugen von StorageAccess.
 * 
 * @author Thomas
 * 
 */
//CHECKSTYLE:OFF
public class StorageAccessFactory {
//CHECKSTYLE:ON

	private StorageAccessFactory() {
	}

	/**
	 * Gibt einen neuen StorageAccess zurück, welcher zum Arbeiten auf der
	 * Datenbank genutzt werden kann.
	 * 
	 * @param context
	 *            Der Kontext für die Initialisierung der Datenbank.
	 * @return Gibt den StorageAccess zurück.
	 */
	public static StorageAccess newStorageAccess(Context context) {

		if (context == null)
			throw new IllegalArgumentException("context cannot be null!");

		DatabaseAdapter db = DatabaseAdapterFactory.newDatabaseAdapter(context);
		return new StappStorageAccess(db);
	}
}
