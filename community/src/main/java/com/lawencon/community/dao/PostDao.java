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
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;

@Repository
public class PostDao extends AbstractJpaDao {

	public Optional<Post> getById(String id) {
		return Optional.ofNullable(super.getById(Post.class, id));
	}

	@SuppressWarnings("unchecked")
	public List<Post> getGetAllPost(int offset, int limit) {
		final StringBuilder sqlQuery = new StringBuilder();
		final List<Post> listPost = new ArrayList<>();

		sqlQuery.append(
				"SELECT p.id,p.category_id,c.category_code,c.category_name, p.post_type_id,pt.type_code,pt.type_name, p.user_id, pr.image_profile_id,p.title,p.content_post,pr.fullname,p.polling_id,ps.position_name, p.ver, p.is_active, p.created_at ");
		sqlQuery.append("FROM t_post p ");
		sqlQuery.append("INNER JOIN t_post_type pt ");
		sqlQuery.append("ON pt.id = p.post_type_id ");
		sqlQuery.append("INNER JOIN t_category c ");
		sqlQuery.append("ON p.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ");
		sqlQuery.append("ON u.id =  p.user_id ");
		sqlQuery.append("INNER JOIN t_profile pr ");
		sqlQuery.append("ON pr.id = u.profile_id  ");
		sqlQuery.append("INNER JOIN t_position ps ");
		sqlQuery.append("ON ps.id = pr.position_id ");
		sqlQuery.append("ORDER BY p.created_at DESC ");

		final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setMaxResults(limit)
				.setFirstResult((offset - 1) * limit).getResultList();
		try {
			for (final Object objs : result) {
				final Object[] obj = (Object[]) objs;
				final Post post = new Post();
				post.setId(obj[0].toString());

				final Category category = new Category();
				category.setId(obj[1].toString());
				category.setCategoryCode(obj[2].toString());
				category.setCategoryName(obj[3].toString());
				post.setCategory(category);

				final PostType postType = new PostType();
				postType.setId(obj[4].toString());
				postType.setTypeCode(obj[5].toString());
				postType.setTypeName(obj[6].toString());
				post.setPostType(postType);

				final User user = new User();
				user.setId(obj[7].toString());
				final Position position = new Position();
				position.setPositionName(obj[13].toString());		
				
				final Profile profile = new Profile();
				profile.setFullname(obj[11].toString());
				profile.setPosition(position);
				if(obj[8]!=null) {
				final File file = new File();
				file.setId(obj[8].toString());
				profile.setImageProfile(file);
				}
				user.setProfile(profile);
				post.setUser(user);

				post.setTitle(obj[9].toString());
				post.setContentPost(obj[10].toString());
				if (obj[12] != null) {
					final Polling polling = new Polling();
					polling.setId(obj[12].toString());
					post.setPolling(polling);
				}
				
				post.setVersion(Integer.valueOf(obj[14].toString()));
				post.setIsActive(Boolean.valueOf(obj[15].toString()));
				post.setCreatedAt(Timestamp.valueOf(obj[16].toString()).toLocalDateTime());
				listPost.add(post);
				
			}
		} catch (final Exception e) {
			e.printStackTrace();

		}

		return listPost;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Post> getGetAllPostByCriteria(int offset, int limit, String criteria, String userId) {
		final StringBuilder sqlQuery = new StringBuilder();
		final List<Post> listPost = new ArrayList<>();

		sqlQuery.append(
				"SELECT p.id,p.category_id,c.category_code,c.category_name, p.post_type_id,pt.type_code,pt.type_name, p.user_id, pr.image_profile_id,p.title,p.content_post,pr.fullname,p.polling_id,ps.position_name, p.ver, p.is_active, p.created_at ");
		sqlQuery.append("FROM t_post p ");
		
		if(criteria.equalsIgnoreCase("bookmark")) {
			sqlQuery.append("INNER JOIN t_post_bookmark pb ");
			sqlQuery.append("ON pb.post_id = p.id ");
		}
		else {
			sqlQuery.append("INNER JOIN t_post_like pl ");
			sqlQuery.append("ON pl.post_id = p.id ");
		}

		sqlQuery.append("INNER JOIN t_post_type pt ");
		sqlQuery.append("ON pt.id = p.post_type_id ");
		sqlQuery.append("INNER JOIN t_category c ");
		sqlQuery.append("ON p.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ");
		sqlQuery.append("ON u.id =  p.user_id ");
		sqlQuery.append("INNER JOIN t_profile pr ");
		sqlQuery.append("ON pr.id = u.profile_id  ");
		sqlQuery.append("INNER JOIN t_position ps ");
		sqlQuery.append("ON ps.id = pr.position_id ");
		
		if(criteria.equalsIgnoreCase("bookmark")) {
		sqlQuery.append(" WHERE pb.user_id = :userId ");
		}
		else {
			sqlQuery.append(" WHERE pl.user_id = :userId ");
		}
		sqlQuery.append("ORDER BY p.created_at DESC ");

		final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setMaxResults(limit)
				.setParameter("userId", userId)
				.setFirstResult((offset - 1) * limit).getResultList();
		try {
			for (final Object objs : result) {
				final Object[] obj = (Object[]) objs;
				final Post post = new Post();
				post.setId(obj[0].toString());

				final Category category = new Category();
				category.setId(obj[1].toString());
				category.setCategoryCode(obj[2].toString());
				category.setCategoryName(obj[3].toString());
				post.setCategory(category);

				final PostType postType = new PostType();
				postType.setId(obj[4].toString());
				postType.setTypeCode(obj[5].toString());
				postType.setTypeName(obj[6].toString());
				post.setPostType(postType);

				final User user = new User();
				user.setId(obj[7].toString());
				final Position position = new Position();
				position.setPositionName(obj[13].toString());		
				
				final Profile profile = new Profile();
				profile.setFullname(obj[11].toString());
				profile.setPosition(position);
				if(obj[8]!=null) {
				final File file = new File();
				file.setId(obj[8].toString());
				profile.setImageProfile(file);
				}
				user.setProfile(profile);
				post.setUser(user);

				post.setTitle(obj[9].toString());
				post.setContentPost(obj[10].toString());
				if (obj[12] != null) {
					final Polling polling = new Polling();
					polling.setId(obj[12].toString());
					post.setPolling(polling);
				}
				
				post.setVersion(Integer.valueOf(obj[14].toString()));
				post.setIsActive(Boolean.valueOf(obj[15].toString()));
				post.setCreatedAt(Timestamp.valueOf(obj[16].toString()).toLocalDateTime());
				listPost.add(post);
				
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return listPost;
	}
	    
	@SuppressWarnings("unchecked")
	public List<Post> getByUserId(final String userId, final int offset, final int limit) {
		final StringBuilder sqlQuery = new StringBuilder();
		final List<Post> listPost = new ArrayList<>();

		sqlQuery.append(
				"SELECT p.id,p.category_id,c.category_code,c.category_name, p.post_type_id,pt.type_code,pt.type_name, p.user_id, pr.image_profile_id,p.title,p.content_post,pr.fullname,p.polling_id,ps.position_name, p.ver, p.is_active, p.created_at ");
		sqlQuery.append("FROM t_post p ");
		sqlQuery.append("INNER JOIN t_post_type pt ");
		sqlQuery.append("ON pt.id = p.post_type_id ");
		sqlQuery.append("INNER JOIN t_category c ");
		sqlQuery.append("ON p.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ");
		sqlQuery.append("ON u.id =  p.user_id ");
		sqlQuery.append("INNER JOIN t_profile pr ");
		sqlQuery.append("ON pr.id = u.profile_id  ");
		sqlQuery.append("INNER JOIN t_position ps ");
		sqlQuery.append("ON ps.id = pr.position_id ");
		sqlQuery.append("WHERE p.user_id = :userId ");
		sqlQuery.append("ORDER BY p.created_at DESC ");

		final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("userId", userId).setMaxResults(limit).setFirstResult((offset - 1) * limit)
				.getResultList();
		try {
			for (final Object objs : result) {
				final Object[] obj = (Object[]) objs;
				final Post post = new Post();
				post.setId(obj[0].toString());

				final Category category = new Category();
				category.setId(obj[1].toString());
				category.setCategoryCode(obj[2].toString());
				category.setCategoryName(obj[3].toString());
				post.setCategory(category);

				final PostType postType = new PostType();
				postType.setId(obj[4].toString());
				postType.setTypeCode(obj[5].toString());
				postType.setTypeName(obj[6].toString());
				post.setPostType(postType);


				final User user = new User();
				user.setId(obj[7].toString());
				final Position position = new Position();
				position.setPositionName(obj[13].toString());		
				
				final Profile profile = new Profile();
				profile.setFullname(obj[11].toString());
				profile.setPosition(position);
				if(obj[8]!=null) {
				final File file = new File();
				file.setId(obj[8].toString());
				profile.setImageProfile(file);
				}
				user.setProfile(profile);
				post.setUser(user);

				post.setTitle(obj[9].toString());
				post.setContentPost(obj[10].toString());
				if (obj[12] != null) {
					final Polling polling = new Polling();
					polling.setId(obj[12].toString());
					post.setPolling(polling);
				}
				
				post.setVersion(Integer.valueOf(obj[14].toString()));
				post.setIsActive(Boolean.valueOf(obj[15].toString()));
				post.setCreatedAt(Timestamp.valueOf(obj[16].toString()).toLocalDateTime());
				listPost.add(post);
				
			}
		} catch (final Exception e) {
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
		int totalCount = Integer.valueOf(ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("userId", userId).getSingleResult().toString());
		return totalCount;
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByMostLikes(final int offset, final int limit) throws Exception {
		final List<Post> postList = new ArrayList<>();

		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(
				"SELECT p.id,p.category_id,c.category_code,c.category_name, p.post_type_id,pt.type_code,pt.type_name, p.user_id, pr.image_profile_id,p.title,p.content_post,pr.fullname,p.polling_id,ps.position_name, p.ver, p.is_active, p.created_at ");
		sqlQuery.append("FROM t_post p ");
		sqlQuery.append("INNER JOIN t_post_type pt ");
		sqlQuery.append("ON pt.id = p.post_type_id ");
		sqlQuery.append("INNER JOIN t_category c ");
		sqlQuery.append("ON p.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ");
		sqlQuery.append("ON u.id =  p.user_id ");
		sqlQuery.append("INNER JOIN t_profile pr ");
		sqlQuery.append("ON pr.id = u.profile_id  ");
		sqlQuery.append("INNER JOIN t_position ps ");
		sqlQuery.append("ON ps.id = pr.position_id ");
		sqlQuery.append("ORDER BY (SELECT COUNT(*) FROM t_post_like pl) DESC ");

		final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setMaxResults(limit)
				.setFirstResult((offset - 1) * limit).getResultList();
		try {
			for (final Object objs : result) {
				final Object[] obj = (Object[]) objs;
				final Post post = new Post();
				post.setId(obj[0].toString());

				final Category category = new Category();
				category.setId(obj[1].toString());
				category.setCategoryCode(obj[2].toString());
				category.setCategoryName(obj[3].toString());
				post.setCategory(category);

				final PostType postType = new PostType();
				postType.setId(obj[4].toString());
				postType.setTypeCode(obj[5].toString());
				postType.setTypeName(obj[6].toString());
				post.setPostType(postType);


				final User user = new User();
				user.setId(obj[7].toString());
				final Position position = new Position();
				position.setPositionName(obj[13].toString());		
				
				final Profile profile = new Profile();
				profile.setFullname(obj[11].toString());
				profile.setPosition(position);
				if(obj[8]!=null) {
				final File file = new File();
				file.setId(obj[8].toString());
				profile.setImageProfile(file);
				}
				user.setProfile(profile);
				post.setUser(user);

				post.setTitle(obj[9].toString());
				post.setContentPost(obj[10].toString());
				if (obj[12] != null) {
					final Polling polling = new Polling();
					polling.setId(obj[12].toString());
					post.setPolling(polling);
				}
				
				post.setVersion(Integer.valueOf(obj[14].toString()));
				post.setIsActive(Boolean.valueOf(obj[15].toString()));
				post.setCreatedAt(Timestamp.valueOf(obj[16].toString()).toLocalDateTime());
				postList.add(post);
				
			}
		} catch (final Exception e) {
			e.printStackTrace();

		}

		return postList;
	}

	public Post getByIdRef(String id) {
		return super.getByIdRef(Post.class, id);
	}

	public Optional<Post> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Post.class, id));

	}
	
	

	
	
	
	
	
	
	

}
