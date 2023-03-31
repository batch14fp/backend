package com.lawencon.community.pojo.salessetting;

public class PojoSalesSettingReqUpdate {
	
	
	private String salesSettingId;
	private Float systemIncome;
	private Float memberIncome;
	private Float tax;
	private Integer ver;
	public String getSalesSettingId() {
		return salesSettingId;
	}
	public void setSalesSettingId(String salesSettingId) {
		this.salesSettingId = salesSettingId;
	}
	public Float getSystemIncome() {
		return systemIncome;
	}
	public void setSystemIncome(Float systemIncome) {
		this.systemIncome = systemIncome;
	}
	public Float getMemberIncome() {
		return memberIncome;
	}
	public void setMemberIncome(Float memberIncome) {
		this.memberIncome = memberIncome;
	}
	public Float getTax() {
		return tax;
	}
	public void setTax(Float tax) {
		this.tax = tax;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}


}
