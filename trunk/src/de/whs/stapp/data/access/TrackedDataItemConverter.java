package de.whs.stapp.data.access;

import java.sql.Timestamp;
import java.util.Date;

import de.whs.stapp.data.bluetooth.TrackedDataItem;
import de.whs.stapp.data.storage.SessionDetail;

/**
 * Converter Klasse für {@link TrackedDataItem}s.
 * @author Chris
 */
public class TrackedDataItemConverter {

	private static final double MAX_DISTANCE_IN_16THS_METERS = 4095.0;
	private double lastReadDistance;
	
	private static final byte MAX_NUMBER_OF_STRIDES = (byte) 128;
	private byte lastReadNumberOfStrides;
	
	/**
	 * 
	 * @param dataItem Das {@link TrackedDataItem}.
	 * @return Eine neue {@link SessionDetail} Instanz bestehend aus dem dataItem.
	 */
	public SessionDetail toSessionDetail(final TrackedDataItem dataItem) {
		if (dataItem == null)
			throw new IllegalArgumentException("dataItem cannot be null!");
		
		return createSessionDetail(dataItem);
	}
	
	private SessionDetail createSessionDetail(final TrackedDataItem dataItem) {
		SessionDetail detail = new SessionDetail();
		
		final int distanceInMeter = getDistanceInMeter(dataItem.getDistanceInOne16thsMeter());
		detail.setDistanceInMeter(distanceInMeter);
		
		final int heartRateInBpm = dataItem.getHeartRateInBpm();
		detail.setHeartRateInBpm(heartRateInBpm);
		
		final int numberOfStrides = getNumberOfStrides(dataItem.getStrides());
		detail.setNumberOfStrides(numberOfStrides);
		
		final int speed = getSpeedInMetersPerSecond(dataItem.getSpeedInOne256thsMeterPerSecond());
		detail.setSpeedInMeterPerSecond(speed);
		
		final Date currentDate = new Date();
		detail.setTimestamp(new Timestamp(currentDate.getTime()));
		
		return detail;
	}
	
	private int getDistanceInMeter(final double distanceIn16thsMeter) {
		Double currentDistanceIn16thsMeter = distanceIn16thsMeter;
		if (distanceIn16thsMeter < lastReadDistance)
			currentDistanceIn16thsMeter += MAX_DISTANCE_IN_16THS_METERS - lastReadDistance;
	
		lastReadDistance = distanceIn16thsMeter;		
		return currentDistanceIn16thsMeter.intValue() * 16;
	}
	
	private int getNumberOfStrides(final byte numberOfStrides) {
		byte currentNumberOfStrides = numberOfStrides;
		if (numberOfStrides < lastReadNumberOfStrides)
			currentNumberOfStrides += MAX_NUMBER_OF_STRIDES - lastReadNumberOfStrides;
		
		lastReadNumberOfStrides = numberOfStrides;
		return currentNumberOfStrides;
	}
	
	private int getSpeedInMetersPerSecond(final double speedIn256thMetersPerSecond) {
		Double currentSpeedIn256thMetersPerSecond = speedIn256thMetersPerSecond;
		return currentSpeedIn256thMetersPerSecond.intValue() * 256;
	}
}