/**
 * 
 */
package de.whs.stapp.data.access;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Dennis
 *
 */
public class ChartDataAggregator {
	

	/**
	 * 
	 */
	public ChartDataAggregator() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Fasst die übergebenen Daten in Pakete der Größe "amountOfDetail" zusammen.
	 * 
	 * @param data Datentupel, die zusammengefasst werden sollen
	 * @param amountOfDetail Paketgröße
	 * @return zusammengefasste Daten
	 */
	public static TreeMap<Float, Float> aggregate(final TreeMap<Float, Float> data, final int amountOfDetail) {
		TreeMap<Float, Float> result = new TreeMap<Float, Float>();
		int chunkSize = data.size() / (amountOfDetail -1);
		int rest = data.size() % (amountOfDetail - 1);
		float sumOfKeys = 0;
		float sumOfValues = 0;
		List<Float> keys = null;
		int i ;
		
		for (i = 0; i < data.size(); i += chunkSize){
			keys = new ArrayList<Float>(data.navigableKeySet()).subList(i, i + chunkSize);
						
			for (Float key : keys){
				sumOfKeys += key;
				sumOfValues += data.get(key);
			}
			
			result.put(sumOfKeys/chunkSize, sumOfValues/chunkSize);
			sumOfKeys = 0;
			sumOfValues = 0;
		}
		
		keys = new ArrayList<Float>(data.navigableKeySet()).subList(i, i + rest);
		for (Float key : keys){
			sumOfKeys += key;
			sumOfValues += data.get(key);
		}

		result.put(sumOfKeys/rest, sumOfValues/rest);
		
		return result;
	}

}
