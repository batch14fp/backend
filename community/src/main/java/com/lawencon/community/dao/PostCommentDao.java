package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PostComment;


@Repository
public class PostCommentDao extends BaseMasterDao<PostComment>{

	@SuppressWarnings("unchecked")
	@Override
	List<PostComment> getAll() {
		final String sql = "SELECT * FROM t_post_comment WHERE  is_active = TRUE";	
		final List<PostComment> res = ConnHandler.getManager().createNativeQuery(sql, PostComment.class).getResultList();
		
		return res;
	}

	@Override
	Optional<PostComment> getById(Long id) {
	final PostComment postComment = ConnHandler.getManager().find(PostComment.class, id);
		return Optional.ofNullable(postComment);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <PostComment> boolean deleteById(Class<PostComment> entityClass, Object entityId) {
		return super.deleteById(entityClass, entityId);
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <PostComment> PostComment getByIdRef(Class<PostComment> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}


	@SuppressWarnings("unchecked")
	List<PostComment> getByOffsetLimit(Long offset, Long limit) {
	
			  final String sql = "SELECT * FROM t_post_comment WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
				
				final List<PostComment> res = ConnHandler.getManager().createNativeQuery(sql, PostComment.class)
						.setParameter("offset", offset)
						.setParameter("limit",limit)
						.getResultList();
				
				return res;
	}

}
