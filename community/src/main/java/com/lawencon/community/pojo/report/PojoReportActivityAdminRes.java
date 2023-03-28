package com.lawencon.community.pojo.report;

import java.util.List;

public class PojoReportActivityAdminRes {
	
	private List<PojoReportActivityAdminResData> data;
	private Long total;
	public List<PojoReportActivityAdminResData> getData() {
		return data;
	}
	public void setData(List<PojoReportActivityAdminResData> data) {
		this.data = data;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
}
