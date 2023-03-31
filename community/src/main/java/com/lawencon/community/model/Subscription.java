package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;



@Entity
@Table(name="t_subscription",
uniqueConstraints = {
        @UniqueConstraint(name = "subscription_ck", 
                columnNames = {"member_status_id","profile_id" }
        )})
public class Subscription extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="profile_id", nullable=false)
	private Profile profile;
	
	
	@OneToOne
	@JoinColumn(name = "member_status_id", nullable=false)
	private MemberStatus memberStatus;
	
	
	@Column(nullable=false)
	private LocalDateTime startDate;
	
	@Column(nullable=false)
	private LocalDateTime endDate;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public MemberStatus getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(MemberStatus memberStatus) {
		this.memberStatus = memberStatus;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	
	
	
	
	
	
	
	
	
	

}
