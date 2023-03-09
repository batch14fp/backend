package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.CodeVerification;
import com.lawencon.community.model.File;

public class FileDao extends BaseMasterDao<File> {

	@SuppressWarnings("unchecked")
	@Override
	public List<File> getAll() {

		final String sql = "SELECT * FROM t_file";
		final List<File> res = ConnHandler.getManager().createNativeQuery(sql, CodeVerification.class).getResultList();
		return res;
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
