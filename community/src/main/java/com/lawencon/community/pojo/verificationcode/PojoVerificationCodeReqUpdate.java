package com.lawencon.community.pojo.verificationcode;

import java.time.LocalDateTime;

public class PojoVerificationCodeReqUpdate {
	private String code;
	private LocalDateTime expiredAt;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LocalDateTime getExpiredAt() {
		return expiredAt;
	}
	public void setExpiredAt(LocalDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}
}
