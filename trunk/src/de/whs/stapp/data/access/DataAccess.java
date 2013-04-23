package de.whs.stapp.data.access;

import java.util.List;

import de.whs.stapp.data.storage.SessionDetail;
import de.whs.stapp.data.storage.TrainingSession;

/**
 * @author Chris
 * Definiert den Zugriff auf Date.
 */
public interface DataAccess {
	
	/**
	 * @return Eine neue {@link Training} Instanz.
	 */
	Training newTraining();
	
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
