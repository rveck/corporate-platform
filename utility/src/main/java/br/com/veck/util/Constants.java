package br.com.veck.util;

public class Constants {
	public static final String CUSTOMER_SERVICE_ENDPOINT = "/customer";
	
	public static final String ID = "id";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	public static enum Operations{
		CREATE("create"), UPDATE("update"), DELETE("delete");
		
		private String value;
		
		private Operations(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
}
