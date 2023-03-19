package com.lawencon.community.pojo.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PojoPaymentReqInsert {
	private String invoiceId;
	private String fileId;
	private String bankPaymentId;
	private BigDecimal discAmmount;
	private BigDecimal taxAmmount;
	private BigDecimal subTotal;
	private BigDecimal total;
	private LocalDate expired;
	private Boolean isPaid;
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getBankPaymentId() {
		return bankPaymentId;
	}
	public void setBankPaymentId(String bankPaymentId) {
		this.bankPaymentId = bankPaymentId;
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
	public LocalDate getExpired() {
		return expired;
	}
	public void setExpired(LocalDate expired) {
		this.expired = expired;
	}
	public Boolean getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	

}
