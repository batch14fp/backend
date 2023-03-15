package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostComment;
import com.lawencon.community.model.User;


@Repository
public class PostCommentDao extends BaseMasterDao<PostComment>{

	@SuppressWarnings("unchecked")
	@Override
	public List<PostComment> getAll() throws Exception {
	    final StringBuilder sb = new StringBuilder();
	    sb.append("SELECT pc.comment_id, pc.user_id, pc.post_id, ");
	    sb.append("c.comment_id, c.user_id, c.post_id, c.created_at, ");
	    sb.append("p.fullname, ");
	    sb.append("p.post_content, p.created_at ");
	    sb.append("FROM t_post_comment pc ");
	    sb.append("LEFT JOIN t_post_comment c ON pc.comment_id = c.id ");
	    sb.append("INNER JOIN t_user u ON pc.user_id = u.user_id ");
	    sb.append("INNER JOIN t_profile pr ON u.profile_id = pr.id ");
	    sb.append("INNER JOIN t_post p ON pc.post_id = p.post_id ");

	    final List<Object[]> listObj = ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();
	    final List<PostComment> listResult = new ArrayList<>();
	    for (Object[] obj : listObj) {
	        final PostComment postComment = new PostComment();
	        postComment.setId((obj[0].toString()));
	        final User user = new User();
	        user.setId( obj[1].toString());
	        postComment.setUser(user);
	        
	        final Post post = new Post();
	        post.setId(obj[2].toString());
	        postComment.setPost(post );

	        if (obj[3] != null) {
	            final PostComment postCommentReply = new PostComment();
	            postCommentReply.setId((String) obj[3]);
	            final User userComment = new User();
	            userComment.setId( obj[4].toString());
		        
	            postCommentReply.setUser(userComment);
	            postCommentReply.setCreatedAt(Timestamp.valueOf( obj[5].toString()).toLocalDateTime());
		          
		   
	            //postComment.setCommentContent((String) obj[6]);
	             postComment.setComment(postCommentReply);
	        }

	        postComment.getUser().getProfile().setFullname((String) obj[6]);

	        postComment.getPost().setContentPost(obj[7].toString());
	        postComment.getPost().setCreatedAt((Timestamp.valueOf(obj[8].toString()).toLocalDateTime()));

	        listResult.add(postComment);
	    }
	    return listResult;
	}
	

	@Override
	public Optional<PostComment> getById(String id) {
		return Optional.ofNullable(super.getById(PostComment.class, id));
	}
	


	@Override
	public PostComment getByIdRef(String id) {
		return super.getByIdRef(PostComment.class, id);
	}
	
	
	
	@Override
	public Optional<PostComment> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PostComment.class, id));

	}
	
	
	public Long countPostComment(final String postId) {
		final StringBuilder sql = new StringBuilder();
		Long count =null;
	
		sql.append("SELECT COUNT(id) FROM t_post_comment ");
		sql.append("WHERE post_id = :postId ");
	

		count= Long.valueOf(ConnHandler.getManager().createNativeQuery(sql.toString())
	
				.setParameter("postId",postId)
				.getSingleResult().toString());
		
	
	return count;	
		
	}
	

}
