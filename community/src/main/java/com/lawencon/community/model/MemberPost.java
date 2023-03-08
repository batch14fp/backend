package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_member_post")
public class MemberPost  extends BaseEntity{

	@OneToOne
	@JoinColumn(name="member_id", nullable=false)
	private User member;
	
	

	@OneToOne
	@JoinColumn(name="post_id", nullable=false)
	private Post post;



	public User getMember() {
		return member;
	}



	public void setMember(User member) {
		this.member = member;
	}



	public Post getPost() {
		return post;
	}



	public void setPost(Post post) {
		this.post = post;
	}
	
	
	
	

}
