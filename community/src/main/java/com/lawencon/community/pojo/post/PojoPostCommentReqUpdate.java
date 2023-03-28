package com.lawencon.community.pojo.post;

public class PojoPostCommentReqUpdate {
	private String postCommentId;
	private String contentComment;
	
	private Integer ver;
	

	public String getPostCommentId() {
		return postCommentId;
	}
	public void setPostCommentId(String postCommentId) {
		this.postCommentId = postCommentId;
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
	
	
	
	
}
