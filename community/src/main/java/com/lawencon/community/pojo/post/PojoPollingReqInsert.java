package com.lawencon.community.pojo.post;

public class PojoPollingReqInsert {
	private String pollingTitle;
	private String endAt;
	private PojoResGetPollingOption pollingOption;
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
	public PojoResGetPollingOption getPollingOption() {
		return pollingOption;
	}
	public void setPollingOption(PojoResGetPollingOption pollingOption) {
		this.pollingOption = pollingOption;
	}
	
}
