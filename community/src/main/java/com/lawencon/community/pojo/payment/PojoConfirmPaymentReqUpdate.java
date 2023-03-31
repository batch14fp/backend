package com.lawencon.community.pojo.payment;

public class PojoConfirmPaymentReqUpdate {
	
	private String paymentId;
	private Boolean isPaid;
	private Integer ver;

	

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}
	

}