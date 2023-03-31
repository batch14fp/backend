package com.lawencon.community.pojo.posttype;

public class PojoPostTypeReqUpdate {
	private String postTypeId;
	private String postTypeName;
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
