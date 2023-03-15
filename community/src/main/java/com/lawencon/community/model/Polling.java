package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_polling")
public class Polling extends BaseEntity{
	
	@Column(length=120,nullable=false )
	private String title;
	
	
	
	@Column(nullable=false )
	private Boolean is_open;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getIs_open() {
		return is_open;
	}
	public void setIs_open(Boolean is_open) {
		this.is_open = is_open;
	}
	

	

}
