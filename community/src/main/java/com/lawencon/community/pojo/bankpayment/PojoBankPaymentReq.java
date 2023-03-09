package com.lawencon.community.pojo.bankpayment;

public class PojoBankPaymentReq {
	private String bankPaymentId;
	private String bankPaymentName;
	private String isActive;
	private String ver;
	public String getBankPaymentId() {
		return bankPaymentId;
	}
	public void setBankPaymentId(String bankPaymentId) {
		this.bankPaymentId = bankPaymentId;
	}
	public String getBankPaymentName() {
		return bankPaymentName;
	}
	public void setBankPaymentName(String bankPaymentName) {
		this.bankPaymentName = bankPaymentName;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}

}