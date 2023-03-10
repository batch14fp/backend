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
	public List<PostComment> getAll() {
		final String sql = "SELECT * FROM t_post_comment WHERE  is_active = TRUE";	
		final List<PostComment> res = ConnHandler.getManager().createNativeQuery(sql, PostComment.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<PostComment> getById(String id) {
		return Optional.ofNullable(super.getById(PostComment.class, id));
	}
	


	@Override
	public Optional<PostComment> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(PostComment.class, id));
	}
	
	

	@SuppressWarnings("unchecked")
	public List<PostComment> getByOffsetLimit(Long offset, Long limit) {
	
			  final String sql = "SELECT * FROM t_post_comment WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
				
				final List<PostComment> res = ConnHandler.getManager().createNativeQuery(sql, PostComment.class)
						.setParameter("offset", offset)
						.setParameter("limit",limit)
						.getResultList();
				
				return res;
	}
	
	@Override
	public Optional<PostComment> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PostComment.class, id));

	}
	

}
