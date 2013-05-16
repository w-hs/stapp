package de.whs.stapp.data.access;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * POJO für Chart-Daten.
 * 
 * @author Dennis
 *
 */
public class ChartData {
	private final List<Coordinate> coordinates;
	private final ValueType typeOfX;
	private final ValueType typeOfY;
	private float minValueX;
	private float maxValueX;
	private float minValueY;
	private float maxValueY;
		
	/**
	 * Creates a new Instance of the {@link ChartData} class.
	 * @param typeOfX Typ der x-Achse
	 * @param typeOfY Typ der y-Achse
	 */
	public ChartData(ValueType typeOfX, ValueType typeOfY) {
		this.coordinates = new ArrayList<Coordinate>();
		this.typeOfX = typeOfX;
		this.typeOfY = typeOfY;
		
		minValueX = Float.MAX_VALUE;
		maxValueX = Float.MIN_VALUE;
		minValueY = Float.MAX_VALUE;
		maxValueY = Float.MIN_VALUE;
	}

	/**
	 * @return the typeOfX
	 */
	public ValueType getTypeOfX() {
		return typeOfX;
	}

	/**
	 * @return the typeOfY
	 */
	public ValueType getTypeOfY() {
		return typeOfY;
	}
	
	/**
	 * @return the minValueX
	 */
	public float getMinValueX() {
		return minValueX;
	}

	/**
	 * @return the maxValueX
	 */
	public float getMaxValueX() {
		return maxValueX;
	}

	/**
	 * @return the minValueY
	 */
	public float getMinValueY() {
		return minValueY;
	}

	/**
	 * @return the maxValueY
	 */
	public float getMaxValueY() {
		return maxValueY;
	}

	/**
	 * @return the coordinates
	 */
	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

	/**
	 * Fügt eine neue Koordinate hinzu.
	 * @param coordinate Die Koordinate.
	 */
	public void addCoordinate(Coordinate coordinate) {
		if (coordinate == null) 
			throw new IllegalArgumentException("coordinate cannot be null!");
		
		coordinates.add(coordinate);
		updateMinMaxValues(coordinate);
	}

	private void updateMinMaxValues(Coordinate coordinate) {
		if (coordinate.x < minValueX)
			minValueX = coordinate.x;
		if (coordinate.x > maxValueX)
			maxValueX = coordinate.x;
		if (coordinate.y < minValueY)
			minValueY = coordinate.y;
		if (coordinate.y > maxValueY)
			maxValueY = coordinate.y;
	}
}
