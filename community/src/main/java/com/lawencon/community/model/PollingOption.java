package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_polling_option")
public class PollingOption  extends BaseEntity{
	
	
	@OneToOne
	@JoinColumn(name="polling_id", nullable=false)
	private Polling polling;
	
	@Column(nullable=false)
	private String contentPolling;


	public Polling getPolling() {
		return polling;
	}


	public void setPolling(Polling polling) {
		this.polling = polling;
	}


	public String getContentPolling() {
		return contentPolling;
	}


	public void setContentPolling(String contentPolling) {
		this.contentPolling = contentPolling;
	}
	
	

}
