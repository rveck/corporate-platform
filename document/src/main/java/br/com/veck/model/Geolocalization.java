package br.com.veck.model;

import java.io.Serializable;

public class Geolocalization implements Serializable{
	
	private static final long serialVersionUID = 8079105979872419279L;
	
	private String ipv4;	    
    private String latitude;
    private String longitude;
    
	public String getIpv4() {
		return ipv4;
	}
	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
