package de.whs.stapp.data.storage;

import java.util.List;

/**
 * 
 * @author Christoph Inhestern
 * Enthält alle Daten einer Trainingseinheit, inklusive einer Liste
 * über alle Messwerten.
 */
public class DetailedTrainingUnit extends TrainingUnit {
	
	protected List<TrainingDetail> details;
	
	/**
	 * Erstellt eine neue Instanz der {@link DetailedTrainingUnit}.
	 * @param details Die Details der Trainingseinheit.
	 */
	public DetailedTrainingUnit(List<TrainingDetail> details) {
		if (details == null)
			throw new IllegalArgumentException("trainingDetails must be not null");
		this.details = details;
	}

	/**
	 * @return Gets the trainings details.
	 */
	public List<TrainingDetail> getDetails() {
		return details;
	}
}
