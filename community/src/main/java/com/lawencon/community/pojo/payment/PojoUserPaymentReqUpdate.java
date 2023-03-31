package com.lawencon.community.pojo.payment;

public class PojoUserPaymentReqUpdate {
	
	private String paymentId;
	private String bankPaymentId;

	private String fileExtension;
	private String fileContent;
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

	public String getBankPaymentId() {
		return bankPaymentId;
	}

	public void setBankPaymentId(String bankPaymentId) {
		this.bankPaymentId = bankPaymentId;
	}


	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	
	

}
