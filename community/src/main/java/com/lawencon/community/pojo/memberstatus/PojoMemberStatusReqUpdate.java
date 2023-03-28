package com.lawencon.community.pojo.memberstatus;

import java.math.BigDecimal;

public class PojoMemberStatusReqUpdate {
	private String memberStatusId;
	private String statusName;
	private Integer periodDay;
	private BigDecimal price;
	private Boolean isActive;
	private Integer ver;
	

	public String getStatusName() {
		return statusName;
	}
	
	
	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
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
