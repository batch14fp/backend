package com.lawencon.community.pojo.activity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.lawencon.community.pojo.file.PojoFileReqInsert;

public class PojoActivityReqInsert {
	private String title;
	private String content;
	private String providers;
	private String typeId;
	private String activityLocation;
	private BigDecimal price;
	private String categoryId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private LocalDate endAt;
	private Integer limitApplied;
	private String voucherCode;
	private Float discountPercent;
	private Integer ver;
	private PojoFileReqInsert file;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProviders() {
		return providers;
	}
	public void setProviders(String providers) {
		this.providers = providers;
	}


	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getLimitApplied() {
		return limitApplied;
	}
	public void setLimitApplied(Integer limitApplied) {
		this.limitApplied = limitApplied;
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
	public LocalDate getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalDate endAt) {
		this.endAt = endAt;
	}
	public String getActivityLocation() {
		return activityLocation;
	}
	public void setActivityLocation(String activityLocation) {
		this.activityLocation = activityLocation;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public PojoFileReqInsert getFile() {
		return file;
	}
	public void setFile(PojoFileReqInsert file) {
		this.file = file;
	}
	
	
	
}
