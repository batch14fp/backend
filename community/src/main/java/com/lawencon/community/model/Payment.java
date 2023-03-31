package com.lawencon.community.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_payment")
public class Payment  extends BaseEntity{
	

	@OneToOne
	@JoinColumn(name="invoice_id", nullable=false)
	private Invoice invoice;
	
	
	@OneToOne
	@JoinColumn(name="file_id")
	private File file;
	
	
	@OneToOne
	@JoinColumn(name="bank_payment_id")
	private BankPayment bankPayment;

	private Boolean isPaid;
	
	
	@Column(nullable=false)
	private LocalDateTime expired;
	
	
	private BigDecimal discAmount;
	
	
	private BigDecimal taxAmount;
	
	
	private BigDecimal subtotal;
	
	
	private BigDecimal total;
	

	public BigDecimal getDiscAmount() {
		return discAmount;
	}


	public void setDiscAmount(BigDecimal discAmount) {
		this.discAmount = discAmount;
	}


	public BigDecimal getTaxAmount() {
		return taxAmount;
	}


	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}


	public BigDecimal getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}


	public BigDecimal getTotal() {
		return total;
	}


	public void setTotal(BigDecimal total) {
		this.total = total;
	}


	public Invoice getInvoice() {
		return invoice;
	}


	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}


	public Boolean getIsPaid() {
		return isPaid;
	}


	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}




	public BankPayment getBankPayment() {
		return bankPayment;
	}


	public void setBankPayment(BankPayment bankPayment) {
		this.bankPayment = bankPayment;
	}


	public LocalDateTime getExpired() {
		return expired;
	}


	public void setExpired(LocalDateTime expired) {
		this.expired = expired;
	}
	

}
