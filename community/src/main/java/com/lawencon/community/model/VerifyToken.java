package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;




@Entity
@Table(name = "t_verify_token")
public class VerifyToken {
	@Column(length = 50, nullable = false)
	private String email;

	@Column(length = 32, nullable = false)
	private String tokenVerify;
	private LocalDateTime expiredAt;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTokenVerify() {
		return tokenVerify;
	}
	public void setTokenVerify(String tokenVerify) {
		this.tokenVerify = tokenVerify;
	}
	public LocalDateTime getExpiredAt() {
		return expiredAt;
	}
	public void setExpiredAt(LocalDateTime expiredAt) {
		this.expiredAt = expiredAt;
	} 

}
