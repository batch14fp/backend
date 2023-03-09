package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.User;


@Repository
public class UserDao extends BaseMasterDao<User>{

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		final String sql = "SELECT * FROM t_user WHERE  is_active = TRUE";	
		final List<User> res = ConnHandler.getManager().createNativeQuery(sql, User.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<User> getById(String id) {
		return Optional.ofNullable(super.getById(User.class, id));
	}

	
	
	@Override
	public Optional<User> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(User.class, id));
	}
	
	
	@SuppressWarnings("unchecked")
	public Optional<User> login(String email){
		List<User> users = new ArrayList<>();
		try {
		
			StringBuilder sqlQuery = new StringBuilder();
			 sqlQuery.append("SELECT u.id, u.email, r.role_code, u.is_active FROM t_user u ");
			 sqlQuery.append("INNER JOIN t_role r ON r.id = u.role_id ");
			 sqlQuery.append("WHERE u.email= :email AND u.is_active = TRUE");
			 
			 users = ConnHandler.getManager().createNativeQuery((sqlQuery.toString()), User.class).setParameter("email", email).getResultList();
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(users.get(0));
		
		
	}
	
	
	
	
	
	
	
	
}
