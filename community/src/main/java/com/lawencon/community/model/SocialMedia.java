package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;



@Entity
@Table(name = "t_social_media")
public class SocialMedia extends BaseEntity{
	@Column(length = 50, nullable = false)
	private String platformName;
	
	@OneToOne
	@JoinColumn(name="file_id")
	private File file;

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	

	
	
}
