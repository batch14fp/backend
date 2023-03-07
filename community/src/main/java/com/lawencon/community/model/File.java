package com.lawencon.community.model;

import javax.persistence.Column;

public class File {
	@Column(columnDefinition = "text")
	private String fileContent;
	
	@Column(length = 5)
	private String fileExtension;

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	

}
