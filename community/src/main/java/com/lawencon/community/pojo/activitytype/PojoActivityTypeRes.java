package com.lawencon.community.pojo.activitytype;

public class PojoActivityTypeRes {
	private String activityTypeId;
	private String typeName;
	private String typeCode;
	private Boolean isActive;
	private Integer ver;
	
	
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getActivityTypeId() {
		return activityTypeId;
	}
	public void setActivityTypeId(String activityTypeId) {
		this.activityTypeId = activityTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
