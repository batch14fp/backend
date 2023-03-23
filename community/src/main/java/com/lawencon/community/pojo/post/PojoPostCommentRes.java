package com.lawencon.community.pojo.post;

import java.time.LocalDateTime;
import java.util.List;

public class PojoPostCommentRes {
	private String postCommentId;
	private String userId;
	private String fullname;
	private String contentComment;
	private LocalDateTime createdAt;
	private Integer ver;
	
	private List <PojoPostCommentReplyResData> data;
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
	public List<PojoPostCommentReplyResData> getData() {
		return data;
	}
	public void setData(List<PojoPostCommentReplyResData> data) {
		this.data = data;
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
	
	
}
