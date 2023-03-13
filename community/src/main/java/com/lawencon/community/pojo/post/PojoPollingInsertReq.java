package com.lawencon.community.pojo.post;

import java.util.List;

public class PojoPollingInsertReq {
	private String pollingTitle;
//	private String endAt;
	private List<PojoPollingOptionInsertReq> pollingOptions;
	public String getPollingTitle() {
		return pollingTitle;
	}
	public void setPollingTitle(String pollingTitle) {
		this.pollingTitle = pollingTitle;
	}
	public List<PojoPollingOptionInsertReq> getPollingOptions() {
		return pollingOptions;
	}
	public void setPollingOptions(List<PojoPollingOptionInsertReq> pollingOptions) {
		this.pollingOptions = pollingOptions;
	}

	/*
	 * public String getEndAt() { return endAt; } public void setEndAt(String endAt)
	 * { this.endAt = endAt; }
	 */
	
	
}
