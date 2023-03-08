package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_post_comment")
public class PostComment  extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="comment_id", nullable=false)
	private PostComment comment;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@OneToOne
	@JoinColumn(name="post_id", nullable=false)
	private Post post;

	public PostComment getComment() {
		return comment;
	}

	public void setComment(PostComment comment) {
		this.comment = comment;
	}

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
