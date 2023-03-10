package com.lawencon.community.constant;

public enum RoleEnum {
	
	MEMBER("MMBR","MEMBER" ), ADMIN("ADMIN", "ADMIN"), SUPERADMIN("SPADM", "SUPER ADMIN"), SYSTEM("SYSTM", "SYSTEM");
	
	
	
	private final String roleName;
	private final String roleCode;
	
	RoleEnum(final String roleCode, final String roleName){
		this.roleCode = roleCode;
		this.roleName = roleName;
	}
	
	public String getRoleCode() {
		return this.roleCode;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	
	
	

}
