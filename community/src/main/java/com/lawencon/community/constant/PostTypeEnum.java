package com.lawencon.community.constant;

public enum PostTypeEnum {
	
	POLLING("PPOL", "Post Polling"),PREMIUM("PPRM","Post Premium" );
	
	private String code;
	private String name;
	
	PostTypeEnum(final String code, final String name){
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
