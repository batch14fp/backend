package com.lawencon.community.pojo.article;

import java.util.List;

public class PojoResGetArticle {
	private List<PojoResGetArticleData> data;
	private int total;
	public List<PojoResGetArticleData> getData() {
		return data;
	}
	public void setData(List<PojoResGetArticleData> data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
