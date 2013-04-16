package de.whs.stapp.database;

import java.util.ArrayList;

import de.whs.stapp.liveDataTracking.TrackedDataItem;

/**
 * 
 * @author Christoph Inhestern
 * Enthält alle Daten einer Trainingseinheit, inklusive einer Liste
 * über alle Messwerten.
 */
public class TrainingUnitDetail extends TrainingUnit{
	
	protected ArrayList<TrackedDataItem> trackedDataCollection = new ArrayList<TrackedDataItem>();

	/**
	 * @return the trackedDataCollection
	 */
	public ArrayList<TrackedDataItem> getTrackedDataCollection() {
		return trackedDataCollection;
	}
}
