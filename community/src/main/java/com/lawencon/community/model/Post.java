package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_post")
public class Post  extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="category_id", nullable=false)
	private Category category;

	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user ;
	
	@ManyToOne
	@JoinColumn(name="post_type_id", nullable=false)
	private PostType postType;
	
	
	@Column(length=120)
	private String title;
	
	
	@OneToOne
	@JoinColumn(name="polling_id")
	private Polling polling;
	
	
	private String contentPost;


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}




	public PostType getPostType() {
		return postType;
	}


	public void setPostType(PostType postType) {
		this.postType = postType;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContentPost() {
		return contentPost;
	}


	public void setContentPost(String contentPost) {
		this.contentPost = contentPost;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Polling getPolling() {
		return polling;
	}


	public void setPolling(Polling polling) {
		this.polling = polling;
	}


	
	
	
	
	
	
}
