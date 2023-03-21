package com.lawencon.community.pojo.post;

import java.time.LocalDateTime;
import java.util.List;

public class PojoPostRes {
	private String id;
	private String userId;
	private String imageProfileId;
	private String pollingId;
	private String fullname;
	private String position;
	private LocalDateTime timeAgo;
	private String title;
	private String content;
	private String typeCode;
	private String typeName;
	private String categoryCode;
	private String categoryName;
	private Long countPostLike;
	private Long countPostComment;

	private boolean isLike;
	private boolean isBookmark;
	private String titlePolling;
	private Integer ver;
	private List<PojoPollingOptionRes> pollingOption;
	private List<PojoFileResData> data;
	

	public List<PojoFileResData> getData() {
		return data;
	}
	public void setData(List<PojoFileResData> data) {
		this.data = data;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public LocalDateTime getTimeAgo() {
		return timeAgo;
	}
	public void setTimeAgo(LocalDateTime timeAgo) {
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
	

	public Long getCountPostLike() {
		return countPostLike;
	}
	public void setCountPostLike(Long countPostLike) {
		this.countPostLike = countPostLike;
	}
	
	public Long getCountPostComment() {
		return countPostComment;
	}
	public void setCountPostComment(Long countPostComment) {
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
	public String getTitlePolling() {
		return titlePolling;
	}
	public void setTitlePolling(String titlePolling) {
		this.titlePolling = titlePolling;
	}


	public String getPollingId() {
		return pollingId;
	}
	public void setPollingId(String pollingId) {
		this.pollingId = pollingId;
	}
	public List<PojoPollingOptionRes> getPollingOption() {
		return pollingOption;
	}
	public void setPollingOption(List<PojoPollingOptionRes> pollingOption) {
		this.pollingOption = pollingOption;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
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