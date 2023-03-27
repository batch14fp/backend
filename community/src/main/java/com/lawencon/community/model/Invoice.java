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
        ),
        @UniqueConstraint(name = "user_activity_ck", 
        columnNames = {"user_id", "activity_id" }
        )
        })
public class Invoice  extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="voucher_id")
	private Voucher voucher;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@OneToOne
	@JoinColumn(name="activity_id")
	private Activity activity;
	

	@OneToOne
	@JoinColumn(name="membership_id")
	private MemberStatus memberStatus;
	
	
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(length=12, nullable= false)
	private String invoiceCode;

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

	public MemberStatus getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(MemberStatus memberStatus) {
		this.memberStatus = memberStatus;
	}
	
	
	
}
