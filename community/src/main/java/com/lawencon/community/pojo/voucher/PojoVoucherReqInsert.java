package com.lawencon.community.pojo.voucher;

import java.time.LocalDate;

public class PojoVoucherReqInsert {
	
	private String activityId;
	private Integer limitApplied;
	private Integer usedCount;
	private String voucherCode;
	private LocalDate expDate;	
	private Float discountPercent;
	

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
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public LocalDate getExpDate() {
		return expDate;
	}
	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}

	
}
