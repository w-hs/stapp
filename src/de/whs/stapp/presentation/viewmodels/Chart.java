package de.whs.stapp.presentation.viewmodels;

import de.whs.stapp.data.access.ChartData;

/**
 * 
 * @author Chris
 *
 */
public class Chart extends StappViewModel {
	
	ChartData heartRateData;
	ChartData speedPerTime;
	
	/**
	 * @return Die Geschwindigkeit pro Zeit als JSON formatierter String.
	 */
	public String getSpeedPerTimeInJson() {
		if (speedPerTime == null)
			return "";
		
		return speedPerTime.toJSON();
	}
	
	/**
	 * @return the data
	 */
	public String getHeartRateData() {
		return heartRateData.toJSON();
	}

	/**
	 * @param heartRateData the data to set
	 */
	public void setHeartRateData(ChartData heartRateData) {
		this.heartRateData = heartRateData;
	}
	
	/**
	 * @param speedPerTime Die Geschwindigkeit pro Zeit.
	 */
	public void setSpeedPerTime(ChartData speedPerTime) {
		this.speedPerTime = speedPerTime;
	}
	
	@Override 
	public String toJSON(){
		
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("{");
		jsonString.append("\"label\":\"Lauf\",");
		jsonString.append(getHeartRateData());
		jsonString.append(",");
		jsonString.append(getSpeedPerTimeInJson());
		jsonString.append("}");
		return jsonString.toString();		
	}
}
