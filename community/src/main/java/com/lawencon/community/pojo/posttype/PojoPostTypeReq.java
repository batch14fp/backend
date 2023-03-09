package com.lawencon.community.pojo.posttype;

public class PojoPostTypeReq {
	private String postTypeId;
	private String postTypeName;
	private String postTypeCode;
	private Integer ver;
	private Boolean isActive;
	public String getPostTypeId() {
		return postTypeId;
	}
	public void setPostTypeId(String postTypeId) {
		this.postTypeId = postTypeId;
	}
	public String getPostTypeName() {
		return postTypeName;
	}
	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}
	public String getPostTypeCode() {
		return postTypeCode;
	}
	public void setPostTypeCode(String postTypeCode) {
		this.postTypeCode = postTypeCode;
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
}
