package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_sales_settings")
public class SalesSettings extends BaseEntity{
	private Float tax;
	private Float systemiIncome;
	private Float memberIncome;
	public Float getTax() {
		return tax;
	}
	public void setTax(Float tax) {
		this.tax = tax;
	}
	public Float getSystemiIncome() {
		return systemiIncome;
	}
	public void setSystemiIncome(Float systemiIncome) {
		this.systemiIncome = systemiIncome;
	}
	public Float getMemberIncome() {
		return memberIncome;
	}
	public void setMemberIncome(Float memberIncome) {
		this.memberIncome = memberIncome;
	}
	
	
	
	

}
