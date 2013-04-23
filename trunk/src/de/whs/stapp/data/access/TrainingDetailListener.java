package de.whs.stapp.data.access;

import java.util.EventListener;
import de.whs.stapp.data.storage.SessionDetail;

/**
 * Horcht auf {@link SessionDetail}s während einer Trainingseinheit.
 * @author Chris
 */
public interface TrainingDetailListener extends EventListener {

	/**
	 * Liefert das gerade aktuelle {@link SessionDetail} der laufenden {@link TrainingSession}.
	 * @param detail Das {@link SessionDetail}.
	 */
	void trackTrainingDetail(SessionDetail detail);
}
