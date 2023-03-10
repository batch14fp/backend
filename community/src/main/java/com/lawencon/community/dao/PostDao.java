package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Post;


@Repository
public class PostDao extends BaseMasterDao<Post>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAll() {
		final String sql = "SELECT * FROM t_post WHERE  is_active = TRUE";	
		final List<Post> res = ConnHandler.getManager().createNativeQuery(sql, Post.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<Post> getById(String id) {
		return Optional.ofNullable(super.getById(Post.class, id));
	}


	@SuppressWarnings("unchecked")
	public List<Post> getByOffsetLimit(Long offset, Long limit) {
		  final String sql = "SELECT * FROM t_post WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
			
			final List<Post> res = ConnHandler.getManager().createNativeQuery(sql, Post.class)
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			
			return res;
	}

	@Override
	public Post getByIdRef(String id) {
		return super.getByIdRef(Post.class, id);
	}
	@Override
	public Optional<Post> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Post.class, id));

	}

}
