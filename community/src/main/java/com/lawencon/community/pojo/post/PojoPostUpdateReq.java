package com.lawencon.community.pojo.post;

public class PojoPostUpdateReq {
	private String postId;
	private String title;
	private String content;
	private String typeId;
	private String categoryId;
	private PojoAttachmentPostReqInsert attachmentPost;
	private PojoPollingInsertReq  pollingInsert;
	private String imagePostId;
	private Boolean isActive;
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setPollingInsert(PojoPollingInsertReq pollingInsert) {
		this.pollingInsert = pollingInsert;
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
	public PojoAttachmentPostReqInsert getAttachmentPost() {
		return attachmentPost;
	}
	public void setAttachmentPost(PojoAttachmentPostReqInsert attachmentPost) {
		this.attachmentPost = attachmentPost;
	}

	public PojoPollingInsertReq getPollingInsert() {
		return pollingInsert;
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

	
	
}
