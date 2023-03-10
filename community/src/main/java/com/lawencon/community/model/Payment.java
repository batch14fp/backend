package com.lawencon.community.model;

import java.time.LocalDate;

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
	@JoinColumn(name="file_id", nullable=false)
	private File file;
	
	
	@OneToOne
	@JoinColumn(name="bank_payment_id", nullable=false)
	private BankPayment bankPayment;

	private Boolean isPaid;
	
	
	private LocalDate expired;


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


	public LocalDate getExpired() {
		return expired;
	}


	public void setExpired(LocalDate expired) {
		this.expired = expired;
	}


	public BankPayment getBankPayment() {
		return bankPayment;
	}


	public void setBankPayment(BankPayment bankPayment) {
		this.bankPayment = bankPayment;
	}
	

}
