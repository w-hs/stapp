package de.whs.stapp.data.storage;

import java.util.List;

/**
 * Definiert Methoden für den Datenbankzugriff.
 * @author Chris
 */
public interface DatabaseAdapter {
	
    /**
     * @param trainingSessionId Die Id der entsprechenden {@link TrainingSession}.
     * @return Die {@link SessionDetail}s zu einer TrainingSession.
     */
    List<SessionDetail> getSessionDetails(int trainingSessionId);
    
    /**
     * @return Eine Liste aller bestehenden {@link TrainingSession}s.
     */
    List<TrainingSession> getSessionHistory();
        
    /**
     * Speichert ein {@link SessionDetail} zu einer gegebenen TrainingSession.
     * @param trainingSessionId Die Id der entsprechenden {@link TrainingSession}.
     * @param detail Das zu speichernde {@link SessionDetail}. 
     */
    void storeSessionDetail(int trainingSessionId, SessionDetail detail);
    
    /**
     * Erstellt eine neue {@link TrainingSession} in der Datenbank.
     * @return Die TrainingSession, die neu in der Datenbank erstellt wurde.
     */
    TrainingSession newTrainingSession();
    
    /**
     * Entfernt die entsprechende Trainings Session vollständig 
     * (inkl. SessionDetails) aus der Datenbank. 
     * @param trainingSessionId Id der zu löschenden {@link TrainingSession}.
     */
    void deleteTrainingSession(int trainingSessionId);
}