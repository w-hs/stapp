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

	private static final double MAX_DISTANCE_IN_METERS = 256.0;
	private double lastReadDistance;
	
	private static final byte MAX_NUMBER_OF_STRIDES = (byte) 128;
	private byte lastReadNumberOfStrides;
	private boolean hasDistanceOverflowed;
	private byte firstStrides;
	private double firstDistance;
	private boolean hasStridesOverflowed;
	private boolean isFirstPackage;
	
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
		
		if (isFirstPackage) {
			firstDistance = dataItem.getDistanceInMeter();
			firstStrides = dataItem.getStrides();
			isFirstPackage = false;
		}
		
		final int distanceInMeter = getDistanceInMeter(dataItem.getDistanceInMeter());
		detail.setDistanceInMeter(distanceInMeter);
		
		final int heartRateInBpm = dataItem.getHeartRateInBpm();
		detail.setHeartRateInBpm(heartRateInBpm);
		
		final int numberOfStrides = getNumberOfStrides(dataItem.getStrides());
		detail.setNumberOfStrides(numberOfStrides);
		
		final float speed = getSpeedInMetersPerSecond(dataItem.getSpeedInMeterPerSecond());
		detail.setSpeedInMeterPerSecond(speed);
		
		final Date currentDate = new Date();
		detail.setTimestamp(new Timestamp(currentDate.getTime()));
		
		return detail;
	}
	
	private int getDistanceInMeter(final double distanceInMeter) {
		Double currentDistanceMeter = distanceInMeter;
		if (distanceInMeter < lastReadDistance) {
			currentDistanceMeter += MAX_DISTANCE_IN_METERS - lastReadDistance;
			hasDistanceOverflowed = true;
		}
			
		lastReadDistance = distanceInMeter;	
		
		if (hasDistanceOverflowed)
			return currentDistanceMeter.intValue();
		else
			return currentDistanceMeter.intValue() - (int)firstDistance;
	}
	
	private int getNumberOfStrides(final byte numberOfStrides) {
		byte currentNumberOfStrides = numberOfStrides;
		if (numberOfStrides < lastReadNumberOfStrides) {
			currentNumberOfStrides += MAX_NUMBER_OF_STRIDES - lastReadNumberOfStrides;
			hasStridesOverflowed = true;
		}	
		
		lastReadNumberOfStrides = numberOfStrides;
		if (hasStridesOverflowed)
			return currentNumberOfStrides;
		else
			return currentNumberOfStrides - firstStrides;
	}
	
	private float getSpeedInMetersPerSecond(final double speedInMetersPerSecond) {
		return (float)speedInMetersPerSecond;
	}

	public void prepareForFirstPackage() {
		isFirstPackage = true;
		hasDistanceOverflowed = false;
		hasStridesOverflowed = false;
	}
}