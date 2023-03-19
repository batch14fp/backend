package com.lawencon.community.pojo.salessetting;

public class PojoSalesSettingRes {
	private float systemIncome;
	private float memberIncome;
	private float tax;
	private Boolean isActive;
	private Integer ver;
	
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
	public float getSystemIncome() {
		return systemIncome;
	}
	public void setSystemIncome(float systemIncome) {
		this.systemIncome = systemIncome;
	}
	public float getMemberIncome() {
		return memberIncome;
	}
	public void setMemberIncome(float memberIncome) {
		this.memberIncome = memberIncome;
	}
	public float getTax() {
		return tax;
	}
	public void setTax(float tax) {
		this.tax = tax;
	}
	
	
	

}
