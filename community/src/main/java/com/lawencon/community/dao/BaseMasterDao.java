package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.AbstractJpaDao;

public abstract class BaseMasterDao<T> extends AbstractJpaDao{


    abstract List<T> getAll();
    abstract Optional<T> getById(Long id);
    abstract Optional<T> getByIdRef(Long id);
   
}
