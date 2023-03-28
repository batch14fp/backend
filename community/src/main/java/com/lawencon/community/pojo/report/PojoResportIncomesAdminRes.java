package com.lawencon.community.pojo.report;

import java.util.List;

public class PojoResportIncomesAdminRes {
	private List<PojoResportIncomesAdminResData> data;
	private Long total;
	public List<PojoResportIncomesAdminResData> getData() {
		return data;
	}
	public void setData(List<PojoResportIncomesAdminResData> data) {
		this.data = data;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	

}
