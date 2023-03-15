package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_polling")
public class Polling extends BaseEntity{
	
	@Column(length=120,nullable=false )
	private String title;
	private LocalDateTime endAt;
	@Column(nullable=false )
	private Boolean isOpen;
	


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
	public LocalDateTime getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}
	
	

}
