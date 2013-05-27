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
	 * @param amountOfDetails Anzahl der Daten (Detailgrad).
	 * @return Gibt Herzrate pro Zeit zurück.
	 */
	public ChartData getHeartratePerTime (int amountOfDetails) {
		ChartData result = new ChartData(ValueType.TIME, ValueType.HEARTRATE);
		
		List<Coordinate> coordinates = getFilteredHeartratePerTime();
		ChartDataAggregator.aggregate(result, coordinates, amountOfDetails);
		
		return result;
	}
	
	/**
	 * @param amountOfDetails Anzahl der Daten (Detailgrad).
	 * @return Gibt die Geschwindigkeit pro Zeit zurück.
	 */
	public ChartData getSpeedPerTime (int amountOfDetails) {
		ChartData result = new ChartData(ValueType.TIME, ValueType.SPEED);
		
		List<Coordinate> coordinates = getFilteredSpeedPerTime();
		ChartDataAggregator.aggregate(result, coordinates, amountOfDetails);
		
		return result;
	}

	private float getFirstTimeFromDetails() {
		return  details.size() > 0 ? details.get(0).getTimestamp().getTime() : 0;
	}

	
	private List<Coordinate> getFilteredHeartratePerTime() {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		float firstTime = getFirstTimeFromDetails();
		
		for (SessionDetail detail: details) {
			float heartrate = detail.getHeartRateInBpm();
			float time = (detail.getTimestamp().getTime() - firstTime) / 1000;
			if (isHeartrateValid(heartrate)/* && zweiter Parameter valid*/)
				coordinates.add(new Coordinate(time, heartrate));
		}
		
		return coordinates;
	}
	
	private static boolean isHeartrateValid(float heartrate) {
		return heartrate >= 40 && heartrate <= 220;
	}
	
	private List<Coordinate> getFilteredSpeedPerTime() {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		float firstTime = getFirstTimeFromDetails();
		
		for (SessionDetail detail: details) {
			float speed = detail.getSpeedInMeterPerSecond();
			float time = (detail.getTimestamp().getTime() - firstTime) / 1000;
			if (isSpeedValid(speed))
				coordinates.add(new Coordinate(time, speed));
		}
		
		return coordinates; 
	}
	
	private static boolean isSpeedValid(float speed) {
		return speed > 0 && speed < 16;
	}
}
