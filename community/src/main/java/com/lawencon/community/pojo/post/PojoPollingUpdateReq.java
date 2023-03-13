package com.lawencon.community.pojo.post;

import java.util.List;

public class PojoPollingUpdateReq {
	
	private String pollingId;
	private String pollingOptionId;
	
	private String pollingTitle;
//	private String endAt;
	private List<PojoPollingOptionUpdateReq> pollingOptions;
	private Boolean isActive;
	private Integer ver;
	
	public String getPollingTitle() {
		return pollingTitle;
	}
	public void setPollingTitle(String pollingTitle) {
		this.pollingTitle = pollingTitle;
	}

	/*
	 * public String getEndAt() { return endAt; } public void setEndAt(String endAt)
	 * { this.endAt = endAt; }
	 */	
	
	
	
	public String getPollingId() {
		return pollingId;
	}
	
	public List<PojoPollingOptionUpdateReq> getPollingOptions() {
		return pollingOptions;
	}
	public void setPollingOptions(List<PojoPollingOptionUpdateReq> pollingOptions) {
		this.pollingOptions = pollingOptions;
	}
	public void setPollingId(String pollingId) {
		this.pollingId = pollingId;
	}
	public String getPollingOptionId() {
		return pollingOptionId;
	}
	public void setPollingOptionId(String pollingOptionId) {
		this.pollingOptionId = pollingOptionId;
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
