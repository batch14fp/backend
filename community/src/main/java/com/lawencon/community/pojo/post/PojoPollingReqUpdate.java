package com.lawencon.community.pojo.post;

import java.time.LocalDate;
import java.util.List;

public class PojoPollingReqUpdate {

	private String pollingId;
	private String pollingOptionId;
	private Boolean isOpen;
	private String pollingTitle;
	private LocalDate endAt;
	private List<PojoPollingOptionReqUpdate> pollingOptions;
	private Boolean isActive;
	private Integer ver;

	public String getPollingTitle() {
		return pollingTitle;
	}

	public void setPollingTitle(String pollingTitle) {
		this.pollingTitle = pollingTitle;
	}

	public String getPollingId() {
		return pollingId;
	}

	public List<PojoPollingOptionReqUpdate> getPollingOptions() {
		return pollingOptions;
	}

	public void setPollingOptions(List<PojoPollingOptionReqUpdate> pollingOptions) {
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

	public LocalDate getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDate endAt) {
		this.endAt = endAt;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

}
