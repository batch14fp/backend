package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
<<<<<<< HEAD
@Table(name = "t_user",
uniqueConstraints = {
        @UniqueConstraint(name = "verification_code_bk", 
                columnNames = {"verificationCode" }
        ),
        
        @UniqueConstraint(name = "email_bk", 
        columnNames = {"email" }
        ),
        @UniqueConstraint(name = "user_role_ck", 
        columnNames = {"profile_id", "role_id" }
        )
		})
<<<<<<< HEAD


public class User extends BaseEntity{
=======
>>>>>>> 432a5377476b30480719d73696d04f3ac3ef73c5
=======
@Table(name = "t_user", uniqueConstraints = {
		@UniqueConstraint(name = "verification_code_bk", columnNames = { "verificationCode" }),
>>>>>>> 761927d4ee827b1cf2b8675e1cfdd490b62dfc62

		@UniqueConstraint(name = "email_bk", columnNames = { "email" }) })

public class User extends BaseEntity {
<<<<<<< HEAD
>>>>>>> 761927d4ee827b1cf2b8675e1cfdd490b62dfc62
=======
>>>>>>> 432a5377476b30480719d73696d04f3ac3ef73c5
	@OneToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@OneToOne
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;

	@Column(length = 50, nullable = false)
	private String email;

	@Column(columnDefinition = "text", nullable = false)
	private String userPassword;

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

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

}
