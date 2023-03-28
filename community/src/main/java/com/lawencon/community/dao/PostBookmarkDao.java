package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostBookmark;
import com.lawencon.community.model.User;

@Repository
public class PostBookmarkDao extends BaseMasterDao<PostBookmark>{
	@SuppressWarnings("unchecked")
	@Override
	public List<PostBookmark> getAll() {
	    final StringBuilder sb = new StringBuilder();
	    sb.append("SELECT id, user_id, post_id ");
	    sb.append("FROM t_post_bookmark ");
	    sb.append("WHERE is_active = TRUE");

        List<PostBookmark> result = new ArrayList<>();
	    try {
	        final List<Object[]> postBookmarkList = 
	            ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();
	        
	        for (Object[] obj : postBookmarkList) {
	            PostBookmark postBookmark = new PostBookmark();
	            postBookmark.setId((String) obj[0]);
	            
	            final User user = new User();
	            user.setId((String) obj[1]);
	            postBookmark.setUser(user);
	            
	            final Post post = new Post();
	            post.setId((String) obj[2]);
	            postBookmark.setPost(post);

	            result.add(postBookmark);
	        }
	      
	    } catch (Exception e) {
	        System.err.println("Error occurred while retrieving PostBookmarks: " + e.getMessage());
	    }
	    return result;
	}
	
	public PostBookmark getByUserIdAndPostId(String userId, String postId) {
		 final StringBuilder sqlQuery = new StringBuilder();
		 final PostBookmark postBookmark = new PostBookmark();
		 try {
		 sqlQuery.append("SELECT id, user_id, post_id ");
		 sqlQuery.append("FROM t_post_bookmark ");
		 sqlQuery.append("WHERE user_id = :userId ");
		 sqlQuery.append("AND post_id = :postId ");

		    final Object result =  ConnHandler
				    .getManager()
				    .createNativeQuery(sqlQuery.toString())
				    .setParameter("userId", userId)
				    .setParameter("postId", postId)
				    .getSingleResult();
		  final Object[] obj =(Object[]) result;
	
		  postBookmark.setId((String) obj[0]);

		        final User user = new User();
		        user.setId((String) obj[1]);
		        postBookmark.setUser(user);

		        final Post post = new Post();
		        post.setId((String) obj[2]);
		        postBookmark.setPost(post);
		 }catch(Exception e) {}
		    return postBookmark;
	}
	@SuppressWarnings("unchecked")
	public List<PostBookmark> getByPostId(String postId) {
		   final StringBuilder sb = new StringBuilder();
		    sb.append("SELECT id, user_id, post_id ");
		    sb.append("FROM t_post_bookmark ");
		    sb.append("WHERE post_id = :postId");
	    
		    final List<Object[]> postBookmarkList = 
	  		  ConnHandler.getManager().createNativeQuery(sb.toString())
	  		  .setParameter("postId", postId)
	  		  .getResultList();
		    List<PostBookmark> result = new ArrayList<>();
		    for (Object[] obj : postBookmarkList) {
		        PostBookmark postBookmark = new PostBookmark();
		        postBookmark.setId((String) obj[0]);
		        
		        final User user = new User();
		        user.setId((String) obj[1]);
		        postBookmark.setUser(user);
		        
		        final Post post = new Post();
		        post.setId((String) obj[2]);
		        postBookmark.setPost(post);

		        result.add(postBookmark);
		    }
		    return result;
	}


	@SuppressWarnings("unused")
	public Boolean getIsBookmarkPost(String userId , String postId) {
		 final StringBuilder sqlQuery = new StringBuilder();
		 sqlQuery.append("SELECT id, user_id, post_id ");
		 sqlQuery.append("FROM t_post_bookmark ");
		 sqlQuery.append("WHERE user_id= :userId ");
		 sqlQuery.append("AND post_id = :postId ");
		 sqlQuery.append("AND is_active = TRUE" );
		 Object result = new Object();
		     try {
		    	 result = 
		    	  		  ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
		    	  		  .setParameter("userId", userId)
		    	  		  .setParameter("postId", postId)
		    	  		  .getSingleResult();
		    	 return true;
	        } catch (NoResultException e) {
	            return false;
	        }

	}
	
	
	

	@Override
	public Optional<PostBookmark> getById(String id) {
		return Optional.ofNullable(super.getById(PostBookmark.class, id));
	}
	
	

	
	@Override
	public PostBookmark getByIdRef(String id) {
		return super.getByIdRef(PostBookmark.class, id);
	}
	
	
	

	@Override
	public Optional<PostBookmark> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PostBookmark.class, id));

	}


	
}
