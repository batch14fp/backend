package com.lawencon.community.post.pojo;

import java.util.List;

public class PojoPostReq {
	private String title;
	private String content;
	private String typeId;
	private PojoAttachmentPostInsertReq attachmentPost;
	private List<PojoPollingInsertReq>  pollingInsert;
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
	public PojoAttachmentPostInsertReq getAttachmentPost() {
		return attachmentPost;
	}
	public void setAttachmentPost(PojoAttachmentPostInsertReq attachmentPost) {
		this.attachmentPost = attachmentPost;
	}
	public List<PojoPollingInsertReq> getPollingInsert() {
		return pollingInsert;
	}
	public void setPollingInsert(List<PojoPollingInsertReq> pollingInsert) {
		this.pollingInsert = pollingInsert;
	}
}
