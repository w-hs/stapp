package de.whs.stapp.test.data.access;

import java.sql.Timestamp;
import java.util.Date;

import junit.framework.TestCase;
import de.whs.stapp.data.access.StopWatch;

//CHECKSTYLE:OFF

/*
 * Ich wusste nicht, wie ich die StopWatch Klasse ohne
 * die Verwendung von Thread.sleep(...) sinnvoll testen
 * kann.
 */

/**
 * Testet die {@link StopWatch} Klasse.
 * Achtung: Bei diesem Test wird u.a. Thread.sleep verwendet,
 * die Ausführung dauert daher ca. 3 Sekunden... 
 * @author Chris
 */
public class StopWatchTest extends TestCase {
	
	public void testCreateStopWatch() {
		StopWatch watch = new StopWatch();
		
		assertEquals(0, watch.getElapsedMilliseconds());
		assertEquals(new Timestamp(0), watch.getElapsedTimestamp());
		assertEquals(false, watch.isRunning());
	}
	
	public void testStopWatchStartNew() {
		StopWatch watch = StopWatch.startNew();
		assertEquals(true, watch.isRunning());
		
		// Bevor nicht stop() gerufen wurde, ist die elapsedTime = 0!
		assertEquals(0, watch.getElapsedMilliseconds());
	}
	
	public void testStopWatchReset() throws InterruptedException {
		StopWatch watch = new StopWatch();	
		watch.start();
		watch.reset();
		
		assertEquals(0, watch.getElapsedMilliseconds());
		assertEquals(new Timestamp(0), watch.getElapsedTimestamp());
		assertEquals(false, watch.isRunning());		
	}
	
	@SuppressWarnings("deprecation")
	public void testElapsedTimeWithMultipleStartStop() throws InterruptedException {
		Date d;
		long elapsedTime;
		Timestamp timestamp;
		StopWatch watch = new StopWatch();
		
		watch.start();
		Thread.sleep(1000);
		watch.stop();
		
		elapsedTime = watch.getElapsedMilliseconds();
		timestamp = watch.getElapsedTimestamp();
		d = new Date(elapsedTime);
		
		assertTrue(elapsedTime > 1000);
		assertEquals(1, d.getSeconds());
		assertEquals(1, timestamp.getSeconds());
		
		
		watch.start();
		Thread.sleep(1000);
		watch.stop();
		
		elapsedTime = watch.getElapsedMilliseconds();
		timestamp = watch.getElapsedTimestamp();
		d = new Date(elapsedTime);
		
		assertTrue(elapsedTime > 2000);
		assertEquals(2, d.getSeconds());
		assertEquals(2, timestamp.getSeconds());
	}	
}

//CHECKSTYLE:ON