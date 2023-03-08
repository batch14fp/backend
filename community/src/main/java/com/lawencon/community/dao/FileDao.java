package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.File;

public class FileDao extends BaseMasterDao<File>{

	@Override
	List<File> getAll() {
		return null;
	}

	@Override
	Optional<File> getById(Long id) {
		final File file = ConnHandler.getManager().find(File.class, id);
		return Optional.of(file);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <File> File getByIdRef(Class<File> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}

}
