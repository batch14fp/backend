package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_activity_type",
uniqueConstraints = {
        @UniqueConstraint(name = "type_code_activity_bk", 
                columnNames = {"typeCode" }
        )})
public class ActivityType extends BaseEntity{
	
	@Column(length=50, nullable=false)
	private String activityName;
	
	@Column(length=5, nullable=false)
	private String typeCode;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	} 
	

}
