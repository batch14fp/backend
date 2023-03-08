package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PostBookmark;
import com.lawencon.community.model.PostComment;

@Repository
public class PostBookmarkDao extends BaseMasterDao<PostBookmark>{
	@SuppressWarnings("unchecked")
	@Override
	List<PostBookmark> getAll() {
		final String sql = "SELECT * FROM t_post_bookmark WHERE  is_active = TRUE";	
		final List<PostBookmark> res = ConnHandler.getManager().createNativeQuery(sql, PostComment.class).getResultList();
		
		return res;
	}

	@Override
	Optional<PostBookmark> getById(Long id) {
	final PostBookmark postBookmark = ConnHandler.getManager().find(PostBookmark.class, id);
		return Optional.ofNullable(postBookmark);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <PostBookmark> boolean deleteById(Class<PostBookmark> entityClass, Object entityId) {
		return super.deleteById(entityClass, entityId);
	}	
	
	@SuppressWarnings("hiding")
	@Override
	public <PostBookmark> PostBookmark getByIdRef(Class<PostBookmark> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}

	
	@SuppressWarnings("unchecked")
	List<PostBookmark> getByOffsetLimit(Long offset, Long limit) {
		  final String sql = "SELECT * FROM t_post_bookmark WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
			
			final List<PostBookmark> res = ConnHandler.getManager().createNativeQuery(sql, PostBookmark.class)
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			
			return res;
	}
}