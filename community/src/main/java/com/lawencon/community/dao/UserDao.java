package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.User;

@Repository
public class UserDao extends BaseMasterDao<User> {

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
	public Optional<User> login(String email) {
		List<User> users = new ArrayList<>();
		try {

			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("SELECT *  FROM t_user u ");
			sqlQuery.append("INNER JOIN t_role r ");
			sqlQuery.append("ON r.id = u.role_id ");
			sqlQuery.append("WHERE u.email= :email ");
			sqlQuery.append("AND u.is_active = TRUE ");

			users = ConnHandler.getManager().createNativeQuery((sqlQuery.toString()), User.class)
					.setParameter("email", email).getResultList();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(users.get(0));

	}

	@Override
	public Optional<User> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(User.class, id));

	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUserByRoleId(String id) {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT * FROM t_user u");
		sqlQuery.append("INNER JOIN t_role r ");
		sqlQuery.append("ON r.id = u.role_id ");
		sqlQuery.append("INNER JOIN t_profile p ");
		sqlQuery.append("ON p.id = u.profile_id ");
		sqlQuery.append("WHERE u.id = :id ");
		sqlQuery.append("AND u.is_active = TRUE ");
		final List<User> res = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), User.class)
				.setParameter("id", id).getResultList();
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserByRoleCode(String roleCode) {

		
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT * FROM t_user u");
		sqlQuery.append("INNER JOIN t_role r ");
		sqlQuery.append("ON r.id = u.role_id ");
		sqlQuery.append("INNER JOIN t_profile p ");
		sqlQuery.append("ON p.id = u.profile_id ");
		sqlQuery.append("WHERE u.role_code = :roleCode ");
		sqlQuery.append("AND u.is_active = TRUE ");
		final List<User> res = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), User.class)
				.setParameter("roleCode",roleCode)
				.getResultList();
		return res;
	}

	

}
