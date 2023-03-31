package com.lawencon.community.pojo.post;

import java.time.LocalDateTime;

public class PojoPostCommentRes {
	private String postCommentId;
	private String userId;
	private String imageProfileId;
	private String position;	
	private String fullname;
	private String contentComment;
	private LocalDateTime createdAt;
	private Integer ver;
	
	public String getPostCommentId() {
		return postCommentId;
	}
	public void setPostCommentId(String postCommentId) {
		this.postCommentId = postCommentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContentComment() {
		return contentComment;
	}
	public void setContentComment(String contentComment) {
		this.contentComment = contentComment;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getImageProfileId() {
		return imageProfileId;
	}
	public void setImageProfileId(String imageProfileId) {
		this.imageProfileId = imageProfileId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
}
