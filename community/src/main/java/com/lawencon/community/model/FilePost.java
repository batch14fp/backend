package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_file_post",
uniqueConstraints = {
        @UniqueConstraint(name = "file_post_ck", 
        columnNames = {"file_id", "post_id" }
        )
        })
public class FilePost extends BaseEntity{

	@OneToOne
	@JoinColumn(name = "file_id", nullable=false)
	private File file;

	@OneToOne
	@JoinColumn(name = "post_id", nullable=false)
	private Post post;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
