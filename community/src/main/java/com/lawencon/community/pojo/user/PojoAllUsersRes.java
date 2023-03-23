package com.lawencon.community.pojo.user;

import java.util.List;

public class PojoAllUsersRes {
	private List<PojoAllUsersResData> data;
	private Long total;
	public List<PojoAllUsersResData> getData() {
		return data;
	}
	public void setData(List<PojoAllUsersResData> data) {
		this.data = data;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	

}
