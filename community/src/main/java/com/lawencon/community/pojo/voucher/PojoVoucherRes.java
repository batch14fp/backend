package com.lawencon.community.pojo.voucher;

public class PojoVoucherRes {
	private String voucherId;
	private String vourcherName;
	private Integer limitApplied;
	private Integer usedCount;
	private String voucherCode;
	private Float discountPercent;
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
}
