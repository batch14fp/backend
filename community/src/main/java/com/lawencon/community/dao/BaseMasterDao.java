package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.AbstractJpaDao;

public abstract class BaseMasterDao<T> extends AbstractJpaDao {

	

	abstract List<T> getAll() throws Exception;

	abstract Optional<T> getById(String id);

	abstract T getByIdRef(String id);

	abstract Optional<T> getByIdAndDetach (String id);
	
	
}
