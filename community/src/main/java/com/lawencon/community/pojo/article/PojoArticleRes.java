package com.lawencon.community.pojo.article;

import java.util.List;

public class PojoArticleRes {
	private List<PojoArticleResData> data;
	private Long total;

	public List<PojoArticleResData> getData() {
		return data;
	}

	public void setData(List<PojoArticleResData> data) {
		this.data = data;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
