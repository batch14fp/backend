package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_post_type",
uniqueConstraints = {
        @UniqueConstraint(name = "type_code_bk", 
                columnNames = {"typeName" }
        )})
public class PostType  extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="polling_id")
	private Polling polling;
	
	@Column(length=5, nullable=false)
	private String typeCode;

	@Column(length=15, nullable=false)
	private String typeName;

	public Polling getPolling() {
		return polling;
	}

	public void setPolling(Polling polling) {
		this.polling = polling;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
	
	
	

}
