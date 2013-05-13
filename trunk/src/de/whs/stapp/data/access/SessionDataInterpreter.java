package de.whs.stapp.data.access;

import java.util.Collections;
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
		
		for (SessionDetail detail: details) {
			float heartrate = detail.getHeartRateInBpm();
			float time = detail.getTimestamp().getNanos();
			if (isHeartrateValid(heartrate)/* && zweiter Parameter valid*/)
				result.getCoordinates().put(time, heartrate);
		}
		
		result.setCoordinates(ChartDataAggregator.aggregate(result.getCoordinates(), amountOfDetails));
		setMinMaxValues(result);
		return result;
	}
	
	private static boolean isHeartrateValid(float heartrate) {
		return heartrate >= 40 && heartrate <= 220;
	}
	
	private static void setMinMaxValues(ChartData data) {
		data.setMinValueX(Collections.min(data.getCoordinates().keySet()));
		data.setMaxValueX(Collections.max(data.getCoordinates().keySet()));
		
		data.setMinValueY(Collections.min(data.getCoordinates().values()));
		data.setMaxValueY(Collections.max(data.getCoordinates().values()));	
	}
}
