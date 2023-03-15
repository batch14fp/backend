package com.lawencon.community.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_polling")
public class Polling extends BaseEntity{
	
	@Column(length=120,nullable=false )
	private String title;
	private LocalTime endAt;
	@Column(nullable=false )
	private Boolean isOpen;
	
	public LocalTime getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalTime endAt) {
		this.endAt = endAt;
	}
	

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	

}
