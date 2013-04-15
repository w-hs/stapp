package de.whs.stapp.database;

import java.util.ArrayList;

import de.whs.stapp.liveDataTracking.TrackedDataItem;

/**
 * 
 * @author Christoph Inhestern
 *Enthält alle Daten einer Trainingseinheit, inklusive einer Liste
 *über alle Messwerten.
 */
public class TrainingUnitDetail extends TrainingUnit{

	
	public ArrayList<TrackedDataItem> trackedDataCollection = new ArrayList<TrackedDataItem>();
}
