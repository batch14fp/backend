package com.lawencon.community.pojo.activity;

import java.time.LocalDateTime;

public class PojoUpcomingActivityByTypeResData {
	
	private String activityId;
	private LocalDateTime startDate;
	private String activityType;
	private String title;
	private Integer totalParticipant;
	
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getTotalParticipant() {
		return totalParticipant;
	}
	public void setTotalParticipant(Integer totalParticipant) {
		this.totalParticipant = totalParticipant;
	}
}
