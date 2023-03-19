package com.lawencon.community.pojo.post;

import java.util.List;

public class PojoPostReqInsert {
	private String title;
	private String content;
	private String typeId;
	private String categoryId;
	private List<PojoAttachmentPostReqInsert> attachmentPost;
	private PojoPollingReqInsert  pollingInsert;

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public List<PojoAttachmentPostReqInsert> getAttachmentPost() {
		return attachmentPost;
	}
	public void setAttachmentPost(List<PojoAttachmentPostReqInsert> attachmentPost) {
		this.attachmentPost = attachmentPost;
	}

	public PojoPollingReqInsert getPollingInsert() {
		return pollingInsert;
	}
	public void setPollingInsert(PojoPollingReqInsert pollingInsert) {
		this.pollingInsert = pollingInsert;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	
	
	
}
