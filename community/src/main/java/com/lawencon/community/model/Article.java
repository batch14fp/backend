package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_article")
public class Article  extends BaseEntity{
	
	
	@OneToOne
	@JoinColumn(name="file_id", nullable=false)
	private File file;

	@Column(length=50)
	private String title;
	

	private String contentArticle;


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContentArticle() {
		return contentArticle;
	}


	public void setContentArticle(String contentArticle) {
		this.contentArticle = contentArticle;
	}
	
	
}
