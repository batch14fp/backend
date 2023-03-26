package com.lawencon.community.pojo.memberstatus;

import java.math.BigDecimal;

public class PojoMemberStatusRes {
	private String memberStatusId;
	private String codeStatus;
	private String statusName;
	private BigDecimal price;
	private Integer periodDay;
	private Boolean isActive;
	private Integer ver;
	
	public String getCodeStatus() {
		return codeStatus;
	}
	public void setCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
	}
	
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getPeriodDay() {
		return periodDay;
	}
	public void setPeriodDay(Integer periodDay) {
		this.periodDay = periodDay;
	}
	public String getMemberStatusId() {
		return memberStatusId;
	}
	public void setMemberStatusId(String memberStatusId) {
		this.memberStatusId = memberStatusId;
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
