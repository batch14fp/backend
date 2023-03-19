package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	    
		    final List<Object[]> postBookmarkList = 
	  		  ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();
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
		 Boolean data = false;
		 final StringBuilder sqlQuery = new StringBuilder();
		 sqlQuery.append("SELECT id, user_id, post_id ");
		 sqlQuery.append("FROM t_post_bookmark ");
		 sqlQuery.append("WHERE user_id= :userId ");
		 sqlQuery.append("AND post_id = :postId ");
		 sqlQuery.append("AND is_active = TRUE" );
	    
		    final Object obj = 
	  		  ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	  		  .setParameter("userId", userId)
	  		  .setParameter("postId", postId)
	  		  .getSingleResult();
		    if(obj!=null) {
		    	data = true;
		    }
		    return true;
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
