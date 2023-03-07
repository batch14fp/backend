package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;




@Entity
@Table(name="t_invoice",
uniqueConstraints = {
        @UniqueConstraint(name = "invoice_code_bk", 
                columnNames = {"invoiceCode" }
        )})
public class Invoice  extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="member_id", nullable=false)
	private Activity activity;

	@OneToOne
	@JoinColumn(name="voucher_id", nullable=false)
	private Voucher voucher;
	
	@Column(length=12, nullable= false)
	private String invoiceCode;

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	
	
}
