package de.whs.stapp.data.access;

import java.util.EventListener;
import de.whs.stapp.data.storage.TrainingDetail;

/**
 * Horcht auf {@link TrainingDetail}s während einer Trainingseinheit.
 * @author Chris
 */
public interface TrainingDetailListener extends EventListener {

	/**
	 * Liefert das gerade aktuelle {@link TrainingDetail} der laufenden {@link TrainingUnit}.
	 * @param detail Das {@link TrainingDetail}.
	 */
	void trackTrainingDetail(TrainingDetail detail);
}
