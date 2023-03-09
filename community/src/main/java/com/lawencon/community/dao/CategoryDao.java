package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseEntity;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Category;


@Repository
public class CategoryDao extends BaseMasterDao<Category>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() {
		
	    final String sql = "SELECT * FROM t_category WHERE is_active = TRUE";
		
		final List<Category> res = ConnHandler.getManager().createNativeQuery(sql, Category.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<Category> getById(String id) {
	        return Optional.ofNullable(super.getById(Category.class, id));
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <Category> boolean deleteById(Class<Category> entityClass, Object entityId) {
		
		return super.deleteById(entityClass, entityId);
	}
		
	
	@Override
	Optional<Category> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(Category.class, id));
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <Category extends BaseEntity> Category save(Category entity) {
		return super.save(entity);
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <Category extends BaseEntity> Category saveAndFlush(Category entity) {
		return super.saveAndFlush(entity);
	}

	@SuppressWarnings("unchecked")

	List<Category> getByOffsetLimit(Long offset, Long limit) {
		
		
	    final String sql = "SELECT * FROM t_category WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
		
		final List<Category> res = ConnHandler.getManager().createNativeQuery(sql, Category.class)
				.setParameter("offset", offset)
				.setParameter("limit",limit)
				.getResultList();
		
		return res;
	
	}	
	
	
	

}
