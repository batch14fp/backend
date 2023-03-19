package com.lawencon.community.pojo.post;

public class PojoPollingOptionReqUpdate {
	private String pollingOptionId;
	private String pollingOptionContent;
	private Boolean isActive;
	private Integer ver;
	public String getPollingOptionId() {
		return pollingOptionId;
	}
	public void setPollingOptionId(String pollingOptionId) {
		this.pollingOptionId = pollingOptionId;
	}
	public String getPollingOptionContent() {
		return pollingOptionContent;
	}
	public void setPollingOptionContent(String pollingOptionContent) {
		this.pollingOptionContent = pollingOptionContent;
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