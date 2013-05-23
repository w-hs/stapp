package de.whs.stapp.data.access;

import java.util.List;

/**
 * @author Dennis
 */
public class ChartDataAggregator {

	/**
	 * Fasst die übergebenen Daten in Pakete der Größe "amountOfDetail" zusammen.
	 * 
	 * @param data Die Diagramm-Daten, in denen die Koordinaten zusammen gefasst werden sollen.
	 * @param coordinates Liste der Koordinaten.
	 * @param amountOfDetail Paketgröße
	 */
	public static void aggregate(ChartData data, List<Coordinate> coordinates, int amountOfDetail) {
		int blockSize = coordinates.size() / (amountOfDetail - 1);
		int rest = coordinates.size() % (amountOfDetail - 1);
		int block = 0;
		
		for (block = 0; block < coordinates.size(); block += blockSize)
			data.addCoordinate(summarizeCoordinates(coordinates, block, blockSize));
		
		if(rest > 0)
			data.addCoordinate(summarizeCoordinates(coordinates, block, rest));
	}
	
	private static Coordinate summarizeCoordinates(List<Coordinate> coordinates, int currentBlock, int blockSize) {
		List<Coordinate> block = coordinates.subList(currentBlock, currentBlock + blockSize);
		float sumOfX = 0;
		float sumOfY = 0;
		
		for (Coordinate c: block) {
			sumOfX += c.x;
			sumOfY += c.y;
		}
		
		return new Coordinate(sumOfX / blockSize, sumOfY / blockSize);
	}
}
