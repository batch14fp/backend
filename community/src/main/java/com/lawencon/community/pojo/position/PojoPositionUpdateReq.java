package com.lawencon.community.pojo.position;

public class PojoPositionUpdateReq {
	private String positionId;
	private String positionCode;
	private String postionName;
	private Integer ver;
	private Boolean isActive;
	
	
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
	public String getPostionName() {
		return postionName;
	}
	public void setPostionName(String postionName) {
		this.postionName = postionName;
	}
	
	
}
