package com.lawencon.community.constant;

public enum StatusEnum {
	
	REGULAR("REG", "REGULAR");
	
	private String statusCode;
	private String statusName;
	StatusEnum(final String statusCode, final String statusName){
		this.statusName = statusName;
		this.statusCode = statusCode;
		
	}
	public String getStatusCode() {
		return statusCode;
	}
	public String getStatusName() {
		return statusName;
	}
	

	
	

}
