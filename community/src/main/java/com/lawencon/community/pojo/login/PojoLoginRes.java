package com.lawencon.community.pojo.login;

public class PojoLoginRes {
	private String userId;
	private String roleCode;
	private String fullname;
	private String token;
	private String imgProfileId;
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getImgProfileId() {
		return imgProfileId;
	}
	public void setImgProfileId(String imgProfileId) {
		this.imgProfileId = imgProfileId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
