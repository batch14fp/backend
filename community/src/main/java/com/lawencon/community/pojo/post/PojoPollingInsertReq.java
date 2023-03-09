package com.lawencon.community.pojo.post;

public class PojoPollingInsertReq {
	private String pollingTitle;
	private String endAt;
	private PojoPollingOptionRes pollingOption;
	public String getPollingTitle() {
		return pollingTitle;
	}
	public void setPollingTitle(String pollingTitle) {
		this.pollingTitle = pollingTitle;
	}
	public String getEndAt() {
		return endAt;
	}
	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}
	public PojoPollingOptionRes getPollingOption() {
		return pollingOption;
	}
	public void setPollingOption(PojoPollingOptionRes pollingOption) {
		this.pollingOption = pollingOption;
	}
	
}
