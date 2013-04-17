package de.whs.stapp.data.storage;

import java.util.ArrayList;

/**
 * 
 * @author Christoph Inhestern
 * Enthält alle Daten einer Trainingseinheit, inklusive einer Liste
 * über alle Messwerten.
 */
public class DetailedTrainingUnit extends TrainingUnit {
	
	protected ArrayList<TrainingDetail> details = new ArrayList<TrainingDetail>();

	/**
	 * @return Gets the trainings details.
	 */
	public ArrayList<TrainingDetail> getDetails() {
		return details;
	}
}
