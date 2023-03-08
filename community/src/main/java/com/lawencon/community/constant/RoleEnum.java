package com.lawencon.community.constant;

public enum RoleEnum {
	
	MEMBER("MB","MEMBER" ), ADMIN("AD", "ADMIN"), SUPERADMIN("SA", "SUPER ADMIN"), SYSTEM("SY", "SYSTEM");
	
	
	
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
