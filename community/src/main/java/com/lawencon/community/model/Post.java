package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name=" t_post")
public class Post  extends BaseEntity{
	
	
	@OneToOne
	@JoinColumn(name="category_id", nullable=false)
	private Category category;
	
	@OneToOne
	@JoinColumn(name="file_id", nullable=false)
	private File file;
	
	
	@OneToOne
	@JoinColumn(name="post_type_id", nullable=false)
	private PostType postType;
	
	
	@Column(length=50)
	private String title;
	
	
	private String contentPost;


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
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
	
	
	
	
}
