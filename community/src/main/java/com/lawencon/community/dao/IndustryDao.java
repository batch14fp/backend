package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Industry;

@Repository
public class IndustryDao extends BaseMasterDao<Industry>{

	@SuppressWarnings("unchecked")
	@Override
	List<Industry> getAll() {
		final String sql = "SELECT * FROM t_industry WHERE  is_active = TRUE";	
		final List<Industry> res = ConnHandler.getManager().createNativeQuery(sql, Industry.class).getResultList();
		
		return res;
	}

	@Override
	Optional<Industry> getById(String id) {
		return Optional.ofNullable(super.getById(Industry.class, id));
	}
	
	@Override
	Optional<Industry> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(Industry.class, id));
	}

	@SuppressWarnings("unchecked")
	List<Industry> getByOffsetLimit(Long offset, Long limit) {
		  final String sql = "SELECT * FROM t_industry WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
			
			final List<Industry> res = ConnHandler.getManager().createNativeQuery(sql, Industry.class)
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			
			return res;
	}
	@Override
	public Optional<Industry> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Industry.class, id));

	}
	
	
	
	
	
	
	
	

}
