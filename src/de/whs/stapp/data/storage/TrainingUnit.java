package de.whs.stapp.data.storage;


/**
 * @author Christoph Inhestern
 * Class to represent the Data from one Training session.
 */
public class TrainingUnit {
	protected int sessionID;	
	protected int distance;
	protected long date;
		
	/**
	 * @return the sessionID
	 */
	public int getSessionID() {
		return sessionID;
	}
	
	/**
	 * @param sessionID the sessionID to set
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}	
	
	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}
	
	/**
	 * @param date the date to set
	 */
	public void setDate(long date) {
		this.date = date;
	}
}

//Andere Möglichkeit wäre in einem Training alle werte aufzunehmen 
//und mit einer Liste dieser zu arbeiten. sessionID dann redundant
