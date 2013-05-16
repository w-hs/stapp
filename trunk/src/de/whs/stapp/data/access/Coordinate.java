package de.whs.stapp.data.access;

/**
 * Definiert eine Koordinate.
 * @author Chris
 */
public class Coordinate {
	
	/**
	 * Die X-Koordinate.
	 */
	public float x;
	
	/**
	 * Die Y-Koordinate.
	 */
	public float y;
	
	/**
	 * Erstellt eine neue Instanz der {@link Coordinate} Klasse.
	 * @param x X-Koordinate.
	 * @param y Y-Koordinate.
	 */
	public Coordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
