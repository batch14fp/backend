package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PostType;


@Repository
public class PostTypeDao extends BaseMasterDao<PostType>{

	@SuppressWarnings("unchecked")
	@Override
	public List<PostType> getAll() {
		final String sql = "SELECT * FROM t_post_type WHERE  is_active = TRUE";	
		final List<PostType> res = ConnHandler.getManager().createNativeQuery(sql, PostType.class).getResultList();
		
		return res;
	}

	@Override
	Optional<PostType> getById(String id) {
		return Optional.ofNullable(super.getById(PostType.class, id));
	}

	
	@Override
	Optional<PostType> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(PostType.class, id));
	}
	
	@Override
	public Optional<PostType> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PostType.class, id));

	}
	

	
	
	

}
