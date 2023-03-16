package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_post_type",
uniqueConstraints = {
        @UniqueConstraint(name = "type_code_bk", 
                columnNames = {"typeCode" }
        )})
public class PostType  extends BaseEntity{
	
	
	
	@Column(length=5, nullable=false)
	private String typeCode;

	@Column(length=15, nullable=false)
	private String typeName;

	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	
	
	
	
	

}
