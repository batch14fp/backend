package com.lawencon.community.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_wallet")
public class Wallet extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "bank_payment_id")
	private BankPayment bankPayment;

	private BigDecimal balance;

	public BankPayment getBankPayment() {
		return bankPayment;
	}

	public void setBankPayment(BankPayment bankPayment) {
		this.bankPayment = bankPayment;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
