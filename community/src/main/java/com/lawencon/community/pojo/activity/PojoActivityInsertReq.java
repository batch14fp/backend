package com.lawencon.community.pojo.activity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PojoActivityInsertReq {
	private String timeAgo;
	private String title;
	private String content;
	private String providers;
	private String typeId;
	private String imgActivityId;
	private BigDecimal price;
	private String categoryId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private LocalDate endAt;
	private String vourcherName;
	private Integer limitApplied;
	private Integer usedCount;
	private String voucherCode;
	private Float discountPercent;

	private Boolean isActive;
	
	
	public String getTimeAgo() {
		return timeAgo;
	}
	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}
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

	public String getImgActivityId() {
		return imgActivityId;
	}
	public void setImgActivityId(String imgActivityId) {
		this.imgActivityId = imgActivityId;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	public LocalDate getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalDate endAt) {
		this.endAt = endAt;
	}
	
	
	
}
