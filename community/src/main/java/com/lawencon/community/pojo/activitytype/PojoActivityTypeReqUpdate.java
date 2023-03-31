package com.lawencon.community.pojo.activitytype;

public class PojoActivityTypeReqUpdate {
	private String activityTypeId;
	private String typeName;
	private Boolean isActive;
	private Integer ver;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getActivityTypeId() {
		return activityTypeId;
	}
	public void setActivityTypeId(String activityTypeId) {
		this.activityTypeId = activityTypeId;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	
}
