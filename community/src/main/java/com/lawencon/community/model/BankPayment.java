package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_bank_payment",
uniqueConstraints = {
        @UniqueConstraint(name = "account_number_bk", 
                columnNames = {"accountNumber" }
        )
		}) 
public class BankPayment extends BaseEntity{
	
	@Column(length = 30, nullable = false)
	private String bankName;
	
	@Column(length = 50, nullable = false)
	private String accountNumber;
	
	
	@Column(length = 50, nullable = false)
	private String accountName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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
