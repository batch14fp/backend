package com.lawencon.community.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_voucher",
uniqueConstraints = {
        @UniqueConstraint(name = "voucher_code_bk", 
                columnNames = {"voucherCode" }
        )})
public class Voucher extends BaseEntity{
	
	private Integer limitApplied;
	
	private Integer usedCount;
	
	@Column(length= 12, nullable= false)
	private String voucherCode;
	
	@Column(nullable= false)
	private Float discountPercent;
	
	
	
	private LocalDate expDate;

	public Integer getLimitApplied() {
		return limitApplied;
	}

	public void setLimitApplied(Integer limitApplied) {
		this.limitApplied = limitApplied;
	}

	public Integer getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public Float getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Float discountPercent) {
		this.discountPercent = discountPercent;
	}

	public LocalDate getExpDate() {
		return expDate;
	}

	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}
	
	
	

	

}
