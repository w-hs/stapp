package de.whs.stapp.data.access;

import java.util.List;

import de.whs.stapp.data.storage.DetailedTrainingUnit;
import de.whs.stapp.data.storage.TrainingUnit;

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
	 * Liefert zu gegebener trainingUnitId die entsprechende {@link DetailedTrainingUnit}. 
	 * @param trainingUnitId Die Id der {@link TrainingUnit}.
	 */
	DetailedTrainingUnit getTrainingUnitWithDetails(int trainingUnitId);
}
