package de.whs.stapp.data.access;

import java.util.ArrayList;
import java.util.List;

import de.whs.stapp.data.storage.SessionDetail;

/**
 * 
 * Diese Klasse beinhaltet Konvertierungsmethoden zur visuellen 
 * Darstellung einer Session in der View.
 * 
 * @author Dennis
 *
 */
public class SessionDataInterpreter {
	private final List<SessionDetail> details;

	/**
	 * Erstellt eine neue Instanz der {@link SessionDataInterpreter} Klasse.
	 * @param details Eine {@link SessionDetail} Liste.
	 */
	public SessionDataInterpreter(List<SessionDetail> details) {
		this.details = details;
	}
	
	/** 
	 * @param amountOfDetails Anzahl der Daten (Detailgrad)
	 * @return Gibt Herzrate pro Zeit zurück
	 */
	public ChartData getHeartratePerTime (int amountOfDetails) {
		ChartData result = new ChartData(ValueType.TIME, ValueType.HEARTRATE);
		
		List<Coordinate> coordinates = getFilteredCoordinates();
		ChartDataAggregator.aggregate(result, coordinates, amountOfDetails);
		
		return result;
	}

	private List<Coordinate> getFilteredCoordinates() {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		
		for (SessionDetail detail: details) {
			float heartrate = detail.getHeartRateInBpm();
			float time = detail.getTimestamp().getNanos();
			if (isHeartrateValid(heartrate)/* && zweiter Parameter valid*/)
				coordinates.add(new Coordinate(time, heartrate));
		}
		
		return coordinates;
	}
	
	private static boolean isHeartrateValid(float heartrate) {
		return heartrate >= 40 && heartrate <= 220;
	}
}
