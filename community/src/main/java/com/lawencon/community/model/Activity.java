package com.lawencon.community.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;


@Entity
@Table(name="t_activity")
public class Activity extends BaseEntity{
	
	
	@ManyToOne
	@JoinColumn(name="type_activity_id", nullable=false)
	private ActivityType typeActivity;
	
	
	@ManyToOne
	@JoinColumn(name="category_id", nullable=false)
	private Category category;
		
	@ManyToOne
	@JoinColumn(name="file_id")
	private File file;
	
	
	@ManyToOne
	@JoinColumn(name="voucher_id", nullable=false)
	private Voucher voucher;
	
	
	@Column(length=50)
	private String title;
	
	private BigDecimal price;
	
	@Column(length=36)
	private String provider;
	
	@Column(length=50)
	private String activityLocation;

	
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime ;
	private LocalTime endTime;
	
	
	
	
	
	public ActivityType getTypeActivity() {
		return typeActivity;
	}
	public void setTypeActivity(ActivityType typeActivity) {
		this.typeActivity = typeActivity;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Voucher getVoucher() {
		return voucher;
	}
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getActivityLocation() {
		return activityLocation;
	}
	public void setActivityLocation(String activityLocation) {
		this.activityLocation = activityLocation;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
	


}
