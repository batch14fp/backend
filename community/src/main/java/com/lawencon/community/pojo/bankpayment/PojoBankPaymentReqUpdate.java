package com.lawencon.community.pojo.bankpayment;

public class PojoBankPaymentReqUpdate {
	private String bankPaymentId;
	private String bankPaymentName;
	private String accountNumber;
	private String accountName;
	private Boolean isActive;
	private Integer ver;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

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
