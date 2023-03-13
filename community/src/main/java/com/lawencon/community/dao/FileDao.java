package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.File;

@Repository
public class FileDao extends BaseMasterDao<File> {

	@SuppressWarnings("unchecked")
	@Override
	public List<File> getAll() {

		final String sql = "SELECT * FROM t_file";
		final List<File> res = ConnHandler.getManager().createNativeQuery(sql, File.class).getResultList();
		return res;
	}

	@Override
	public Optional<File> getById(String id) {
		return Optional.of(super.getById(File.class, id));
	}

	@Override
	public File getByIdRef(String id) {
		return super.getByIdRef(File.class, id);
	}

	@Override
	public Optional<File> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(File.class, id));

	}

}
