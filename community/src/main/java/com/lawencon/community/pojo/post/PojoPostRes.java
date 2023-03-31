package com.lawencon.community.pojo.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PojoPostRes {
	private String id;
	private String userId;
	private String imageProfileId;
	private String pollingId;
	private String pollingResponId;
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

	private Boolean isLike;
	private Boolean isBookmark;
	private String titlePolling;
	private Boolean isVote;
	private Integer ver;
	private PojoPollingResponRes pollingRespon;
	private LocalDate endAt;
	private List<PojoPollingOptionRes> pollingOption;
	private List<PojoFileResData> data;
	private Boolean isMoreContent;
	

	public PojoPollingResponRes getPollingRespon() {
		return pollingRespon;
	}
	public void setPollingRespon(PojoPollingResponRes pollingRespon) {
		this.pollingRespon = pollingRespon;
	}
	public Boolean getIsLike() {
		return isLike;
	}
	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}
	public Boolean getIsBookmark() {
		return isBookmark;
	}
	public void setIsBookmark(Boolean isBookmark) {
		this.isBookmark = isBookmark;
	}
	public Boolean getIsVote() {
		return isVote;
	}
	public void setIsVote(Boolean isVote) {
		this.isVote = isVote;
	}
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
	public Boolean isLike() {
		return isLike;
	}
	public void setLike(Boolean isLike) {
		this.isLike = isLike;
	}
	public Boolean isBookmark() {
		return isBookmark;
	}
	public void setBookmark(Boolean isBookmark) {
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
	public LocalDate getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalDate endAt) {
		this.endAt = endAt;
	}
	public String getPollingResponId() {
		return pollingResponId;
	}
	public void setPollingResponId(String pollingResponId) {
		this.pollingResponId = pollingResponId;
	}
	public Boolean getIsMoreContent() {
		return isMoreContent;
	}
	public void setIsMoreContent(Boolean isMoreContent) {
		this.isMoreContent = isMoreContent;
	}
	
	
}