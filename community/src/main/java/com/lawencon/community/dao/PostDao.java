package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;


@Repository
public class PostDao extends BaseMasterDao<Post>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAll() {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT * FROM t_post p ");
		sqlQuery.append("INNER JOIN t_post_type pt ");
		sqlQuery.append("ON pt.id = p.post_type_id ");
		sqlQuery.append("INNER JOIN t_category c ");
		sqlQuery.append("ON p.category_id = c.id ");
		final List<Post> res = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), Post.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<Post> getById(String id) {
		return Optional.ofNullable(super.getById(Post.class, id));
	}


	@SuppressWarnings("unchecked")
	public List<Post> getByOffsetLimit(int offset, int limit) {
			final StringBuilder sqlQuery = new StringBuilder();
			final List<Post> listPost = new ArrayList<>();
			
			sqlQuery.append("SELECT p.id,p.category_id,c.category_code, p.file_id,p.post_type_id,pt.type_code, p.user_id,p.title,p.end_at,p.content_post, p.ver, p.is_active ");
			sqlQuery.append("FROM t_post p ");
			sqlQuery.append("INNER JOIN t_post_type pt ");
			sqlQuery.append("ON pt.id = p.post_type_id ");
			sqlQuery.append("INNER JOIN t_category c ");
			sqlQuery.append("ON p.category_id = c.id ");
			sqlQuery.append("LIMIT :limit OFFSET :offset ");
			sqlQuery.append("ORDER BY p.created_at DESC");
			final List<Object>result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), Post.class)
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
					post.setUser(user);
					
					post.setTitle(obj[7].toString());
					post.setEndAt(Timestamp.valueOf(obj[8].toString()).toLocalDateTime().toLocalTime());
					post.setContentPost(obj[9].toString());
					post.setVersion(Integer.valueOf(obj[10].toString()));
					post.setIsActive(Boolean.valueOf(obj[11].toString()));post.setIsActive(Boolean.valueOf(obj[0].toString()));
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
	
	
	@Override
	public Post getByIdRef(String id) {
		return super.getByIdRef(Post.class, id);
	}
	@Override
	public Optional<Post> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Post.class, id));

	}

}
