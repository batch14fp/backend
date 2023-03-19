package com.lawencon.community.pojo.position;

public class PojoPostionRes {
	private String positionId;
	private String positionCode;
	private String positionName;
	private Integer ver;
	private Boolean isActive;
	
	
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	
}
