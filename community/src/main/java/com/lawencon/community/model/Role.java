package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;





@Entity
@Table(name = "t_role")
public class Role extends BaseEntity{
	@Column(unique = true, length = 5, nullable = false)
	private String roleCode;
	
	@Column(length = 15, nullable = false)
	private String roleName;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
