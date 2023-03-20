package com.lawencon.community.pojo.post;

import java.util.List;

public class PojoPostReqUpdate {
	private String postId;
	private String title;
	private String content;
	private String typeId;
	private String filePostId;
	private String categoryId;
	private List<PojoAttachmentPostReqUpdate> attachmentPost;
	private PojoPollingReqUpdate  pollingUpdate;
	private String imagePostId;
	private Boolean isActive;

	
	
	public Boolean getIsActive() {
		return isActive;
	}

	private Integer ver;
	
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
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

	public List<PojoAttachmentPostReqUpdate> getAttachmentPost() {
		return attachmentPost;
	}
	public void setAttachmentPost(List<PojoAttachmentPostReqUpdate> attachmentPost) {
		this.attachmentPost = attachmentPost;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getImagePostId() {
		return imagePostId;
	}
	public void setImagePostId(String imagePostId) {
		this.imagePostId = imagePostId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getFilePostId() {
		return filePostId;
	}
	public void setFilePostId(String filePostId) {
		this.filePostId = filePostId;
	}
	public PojoPollingReqUpdate getPollingUpdate() {
		return pollingUpdate;
	}
	public void setPollingUpdate(PojoPollingReqUpdate pollingUpdate) {
		this.pollingUpdate = pollingUpdate;
	}

	
	
}
