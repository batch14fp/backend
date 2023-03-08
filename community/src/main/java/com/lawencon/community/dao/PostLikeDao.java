package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PostLike;



@Repository
public class PostLikeDao extends BaseMasterDao<PostLike>{

	@SuppressWarnings("unchecked")
	@Override
	List<PostLike> getAll() {
		final String sql = "SELECT * FROM t_post_like WHERE  is_active = TRUE";	
		final List<PostLike> res = ConnHandler.getManager().createNativeQuery(sql, PostLike.class).getResultList();
		
		return res;
	}

	@Override
	Optional<PostLike> getById(Long id) {
		final PostLike postLike = ConnHandler.getManager().find(PostLike.class, id);
		return Optional.ofNullable(postLike);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <PostLike> boolean deleteById(Class<PostLike> entityClass, Object entityId) {
		return super.deleteById(entityClass, entityId);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <PostLike> PostLike getByIdRef(Class<PostLike> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}


	@SuppressWarnings("unchecked")
	List<PostLike> getByOffsetLimit(Long offset, Long limit) {
		  final String sql = "SELECT * FROM t_post_like WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
			
			final List<PostLike> res = ConnHandler.getManager().createNativeQuery(sql, PostLike.class)
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			
			return res;
	}

}