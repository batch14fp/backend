package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_category",
uniqueConstraints = {
        @UniqueConstraint(name = "category_code_bk", 
                columnNames = {"categoryCode" }
        )})
public class Category extends BaseEntity{
	
	
	@Column(length=25, nullable=false)
	private String categoryName ;
	
	@Column(length=5, nullable=false)
	private String categoryCode;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	
	
	
}
