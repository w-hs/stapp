package de.whs.stapp.dataAccess;

import java.util.List;

import de.whs.stapp.database.TrainingUnit;
import de.whs.stapp.database.TrainingUnitDetail;

/**
 * @author Chris
 * Definiert den Zugriff auf Date.
 */
public interface DataAccess {
	
	/**
	 * Liefert einen {@link TrainingController} um eine konkrete
	 * Trainingseinheit zu verwalten.
	 */
	TrainingController newTraining();
	
	/**
	 * Liefert eine Liste der in der Datenbank 
	 * gespeicherten {@link TrainingUnit}s.
	 */
	List<TrainingUnit> getTrainingUnitsOverview();

	/**
	 * Liefert zu gegebener trainingUnitId die entsprechende {@link TrainingUnitDetail}. 
	 * @param trainingUnitId Die Id der {@link TrainingUnit}.
	 */
	TrainingUnitDetail getTrainingUnitWithDetails(int trainingUnitId);
}
