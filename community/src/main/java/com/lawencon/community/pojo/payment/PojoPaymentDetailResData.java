package com.lawencon.community.pojo.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PojoPaymentDetailResData {
	private String invoiceId;
	private String activityId;
	private String bankPaymetId;
	private String paymentId;
	private String membershipId;
	private String filePaymentId;
	private LocalDateTime paymentExpired;
	private BigDecimal activityPrice;
	private BigDecimal taxAmmount;
	private BigDecimal subTotal;
	private BigDecimal total;
	private BigDecimal discAmmount;
	private String bankName;
	private String titleActivity;
	private String accountNumber;
	private String accountName;
	private String imageActivity;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String codeStatus;
	private String statusName;
	private Integer periodDay;
	private String nameCreated;
	private String type;
	private String categoryName;
	private BigDecimal priceMemberShip;
	private Boolean isPaid;
	private String invoiceCode;
	private Integer ver;
	private String statusTrans;
	
	
	public String getStatusTrans() {
		return statusTrans;
	}
	public void setStatusTrans(String statusTrans) {
		this.statusTrans = statusTrans;
	}
	public String getCodeStatus() {
		return codeStatus;
	}
	public void setCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
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

	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getBankPaymetId() {
		return bankPaymetId;
	}
	public void setBankPaymetId(String bankPaymetId) {
		this.bankPaymetId = bankPaymetId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public BigDecimal getDiscAmmount() {
		return discAmmount;
	}
	public void setDiscAmmount(BigDecimal discAmmount) {
		this.discAmmount = discAmmount;
	}
	public BigDecimal getTaxAmmount() {
		return taxAmmount;
	}
	public void setTaxAmmount(BigDecimal taxAmmount) {
		this.taxAmmount = taxAmmount;
	}
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getTitleActivity() {
		return titleActivity;
	}
	public void setTitleActivity(String titleActivity) {
		this.titleActivity = titleActivity;
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
	public String getImageActivity() {
		return imageActivity;
	}
	public void setImageActivity(String imageActivity) {
		this.imageActivity = imageActivity;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public LocalDateTime getPaymentExpired() {
		return paymentExpired;
	}
	public void setPaymentExpired(LocalDateTime paymentExpired) {
		this.paymentExpired = paymentExpired;
	}
	public BigDecimal getActivityPrice() {
		return activityPrice;
	}
	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
	public BigDecimal getPriceMemberShip() {
		return priceMemberShip;
	}
	public void setPriceMemberShip(BigDecimal priceMemberShip) {
		this.priceMemberShip = priceMemberShip;
	}
	public Boolean getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getFilePaymentId() {
		return filePaymentId;
	}
	public void setFilePaymentId(String filePaymentId) {
		this.filePaymentId = filePaymentId;
	}
	public String getNameCreated() {
		return nameCreated;
	}
	public void setNameCreated(String nameCreated) {
		this.nameCreated = nameCreated;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
