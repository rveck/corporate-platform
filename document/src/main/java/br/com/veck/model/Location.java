package br.com.veck.model;

public class Location {
	private long distance;
	private long woeid;
	
	public Location(long distance, long woeid) {
		super();
		this.distance = distance;
		this.woeid = woeid;
	}
	public long getDistance() {
		return distance;
	}
	public void setDistance(long distance) {
		this.distance = distance;
	}
	public long getWoeid() {
		return woeid;
	}
	public void setWoeid(long woeid) {
		this.woeid = woeid;
	}

}
