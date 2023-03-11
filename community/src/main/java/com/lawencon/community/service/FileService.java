package com.lawencon.community.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.File;

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
}



