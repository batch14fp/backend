package com.lawencon.community.pojo.post;

public class PojoResGetPost {
	private String postId;
	private String userId;
	private String fullname;
	private String timeAgo;
	private String title;
	private String content;
	private String imgPostId;
	private String typeCode;
	private String typeName;
	private String categoryCode;
	private String categoryName;
	private int countPostLike;
	private int countPostComment;
	private boolean isLike;
	private boolean isBookmark;
	
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getTimeAgo() {
		return timeAgo;
	}
	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
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
	public String getImgPostId() {
		return imgPostId;
	}
	public void setImgPostId(String imgPostId) {
		this.imgPostId = imgPostId;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCountPostLike() {
		return countPostLike;
	}
	public void setCountPostLike(int countPostLike) {
		this.countPostLike = countPostLike;
	}
	public int getCountPostComment() {
		return countPostComment;
	}
	public void setCountPostComment(int countPostComment) {
		this.countPostComment = countPostComment;
	}
	public boolean isLike() {
		return isLike;
	}
	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}
	public boolean isBookmark() {
		return isBookmark;
	}
	public void setBookmark(boolean isBookmark) {
		this.isBookmark = isBookmark;
	}
	
}
