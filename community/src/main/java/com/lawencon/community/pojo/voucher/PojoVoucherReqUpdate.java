package com.lawencon.community.pojo.voucher;

import java.time.LocalDate;

public class PojoVoucherReqUpdate {
	private String voucherId;
	private String vourcherName;
	private Integer limitApplied;
	private Integer usedCount;
	private String voucherCode;
	private Float discountPercent;
	private LocalDate expDate;
	private Boolean isActive;
	private Integer ver;
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public String getVourcherName() {
		return vourcherName;
	}
	public void setVourcherName(String vourcherName) {
		this.vourcherName = vourcherName;
	}
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public LocalDate getExpDate() {
		return expDate;
	}
	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	
	
	
	
}
