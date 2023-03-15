package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;




@Entity
@Table(name = "t_polling_respon")
public class PollingRespon  extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="polling_option_id", nullable=false)
	private PollingOption pollingOption;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	public PollingOption getPollingOption() {
		return pollingOption;
	}

	public void setPollingOption(PollingOption pollingOption) {
		this.pollingOption = pollingOption;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
