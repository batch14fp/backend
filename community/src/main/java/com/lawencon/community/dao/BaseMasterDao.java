package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.AbstractJpaDao;

public abstract class BaseMasterDao<T> extends AbstractJpaDao{
	abstract T insert(T data);

    abstract T update(T data);

    abstract List<T> getAll();

    abstract Boolean deleteById(Long id);

    abstract Optional<T> getById(Long id);
}
