package de.whs.stapp.data.access;

import java.util.TreeMap;

/**
 * 
 * POJO für Chart-Daten.
 * 
 * @author Dennis
 *
 */
public class ChartData {
	private TreeMap<Float, Float> coordinates =new TreeMap<Float, Float>();	
	private ValueType typeOfX;
	private ValueType typeOfY;
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
		super();
		this.typeOfX = typeOfX;
		this.typeOfY = typeOfY;
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
	 * @param minValueX the minValueX to set
	 */
	public void setMinValueX(float minValueX) {
		this.minValueX = minValueX;
	}

	/**
	 * @return the maxValueX
	 */
	public float getMaxValueX() {
		return maxValueX;
	}

	/**
	 * @param maxValueX the maxValueX to set
	 */
	public void setMaxValueX(float maxValueX) {
		this.maxValueX = maxValueX;
	}

	/**
	 * @return the minValueY
	 */
	public float getMinValueY() {
		return minValueY;
	}

	/**
	 * @param minValueY the minValueY to set
	 */
	public void setMinValueY(float minValueY) {
		this.minValueY = minValueY;
	}

	/**
	 * @return the maxValueY
	 */
	public float getMaxValueY() {
		return maxValueY;
	}

	/**
	 * @param maxValueY the maxValueY to set
	 */
	public void setMaxValueY(float maxValueY) {
		this.maxValueY = maxValueY;
	}

	/**
	 * @return the coordinates
	 */
	public TreeMap<Float, Float> getCoordinates() {
		return coordinates;
	}
	
	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(TreeMap<Float, Float> coordinates){
		this.coordinates = coordinates;
	}
}
