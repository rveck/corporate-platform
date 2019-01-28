package br.com.veck.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Weather implements Serializable{
	
	private static final long serialVersionUID = 6458183841040872694L;
	
	private Date date;
	private BigDecimal minTemp;
	private BigDecimal maxTemp;
	
	public Weather(Date date, BigDecimal minTemp, BigDecimal maxTemp) {
		super();
		this.date = date;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(BigDecimal minTemp) {
		this.minTemp = minTemp;
	}
	public BigDecimal getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(BigDecimal maxTemp) {
		this.maxTemp = maxTemp;
	}
}
