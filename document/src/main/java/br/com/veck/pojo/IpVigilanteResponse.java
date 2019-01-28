package br.com.veck.pojo;

import br.com.veck.model.Geolocalization;

public class IpVigilanteResponse {
	private String status;
	private Geolocalization data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Geolocalization getData() {
		return data;
	}
	public void setData(Geolocalization data) {
		this.data = data;
	}
}
