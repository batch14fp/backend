package com.lawencon.community.pojo.bankpayment;

public class PojoBankPaymentReqInsert {

	private String bankPaymentName;
	private String accountNumber;
	private String accountName;
	
	public String getBankPaymentName() {
		return bankPaymentName;
	}
	public void setBankPaymentName(String bankPaymentName) {
		this.bankPaymentName = bankPaymentName;
	}
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
	
}