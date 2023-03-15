package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;


@Repository
public class PostDao extends AbstractJpaDao{



	public Optional<Post> getById(String id) {
		return Optional.ofNullable(super.getById(Post.class, id));
	}


	@SuppressWarnings("unchecked")
	public List<Post> getGetAllPost(int offset, int limit) {
			final StringBuilder sqlQuery = new StringBuilder();
			final List<Post> listPost = new ArrayList<>();
			
			sqlQuery.append("SELECT p.id,p.category_id,c.category_code, p.file_id,p.post_type_id,pt.type_code, p.user_id,p.title,p.content_post,pr.fullname, p.ver, p.is_active, p.created_at ");
			sqlQuery.append("FROM t_post p ");
			sqlQuery.append("INNER JOIN t_post_type pt ");
			sqlQuery.append("ON pt.id = p.post_type_id ");
			sqlQuery.append("INNER JOIN t_category c ");
			sqlQuery.append("ON p.category_id = c.id ");
			sqlQuery.append("INNER JOIN t_user u ");
			sqlQuery.append("ON u.id =  p.user_id ");
			sqlQuery.append("INNER JOIN t_profile pr ");
			sqlQuery.append("ON pr.id = u.profile_id  ");
			sqlQuery.append("ORDER BY p.created_at DESC ");
			sqlQuery.append("LIMIT :limit OFFSET :offset ");
		
			final List<Object>result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			try {
				for(final Object objs : result) {
					final Object[] obj = (Object[]) objs;
					final Post post = new Post();
					post.setId(obj[0].toString());
					
					final Category category = new Category();
					category.setId(obj[1].toString());
					category.setCategoryCode(obj[2].toString());
					post.setCategory(category);
					
					if(obj[5].toString()!=null) {
						final File file = new File();
						file.setId(obj[3].toString());
						post.setFile(file);
						}
					final PostType postType = new PostType();
					postType.setId(obj[4].toString());
					postType.setTypeCode(obj[5].toString());
					post.setPostType(postType);
					
					
					final User user = new User();
					user.setId(obj[6].toString());
					
					final Profile profile = new Profile();
					profile.setFullname(obj[9].toString());
					user.setProfile(profile);
					post.setUser(user);
					
					post.setTitle(obj[7].toString());
					post.setContentPost(obj[8].toString());
					post.setVersion(Integer.valueOf(obj[10].toString()));
					post.setIsActive(Boolean.valueOf(obj[11].toString()));
					post.setCreatedAt(Timestamp.valueOf(obj[12].toString()).toLocalDateTime());
					listPost.add(post);
				}
			}catch(final Exception e){
				e.printStackTrace();
				
			}
			
			
			return listPost;
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> getByUserId(final String userId, final int offset, final int limit) {
		final StringBuilder sqlQuery = new StringBuilder();
		final List<Post> listPost = new ArrayList<>();
		
		sqlQuery.append("SELECT p.id,p.category_id,c.category_code, p.file_id,p.post_type_id,pt.type_code, p.user_id,p.title,p.content_post,pr.fullname, p.ver, p.is_active, p.created_at ");
		sqlQuery.append("FROM t_post p ");
		sqlQuery.append("INNER JOIN t_post_type pt ");
		sqlQuery.append("ON pt.id = p.post_type_id ");
		sqlQuery.append("INNER JOIN t_category c ");
		sqlQuery.append("ON p.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ");
		sqlQuery.append("ON u.id =  p.user_id ");
		sqlQuery.append("INNER JOIN t_profile pr ");
		sqlQuery.append("ON pr.id = u.profile_id  ");
		sqlQuery.append("WHERE p.user_id = :userId ");
		sqlQuery.append("ORDER BY p.created_at DESC ");
		sqlQuery.append("LIMIT :limit OFFSET :offset ");

		final List<Object>result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("userId", userId)
				.setParameter("offset", offset)
				.setParameter("limit",limit)
				.getResultList();
		try {
			for(final Object objs : result) {
				final Object[] obj = (Object[]) objs;
				final Post post = new Post();
				post.setId(obj[0].toString());
				
				final Category category = new Category();
				category.setId(obj[1].toString());
				category.setCategoryCode(obj[2].toString());
				post.setCategory(category);
				
				if(obj[5].toString()!=null) {
					final File file = new File();
					file.setId(obj[3].toString());
					post.setFile(file);
					}
				final PostType postType = new PostType();
				postType.setId(obj[4].toString());
				postType.setTypeCode(obj[5].toString());
				post.setPostType(postType);
				
				final User user = new User();
				user.setId(obj[6].toString());
				
				final Profile profile = new Profile();
				profile.setFullname(obj[9].toString());
				user.setProfile(profile);
				post.setUser(user);
				
				post.setTitle(obj[7].toString());
				post.setContentPost(obj[8].toString());
			
				post.setVersion(Integer.valueOf(obj[10].toString()));
				post.setIsActive(Boolean.valueOf(obj[11].toString()));
				post.setCreatedAt(Timestamp.valueOf(obj[12].toString()).toLocalDateTime());
				listPost.add(post);
			}
		}catch(final Exception e){
			e.printStackTrace();
			
		}
		
		
		return listPost;
}
	
	

    public int getTotalCount() {
        final String sql = "SELECT COUNT(id) as total FROM t_post";
        int totalCount = Integer.valueOf(ConnHandler.getManager().createNativeQuery(sql).getSingleResult().toString()); 
        return totalCount;
    }
    public int getByUserIdTotalCount(final String userId) {
    	final StringBuilder sqlQuery = new StringBuilder();
    	sqlQuery.append("SELECT COUNT(id) as total FROM t_post ");
    	sqlQuery.append("WHERE user_id = :userId");
        int totalCount = Integer.valueOf(ConnHandler.getManager()
        		.createNativeQuery(sqlQuery.toString())
        		.setParameter("userId", userId)
        		.getSingleResult().toString()); 
        return totalCount;
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Post> getPostsByMostLikes(final int offset, final int limit) throws Exception {
     final List<Post> postList = new ArrayList<>();

		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT p.id, p.title,p.category_id, p.content_post, c.category_name, c.category_code, p.post_type_id, pt.type_name, pt.type_code ");
		sqlQuery.append("FROM t_post p ");
		sqlQuery.append("JOIN t_category c ");
		sqlQuery.append("ON p.category_id = c.id ");
		sqlQuery.append("JOIN t_post_type pt ");
		sqlQuery.append("ON p.post_type_id = pt.id ");
		sqlQuery.append("ORDER BY (SELECT COUNT(*) FROM t_post_like pl) DESC ");
		sqlQuery.append("LIMIT :limit OFFSET :offset ");



		final List<Object> results =
				ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				
				.getResultList();

		
		for(final Object objs : results) {
		
		final Post post = new Post();
		final Object[] obj = (Object[]) objs;
		post.setId(obj[0].toString());
		post.setTitle(obj[1].toString());
		post.setContentPost(obj[2].toString());

		Category category = new Category();
		category.setId(obj[3].toString());
		category.setCategoryName(obj[4].toString());
		category.setCategoryCode(obj[5].toString());
		post.setCategory(category);

		PostType postType = new PostType();
		postType.setId(obj[6].toString());
		postType.setTypeName(obj[7].toString());
		postType.setTypeCode((String) obj[8]);
		post.setPostType(postType);
		
		postList.add(post);

		}
		return postList ;
	}
	
	

	public Post getByIdRef(String id) {
		return super.getByIdRef(Post.class, id);
	}
	
	public Optional<Post> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Post.class, id));

	}

}
