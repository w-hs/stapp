package de.whs.stapp.data.access;

import java.util.EventListener;

import de.whs.stapp.data.storage.SessionDetail;


/**
 * Lauscht auf {@link SessionDetail}s während einer {@link TrainingSession}.
 * @author Chris
 */
public interface SessionDetailListener extends EventListener {

	/**
	 * Liefert das gerade aktuelle {@link SessionDetail} der laufenden {@link TrainingSession}.
	 * @param detail Das {@link SessionDetail}.
	 */
	void listen(SessionDetail detail);
}
