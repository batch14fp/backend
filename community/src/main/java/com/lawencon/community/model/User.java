package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_user",
uniqueConstraints = {
        @UniqueConstraint(name = "verification_code_bk", 
                columnNames = {"verificationCode" }
        ),
        
        @UniqueConstraint(name = "email_bk", 
        columnNames = {"email" }
        )
		})


public class User extends BaseEntity{
	@OneToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	@OneToOne
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;
	
	@Column(length = 50, nullable = false)
	private String email;
	
	@Column(columnDefinition = "text", nullable=false)
	private String userPassword;
	
	@Column(length = 64, nullable=false)
	private String verificationCode;
	
	private Boolean isEnable;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	
}
