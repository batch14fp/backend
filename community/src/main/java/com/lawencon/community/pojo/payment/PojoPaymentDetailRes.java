package com.lawencon.community.pojo.payment;

import java.util.List;

public class PojoPaymentDetailRes {
	private List<PojoPaymentDetailResData> data;
	private Integer total;

	public List<PojoPaymentDetailResData> getData() {
		return data;
	}
	public void setData(List<PojoPaymentDetailResData> data) {
		this.data = data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
