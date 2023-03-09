package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;




@Entity
@Table(name = "t_code_verification",
uniqueConstraints = {
        @UniqueConstraint(name = "verification_code_bk", 
                columnNames = {"code" }
        ),
        
        @UniqueConstraint(name = "email_bk", 
        columnNames = {"email" }
        )
		})
public class CodeVerification {
	@Column(length = 50, nullable = false)
	private String email;

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

}
