package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostLike;
import com.lawencon.community.model.User;

@Repository
public class PostLikeDao extends BaseMasterDao<PostLike> {

	@SuppressWarnings("unchecked")
	@Override
	public List<PostLike> getAll() {
		 StringBuilder sb = new StringBuilder();
		    sb.append("SELECT id, user_id, post_id ");
		    sb.append("FROM t_post_like ");

		    final List<Object[]> postLikeList =  ConnHandler
				    .getManager()
				    .createNativeQuery(sb.toString())
				    .getResultList();
		    final List<PostLike> result = new ArrayList<>();
		    for (Object[] obj : postLikeList) {
		        final PostLike postLike = new PostLike();
		        postLike.setId((String) obj[0]);

		        final User user = new User();
		        user.setId((String) obj[1]);
		        postLike.setUser(user);

		        final Post post = new Post();
		        post.setId((String) obj[2]);
		        postLike.setPost(post);

		        result.add(postLike);
		    }
		    return result;
	}
	
	
	public PostLike getByUserIdAndPostId(String userId, String postId) {
		 final StringBuilder sqlQuery = new StringBuilder();
	     final PostLike postLike = new PostLike();
		 try {
		 sqlQuery.append("SELECT id, user_id, post_id ");
		 sqlQuery.append("FROM t_post_like ");
		 sqlQuery.append("WHERE user_id = :userId ");
		 sqlQuery.append("AND post_id = :postId ");

		    final Object result =  ConnHandler
				    .getManager()
				    .createNativeQuery(sqlQuery.toString())
				    .setParameter("userId", userId)
				    .setParameter("postId", postId)
				    .getSingleResult();
		  final Object[] obj =(Object[]) result;
	
		        postLike.setId((String) obj[0]);

		        final User user = new User();
		        user.setId((String) obj[1]);
		        postLike.setUser(user);

		        final Post post = new Post();
		        post.setId((String) obj[2]);
		        postLike.setPost(post);
		 }catch(Exception e) {}
		    return postLike;
	}

	@SuppressWarnings("unchecked")
	public List<PostLike> getByIdPost(String postId) {
		 StringBuilder sb = new StringBuilder();
		    sb.append("SELECT id, user_id, post_id ");
		    sb.append("FROM t_post_like ");
		    sb.append("WHERE post_id = :postId ");
		    final List<PostLike> result = new ArrayList<>();
		    try {
		    final List<Object[]> postLikeList =  ConnHandler
				    .getManager()
				    
				    .createNativeQuery(sb.toString())
				    .setParameter("postId", postId)
				    .getResultList();
		
		    for (Object[] obj : postLikeList) {
		        final PostLike postLike = new PostLike();
		        postLike.setId((String) obj[0]);

		        final User user = new User();
		        user.setId((String) obj[1]);
		        postLike.setUser(user);

		        final Post post = new Post();
		        post.setId((String) obj[2]);
		        postLike.setPost(post);

		        result.add(postLike);
		    }
		    }catch(Exception e) {
		    	e.printStackTrace();
		    }
		    return result;
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
	List<PostLike> res = new ArrayList<>();
		try {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT * FROM t_post_like pl ");
		sqlQuery.append("INNER JOIN t_post p ");
		sqlQuery.append("ON p.id = pl.post_id ");
		sqlQuery.append("INNER JOIN t_user u ");
		sqlQuery.append("ON pl.user_id = u.id ");
		sqlQuery.append(" WHERE pl.is_active = TRUE LIMIT :limit OFFSET :offset");

		 res = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), PostLike.class)
				.setParameter("offset", offset).setParameter("limit", limit).getResultList();
		}
		catch(Exception e) {}
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

	public Long countPostLike( String postId) {
		final StringBuilder sql = new StringBuilder();
		Long count =null;
	
		sql.append("SELECT COUNT(id) FROM t_post_like ");
		sql.append("WHERE post_id = :postId ");	

		count= Long.valueOf(ConnHandler.getManager().createNativeQuery(sql.toString())
				.setParameter("postId",postId)
				.getSingleResult().toString());
		
	
	return count;	
		
	}
		
	@SuppressWarnings("unused")
	public Boolean getIsLike(String userId , String postId) {
		 Boolean data = false;
		 try {
		 final StringBuilder sqlQuery = new StringBuilder();
		 sqlQuery.append("SELECT id, user_id, post_id ");
		 sqlQuery.append("FROM t_post_like ");
		 sqlQuery.append("WHERE user_id= :userId ");
		 sqlQuery.append("AND post_id = :postId ");
		 sqlQuery.append("AND is_active = TRUE" );
	    
		    final Object result = 
	  		  ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	  		  .setParameter("userId", userId)
	  		  .setParameter("postId", postId)
	  		  .getSingleResult();

			data = true;
		 
		 }catch(final Exception e)
		 {}
			 
		 
		    
		    return data;
	}
	
	

}
