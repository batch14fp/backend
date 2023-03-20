package com.lawencon.community.pojo.article;

import java.util.List;

public class PojoArticleRes {
	private List<PojoArticleResData> data;
	private int total;
	public List<PojoArticleResData> getData() {
		return data;
	}
	public void setData(List<PojoArticleResData> data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
