package de.whs.stapp.data.access;

import java.util.List;

import de.whs.stapp.data.storage.SessionDetail;
import de.whs.stapp.data.storage.TrainingSession;

/**
 * Definiert den Zugriff auf den Storage.
 * @author Thomas
 *
 */
public interface StorageAccess {
	/**
	 * @return Die in der Datenbank gespeicherten {@link TrainingSession}s.
	 */
	List<TrainingSession> getSessionHistory();

	/**
	 * @param trainingSessionId Die Id der entsprechenden {@link TrainingSession}.
	 * @return Eine Liste der {@link SessionDetail}s zu einer gegebenen trainingSessionId.
	 */
	List<SessionDetail> getSessionDetails(int trainingSessionId);
}
