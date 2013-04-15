package de.whs.stapp.mocks;

import de.whs.stapp.liveDataTracking.TrackedDataEvent;
import de.whs.stapp.liveDataTracking.TrackedDataListener;
import de.whs.stapp.liveDataTracking.BTServiceConnectionRegisterable;
import de.whs.stapp.liveDataTracking.TrackedDataItem;


public class BTCommunicationListenerMock implements BTServiceConnectionRegisterable {

	private TrackedDataListener listener;
	
	private TrackedDataItem createDataItem() {
		int[] beats = { 1, 2, 3, 4, 5 };
		return new TrackedDataItem("123", "1.4", "789", "10.2", (byte) 50,
				(byte) 80, (byte) 200, beats, 1000.4, 10.2, (byte) 120);
	}

	private TrackedDataEvent createEvent() {
		return new TrackedDataEvent(this, createDataItem());
	}

	void executeEventThreeTimes() {		
		listener.getTrackedData(createEvent());
		// Im Unit Test keine Zeit vertrödeln!!
		// Thread.sleep(1000);
		listener.getTrackedData(createEvent());
		listener.getTrackedData(createEvent());
	}

	public void registerListener(TrackedDataListener listener) {
		this.listener = listener;
	}
}