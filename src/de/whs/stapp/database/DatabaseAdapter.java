package de.whs.stapp.database;

import java.util.List;

import de.whs.stapp.data.bluetooth.TrackedDataItem;

/**
 * Definiert Methoden für den Datenbankzugriff.
 * @author Chris
 */
public interface DatabaseAdapter {
	
    /**
     * Gibt alle Daten einer Trainingseinheit zurück. Dies enthält
     * alle vorhandenen Messwerte zu einer Einheit.
     * 
     * @param id , ID der detailliert anzuzeigenden Trainingseinheit.
     * @return
     */
    TrainingUnitDetail getTrainingUnitDetail(int id);
    
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
     * @param dataItem Die aktuellen Messwerte, die in der Datenbank gespeichert werden sollen. 
     * @return
     */
    boolean saveTrackedDataItem (int trainingUnitId, TrackedDataItem dataItem);
    
    /**
     * Die Methode legt einen neuen Eintrag für eine Trainingseinheit
     * in der Datenbank (in der Tabelle "TrainingsUnit") an.
     * 
     * @return
     */
    TrainingUnit createNewTrainingUnit();
}
