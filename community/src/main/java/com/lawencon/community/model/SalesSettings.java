package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_sales_settings")
public class SalesSettings extends BaseEntity{
	private Float tax;
	private Float systemIncome;
	private Float memberIncome;
	public Float getTax() {
		return tax;
	}
	public void setTax(Float tax) {
		this.tax = tax;
	}

	
	public Float getSystemIncome() {
		return systemIncome;
	}
	public void setSystemIncome(Float systemIncome) {
		this.systemIncome = systemIncome;
	}
	public Float getMemberIncome() {
		return memberIncome;
	}
	public void setMemberIncome(Float memberIncome) {
		this.memberIncome = memberIncome;
	}
	
	
	
	

}
