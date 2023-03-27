package com.lawencon.community.constant;

public enum ActivityTypeEnum {
	EVENT("EVN", "Event"), COURSE("COU", "Course");
	private String code;
	private String name;
	
	ActivityTypeEnum(final String code, final String name){
		this.code = code;
		this.name = name;
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
