package com.lawencon.community.pojo.socialmedia;

public class PojoSocialMediaUserUpdateReq {
	private String profileSocialMediaId;
	private String url;
	private Boolean isActive;
	private Integer ver;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getProfileSocialMediaId() {
		return profileSocialMediaId;
	}
	public void setProfileSocialMediaId(String profileSocialMediaId) {
		this.profileSocialMediaId = profileSocialMediaId;
	}
	
}
