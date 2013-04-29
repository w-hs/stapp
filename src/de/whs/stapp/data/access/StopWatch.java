package de.whs.stapp.data.access;

import java.sql.Timestamp;

/**
 * Klasse für die Zeitmessung.
 * @author Chris
 */
public class StopWatch {

	private long startTime = 0;
	private long elapsedTime = 0;
	private boolean runs = false;
	
	/**
	 * @return Einen Wert der angibt, ob die Stopwatch läuft.
	 */
	public boolean isRunning() { return runs; }
	
	/**
	 * @return Die gemessene Zeit in Millisekunden.
	 */
	public long getElapsedTime() { return elapsedTime; }
	
	/**
	 * @return Die gemessene Zeit als {@link Timestamp}.
	 */
	public Timestamp getElapsedTimestamp() { return new Timestamp(elapsedTime); }
	
	/**
	 * Erzeugt eine neue Instanz der {@link StopWatch} Klasse.
	 */
	public StopWatch() {
		reset();
	}
	
	/**
	 * @return Liefert eine bereits gestartete Stopwatch Instanz zurück.
	 */
	public static StopWatch startNew() {
		StopWatch watch = new StopWatch();
		watch.start();
		
		return watch;
	}
	
	/**
	 * Startet eine Zeitmessung.
	 */
	public void start() {
		if (!runs) {
			startTime = System.currentTimeMillis();
			runs = true;
		}
	}
	
	/**
	 * Stoppt die Zeitmessung.
	 */
	public void stop() {
		if (runs) { 
			elapsedTime += System.currentTimeMillis() - startTime;
			runs = false;
		}
	}
	
	/**
	 * Setzt die Werte der Zeitmessung wieder zurück.
	 */
	public void reset() {
		elapsedTime = 0;
		startTime = 0;
		runs = false;
	}
}
