package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseEntity;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.User;


@Repository
public class UserDao extends BaseMasterDao<User>{

	@SuppressWarnings("unchecked")
	@Override
	List<User> getAll() {
		final String sql = "SELECT * FROM t_user WHERE  is_active = TRUE";	
		final List<User> res = ConnHandler.getManager().createNativeQuery(sql, User.class).getResultList();
		
		return res;
	}

	@Override
	Optional<User> getById(Long id) {
		final User user = ConnHandler.getManager().find(User.class, id);
		return Optional.ofNullable(user);
	}

	
	
	@SuppressWarnings("hiding")
	@Override
	public <User> User getByIdRef(Class<User> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}
	@SuppressWarnings("hiding")
	@Override
	public <User extends BaseEntity> User save(User entity) {

		return super.save(entity);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <User extends BaseEntity> User saveAndFlush(User entity) {
		return super.saveAndFlush(entity);
	}
	
	
	
}
