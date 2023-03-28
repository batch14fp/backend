package com.lawencon.community.pojo.report;

import java.util.List;

public class PojoReportIncomesAdminRes {
	private List<PojoReportIncomesAdminResData> data;
	private Long total;
	public List<PojoReportIncomesAdminResData> getData() {
		return data;
	}
	public void setData(List<PojoReportIncomesAdminResData> data) {
		this.data = data;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	
}
