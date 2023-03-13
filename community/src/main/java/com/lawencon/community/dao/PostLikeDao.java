package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PostLike;

@Repository
public class PostLikeDao extends BaseMasterDao<PostLike> {

	@SuppressWarnings("unchecked")
	@Override
	public List<PostLike> getAll() {
		final String sql = "SELECT * FROM t_post_like WHERE  is_active = TRUE";
		final List<PostLike> res = ConnHandler.getManager().createNativeQuery(sql, PostLike.class).getResultList();

		return res;
	}

	@Override
	public Optional<PostLike> getById(String id) {
		return Optional.ofNullable(super.getById(PostLike.class, id));
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
	public List<PostLike> getByOffsetLimit(Long offset, Long limit) {
		
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT * FROM t_post_like pl ");
		sqlQuery.append("INNER JOIN t_post p ");
		sqlQuery.append("ON p.id = pl.post_id ");
		sqlQuery.append("INNER JOIN t_user u ");
		sqlQuery.append("ON pl.user_id = u.id ");
		sqlQuery.append(" WHERE pl.is_active = TRUE LIMIT :limit OFFSET :offset");

		final List<PostLike> res = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), PostLike.class)
				.setParameter("offset", offset).setParameter("limit", limit).getResultList();

		return res;
	}

	@Override
	public PostLike getByIdRef(String id) {
		return super.getByIdRef(PostLike.class, id);
	}

	@Override
	public Optional<PostLike> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PostLike.class, id));

	}

	public Long countPostLike(String userId, String postId) {
		final StringBuilder sql = new StringBuilder();
		Long count =null;
	
		sql.append("SELECT COUNT(id) FROM t_post_like ");
		sql.append("WHERE post_id = :postId ");
		sql.append("AND user_id = :userId ");	

		count= Long.valueOf(ConnHandler.getManager().createNativeQuery(sql.toString())
				.setParameter("userId", userId)
				.setParameter("postId",postId)
				.getSingleResult().toString());
		
	
	return count;	
		
	}
		

}
