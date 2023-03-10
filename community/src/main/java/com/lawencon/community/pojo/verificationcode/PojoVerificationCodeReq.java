package com.lawencon.community.pojo.verificationcode;

import java.time.LocalDateTime;

public class PojoVerificationCodeReq {
	private String email;
	private String password;
	private LocalDateTime expiredAt;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getExpiredAt() {
		return expiredAt;
	}
	public void setExpiredAt(LocalDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}
	
}
