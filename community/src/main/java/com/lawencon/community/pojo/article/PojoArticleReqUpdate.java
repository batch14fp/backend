package com.lawencon.community.pojo.article;

public class PojoArticleReqUpdate {
	private String articleId;
	private String title;
	private String content;
	private String imageArticle;
	private Integer ver;
	private Boolean isActive;
	
	
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	public String getImageArticle() {
		return imageArticle;
	}
	public void setImageArticle(String imageArticle) {
		this.imageArticle = imageArticle;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
}