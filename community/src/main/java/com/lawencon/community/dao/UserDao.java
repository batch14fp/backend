package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Profile;
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
	public User getByIdRef(String id) {
		return super.getByIdRef(User.class, id);
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
		sqlQuery.append("SELECT * FROM t_user u ");
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
		sqlQuery.append("SELECT * FROM t_user u ");
		sqlQuery.append("INNER JOIN t_role r ");
		sqlQuery.append("ON r.id = u.role_id ");
		sqlQuery.append("INNER JOIN t_profile p ");
		sqlQuery.append("ON p.id = u.profile_id ");
		sqlQuery.append("WHERE r.role_code = :roleCode ");
		sqlQuery.append("AND u.is_active = TRUE ");
		final List<User> res = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), User.class)
				.setParameter("roleCode", roleCode).getResultList();
		return res;
	}

	public User getUserByProfileId(String id) {
		final StringBuilder sqlQuery = new StringBuilder();
		final User user = new User();
		sqlQuery.append("SELECT u.profile_id, u.id ");
		sqlQuery.append("FROM t_user u ");
		sqlQuery.append("INNER JOIN t_role r ");
		sqlQuery.append("ON r.id = u.role_id ");
		sqlQuery.append("INNER JOIN t_profile p ");
		sqlQuery.append("ON p.id = u.profile_id ");
		sqlQuery.append("WHERE u.profile_id = :id ");
		sqlQuery.append("AND u.is_active = TRUE ");
		
		final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("id", id)
				.getSingleResult();

		final Profile profile = new Profile();
		final Object[] obj = (Object[]) result;
		profile.setId(obj[0].toString());
		user.setProfile(profile);
		user.setId(obj[1].toString());

		return user;
	}
	
	

	public User getUserByEmail(String email) {
		final StringBuilder sqlQuery = new StringBuilder();
		final User user = new User();
		sqlQuery.append("SELECT u.profile_id, u.id, u.email, u.pass ");
		sqlQuery.append("FROM t_user u ");
		sqlQuery.append("WHERE u.email = :email ");
		sqlQuery.append("AND u.is_active = TRUE ");
		final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("email", email)
				.getSingleResult();

		final Profile profile = new Profile();
		final Object[] obj = (Object[]) result;
		profile.setId(obj[0].toString());
		user.setProfile(profile);
		user.setId(obj[1].toString());
		user.setEmail(obj[2].toString());
		user.setUserPassword(obj[3].toString());

		return user;
	}

	// ,p.position_id,p.image_profile_id,p.industry_id,p.fullname,p.company_name,dob,phone_number,country,city,province,postal_code,member_status_id

}
