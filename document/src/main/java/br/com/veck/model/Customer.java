package br.com.veck.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer implements Serializable{
	
	private static final long serialVersionUID = 7557626692720639994L;
	
	@Id
	private String id;
	private String name;
	private Integer age;	
	private Geolocalization Geolocalization;
	private Weather weather;
	
	@Transient
	private String operation;
	
	@Transient
	private String ip;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Geolocalization getGeolocalization() {
		return Geolocalization;
	}
	public void setGeolocalization(Geolocalization geolocalization) {
		Geolocalization = geolocalization;
	}
	public Weather getWeather() {
		return weather;
	}
	public void setWeather(Weather weather) {
		this.weather = weather;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
