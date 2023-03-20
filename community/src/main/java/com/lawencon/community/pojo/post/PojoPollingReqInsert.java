package com.lawencon.community.pojo.post;

import java.time.LocalDate;
import java.util.List;

public class PojoPollingReqInsert {
	private String postId;
	private String pollingTitle;
	private LocalDate endAt;
	private List<PojoPollingOptionReqInsert> pollingOptions;
	public String getPollingTitle() {
		return pollingTitle;
	}
	public void setPollingTitle(String pollingTitle) {
		this.pollingTitle = pollingTitle;
	}
	public List<PojoPollingOptionReqInsert> getPollingOptions() {
		return pollingOptions;
	}
	public void setPollingOptions(List<PojoPollingOptionReqInsert> pollingOptions) {
		this.pollingOptions = pollingOptions;
	}

	public LocalDate getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalDate endAt) {
		this.endAt = endAt;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	
}
