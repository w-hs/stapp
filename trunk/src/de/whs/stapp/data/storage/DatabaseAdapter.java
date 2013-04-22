package de.whs.stapp.data.storage;

import java.util.List;

/**
 * Definiert Methoden für den Datenbankzugriff.
 * @author Chris
 */
public interface DatabaseAdapter {
	
    /**
     * Gibt alle Daten einer Trainingseinheit zurück. Dies enthält
     * alle vorhandenen Messwerte zu einer Einheit.
     * 
     * @param trainingUnitId ID der detailliert anzuzeigenden Trainingseinheit.
     * @return
     */
    DetailedTrainingUnit getTrainingUnitDetail(int trainingUnitId);
    
    /**
     * Liefert eine Liste aller Trainingseinheiten zurück.
     * @return
     */
    List<TrainingUnit> getTrainingUnitsOverview();
        
    /**
     * Die Methode schreibt Werte zu einer Trainingseinheit in die
     * Datenbank.
     * 
     * @param trainingUnitId Id der aktuell laufenden Trainingseinheit.
     * @param detail Die aktuellen Messwerte, die in der Datenbank gespeichert werden sollen. 
     * @return
     */
    void insertTrainingDetail(int trainingUnitId, TrainingDetail detail);
    
    /**
     * Die Methode legt einen neuen Eintrag für eine Trainingseinheit
     * in der Datenbank (in der Tabelle "TrainingsUnit") an.
     * 
     * @return
     */
    TrainingUnit createNewTrainingUnit();
    
    /**
     * Die Methode entfernt alle Einträge zur übergebenen trainingsUnitId.
     * 
     * @param trainingsUnitId Die ID der zu löschenden Trainingseinheit
     */
    void removeTrainingUnit(int trainingsUnitId);
    
}
