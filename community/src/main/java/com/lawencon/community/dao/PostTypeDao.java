package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseEntity;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PostType;


@Repository
public class PostTypeDao extends BaseMasterDao<PostType>{

	@SuppressWarnings("unchecked")
	@Override
	List<PostType> getAll() {
		final String sql = "SELECT * FROM t_post_type WHERE  is_active = TRUE";	
		final List<PostType> res = ConnHandler.getManager().createNativeQuery(sql, PostType.class).getResultList();
		
		return res;
	}

	@Override
	Optional<PostType> getById(Long id) {
		final PostType postType = ConnHandler.getManager().find(PostType.class, id);
		return Optional.ofNullable(postType);
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <PostType> boolean deleteById(Class<PostType> entityClass, Object entityId) {
		return super.deleteById(entityClass, entityId);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <PostType> PostType getByIdRef(Class<PostType> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <PostType extends BaseEntity> PostType save(PostType entity) {
		
		return super.save(entity);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <PostType extends BaseEntity> PostType saveAndFlush(PostType entity) {
		return super.saveAndFlush(entity);
	}
	
	
	
	
	

}
