package com.lawencon.community.pojo.report;

import java.math.BigDecimal;

public class PojoResportIncomesAdminResData {

	private String memberName;
	private String type;
	private BigDecimal totalIncomes;

	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getTotalIncomes() {
		return totalIncomes;
	}
	public void setTotalIncomes(BigDecimal totalIncomes) {
		this.totalIncomes = totalIncomes;
	}
	
	
	
}
