package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;




@Entity
@Table(name="t_Article_viewer",
uniqueConstraints = {
        @UniqueConstraint(name = "article_viewer_ck", 
                columnNames = {"user_id", "article_id" }
        )})
public class ArticleViewer extends BaseEntity{
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@OneToOne
	@JoinColumn(name="article_id", nullable=false)
	private Article article;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}


}
