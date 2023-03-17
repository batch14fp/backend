package com.lawencon.community.pojo.post;

import java.util.List;

public class PojoPostInsertReq {
	private String title;
	private String content;
	private String typeId;
	private String categoryId;
	private String imagePostId;
	private List<PojoAttachmentPostInsertReq> attachmentPost;
	private PojoPollingInsertReq  pollingInsert;

	
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

	public List<PojoAttachmentPostInsertReq> getAttachmentPost() {
		return attachmentPost;
	}
	public void setAttachmentPost(List<PojoAttachmentPostInsertReq> attachmentPost) {
		this.attachmentPost = attachmentPost;
	}
	public String getImagePostId() {
		return imagePostId;
	}
	public void setImagePostId(String imagePostId) {
		this.imagePostId = imagePostId;
	}
	public PojoPollingInsertReq getPollingInsert() {
		return pollingInsert;
	}
	public void setPollingInsert(PojoPollingInsertReq pollingInsert) {
		this.pollingInsert = pollingInsert;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	
	
	
}
