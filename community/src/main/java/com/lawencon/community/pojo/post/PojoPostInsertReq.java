package com.lawencon.community.pojo.post;

import java.util.List;

public class PojoPostInsertReq {
	private String title;
	private String content;
	private String typeId;
	private String imagePostId;
	private PojoAttachmentPostReqInsert attachmentPost;
	private List<PojoPollingReqInsert>  pollingInsert;

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
	public PojoAttachmentPostReqInsert getAttachmentPost() {
		return attachmentPost;
	}
	public void setAttachmentPost(PojoAttachmentPostReqInsert attachmentPost) {
		this.attachmentPost = attachmentPost;
	}
	public List<PojoPollingReqInsert> getPollingInsert() {
		return pollingInsert;
	}
	public void setPollingInsert(List<PojoPollingReqInsert> pollingInsert) {
		this.pollingInsert = pollingInsert;
	}
	public String getImagePostId() {
		return imagePostId;
	}
	public void setImagePostId(String imagePostId) {
		this.imagePostId = imagePostId;
	}

	
}
