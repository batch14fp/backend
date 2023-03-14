package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;




@Entity
@Table(name = "t_code_verification")
public class CodeVerification extends BaseEntity{
	@Column(length = 50, nullable = false)
	private String email;


	@Column(columnDefinition = "text", nullable = false)
	private String userPassword;


	@Column(length = 6, nullable = false)
	private String code;
	private LocalDateTime expiredAt;
	public String getEmail() {
		return email;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getExpiredAt() {
		return expiredAt;
	}
	public void setExpiredAt(LocalDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	} 

}
