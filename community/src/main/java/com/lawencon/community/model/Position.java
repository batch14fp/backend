package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;





@Entity
@Table(name = "t_position")
public class Position extends BaseEntity{
	@Column(length = 5, nullable = false)
	private String positionCode;
	
	@Column(length = 50, nullable = false)
	private String positionName;
	
	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	
}
