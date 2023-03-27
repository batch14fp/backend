package com.lawencon.community.pojo.post;

import java.util.List;

public class PojoPollingResponRes {

	private List<PojoOptionCountRes> data;
	private Integer totalOption;
	private Integer totalRespondents;
	
	public List<PojoOptionCountRes> getData() {
		return data;
	}
	public void setData(List<PojoOptionCountRes> data) {
		this.data = data;
	}
	public Integer getTotalRespondents() {
		return totalRespondents;
	}
	public void setTotalRespondents(Integer totalRespondents) {
		this.totalRespondents = totalRespondents;
	}
	public Integer getTotalOption() {
		return totalOption;
	}
	public void setTotalOption(Integer totalOption) {
		this.totalOption = totalOption;
	}
	
	
	
	
	
	

}
