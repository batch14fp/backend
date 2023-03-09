package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

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

		
	
	@Override
	Optional<Category> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(Category.class, id));
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
	
	@Override
	public Optional<Category> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Category.class, id));

	}
	
	
	

}
