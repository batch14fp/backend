package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.model.File;

public class FileDao extends BaseMasterDao<File>{

	@Override
	List<File> getAll() {
		return null;
	}

	@Override
	Optional<File> getById(String id) {
		return Optional.of(super.getById(File.class, id));
	}
	
	
	@Override
	Optional<File> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(File.class, id));
	}
	
	@Override
	public Optional<File> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(File.class, id));

	}

}
