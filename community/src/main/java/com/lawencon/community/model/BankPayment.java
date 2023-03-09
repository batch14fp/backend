package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_bank_payment",
uniqueConstraints = {
        @UniqueConstraint(name = "bank_bk", 
                columnNames = {"bankName" }
        )
		}) 
public class BankPayment extends BaseEntity{
	
	@Column(length = 30, nullable = false)
	private String bankName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	
	

}
