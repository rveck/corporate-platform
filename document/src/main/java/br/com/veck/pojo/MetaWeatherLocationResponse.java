package br.com.veck.pojo;

import java.math.BigDecimal;

public class MetaWeatherLocationResponse {
	
	private BigDecimal distance;
	private String title; 
	private String locationType;
	private BigDecimal woeid;
	private String latLong;
	
	public BigDecimal getDistance() {
		return distance;
	}
	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public BigDecimal getWoeid() {
		return woeid;
	}
	public void setWoeid(BigDecimal woeid) {
		this.woeid = woeid;
	}
	public String getLatLong() {
		return latLong;
	}
	public void setLatLong(String latLong) {
		this.latLong = latLong;
	}
}
