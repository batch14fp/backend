package com.lawencon.community.model;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;



@Entity
@Table(name="t_wallet")
public class Wallet  extends BaseEntity{
	private  BigInteger saldo;
	@OneToOne
	@JoinColumn(name = "user_id" )
	private User user;
	public BigInteger getSaldo() {
		return saldo;
	}
	public void setSaldo(BigInteger saldo) {
		this.saldo = saldo;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
