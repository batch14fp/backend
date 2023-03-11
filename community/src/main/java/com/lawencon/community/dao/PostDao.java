package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Post;


@Repository
public class PostDao extends BaseMasterDao<Post>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAll() {
		final String sql = "SELECT * FROM t_post WHERE  is_active = TRUE";	
		final List<Post> res = ConnHandler.getManager().createNativeQuery(sql, Post.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<Post> getById(String id) {
		return Optional.ofNullable(super.getById(Post.class, id));
	}


	@SuppressWarnings("unchecked")
	public List<Post> getByOffsetLimit(int offset, int limit) {
			final StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("SELECT * FROM t_post p");
			sqlQuery.append("INNER JOIN t_post_type pt ");
			sqlQuery.append("ON pt.id = p.post_type_id ");
			sqlQuery.append("INNER JOIN t_category c ");
			sqlQuery.append("ON p.category_id = c.id ");
			sqlQuery.append("WHERE is_active = TRUE ");
			sqlQuery.append("LIMIT :limit OFFSET :offset");
			final List<Post> res = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), Post.class)
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			
			return res;
	}

    public int getTotalCount() {
        final String sql = "SELECT COUNT(*) FROM t_post";
        int totalCount =(int) ConnHandler.getManager().createNativeQuery(sql, Post.class).getSingleResult();
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
