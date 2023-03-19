package com.lawencon.community.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.File;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.file.PojoFileReqInsert;

@Service
public class FileService {
	private final FileDao fileDao;
	
	
	public FileService (final FileDao fileDao) {
		this.fileDao = fileDao;
	}
	
	public Optional<File> getById(String id) {
		final Optional <File> file =Optional.ofNullable(fileDao.getByIdRef(id));
		return file;
	}
	
	

	public PojoInsertRes save(PojoFileReqInsert data) {
		ConnHandler.begin();
		final File file = new File();
		file.setFileContent(data.getFileContent());
		file.setFileExtension(data.getExtension());
		file.setIsActive(true);
		final File fileNew = fileDao.save(file);
		ConnHandler.commit();
		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(fileNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
}



