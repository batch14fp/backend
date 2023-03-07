package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_post_bookmark",
uniqueConstraints = {
        @UniqueConstraint(name = "post_bookmark_ck", 
                columnNames = {"user_id", "post_id" }
        )})
public class PostBookmark extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@OneToOne
	@JoinColumn(name="post_id", nullable=false)
	private Post post;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
	
	

}
