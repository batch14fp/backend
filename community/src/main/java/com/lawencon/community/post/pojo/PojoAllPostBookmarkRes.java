package com.lawencon.community.post.pojo;

public class PojoAllPostBookmarkRes {
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
	private String countPostLike;
	private String isLike;
	private String isBookmark;
	private PojoPollingOptionRes pollingOption;
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
	public String getCountPostLike() {
		return countPostLike;
	}
	public void setCountPostLike(String countPostLike) {
		this.countPostLike = countPostLike;
	}
	public String getIsLike() {
		return isLike;
	}
	public void setIsLike(String isLike) {
		this.isLike = isLike;
	}
	public String getIsBookmark() {
		return isBookmark;
	}
	public void setIsBookmark(String isBookmark) {
		this.isBookmark = isBookmark;
	}
	public PojoPollingOptionRes getPollingOption() {
		return pollingOption;
	}
	public void setPollingOption(PojoPollingOptionRes pollingOption) {
		this.pollingOption = pollingOption;
	}
}