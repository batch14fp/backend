package com.lawencon.community.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;


@Entity
@Table(name="t_activity")
public class Activity extends BaseEntity{
	
	
	@ManyToOne
	@JoinColumn(name="type_activity_id", nullable=false)
	private ActivityType typeActivity;
	
	

	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="category_id", nullable=false)
	private Category category;
		
	@OneToOne
	@JoinColumn(name="file_id")
	private File file;
	
	
	@Column(length=120, nullable= false)
	private String title;
	
	private BigDecimal price;
	
	@Column(length=36, nullable= false)
	private String provider;
	
	@Column(length=50,  nullable= false)
	private String activityLocation;

	
	
	@Column(nullable=false)
	private LocalDateTime startDate;
	
	@Column(nullable=false)
	private LocalDateTime endDate;
	
	@Column(length=50,  nullable= false)
	private String description;
	
	
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
