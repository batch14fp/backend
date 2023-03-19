package com.lawencon.community.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Wallet;

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
	public List<User> getUserByRoleCode(final String roleCode) {
		final List<User> userList = new ArrayList<>();
		final StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append(
				"SELECT u.id as user_id, u.email, u.wallet_id, r.id as role_id, r.role_code, r.role_name, p.id as profile_id, p.fullname, p.company_name, p.dob, p.phone_number, p.country, p.city, p.province, p.postal_code, ps.id as member_status_id, ps.code_status, ps.status_name, i.id as industry_id, i.industry_name, p.position_id ");
		sqlQuery.append("FROM t_user u ");
		sqlQuery.append("INNER JOIN t_role r ON r.id = u.role_id ");
		sqlQuery.append("INNER JOIN t_profile p ON p.id = u.profile_id ");
		sqlQuery.append("INNER JOIN t_position po ON po.id = p.position_id ");
		sqlQuery.append("INNER JOIN t_member_status ps ON ps.id = p.member_status_id ");
		sqlQuery.append("INNER JOIN t_industry i ON i.id = p.industry_id ");
		sqlQuery.append("WHERE r.role_code = :roleCode AND u.is_active = TRUE");

		final List<Object[]> resultList = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("roleCode", roleCode).getResultList();

		for (Object[] obj : resultList) {

			final User user = new User();
			user.setId(obj[0].toString());
			user.setEmail(obj[1].toString());

			if (String.valueOf(obj[2])!= null) {
				final Wallet wallet = new Wallet();
				wallet.setId(String.valueOf(obj[2]));
				user.setWallet(wallet);
			}
			final Role role = new Role();
			role.setId((obj[3]).toString());
			role.setRoleCode(obj[4].toString());
			role.setRoleName(obj[5].toString());

			
			final Profile profile = new Profile();
			profile.setId(obj[6].toString());
			profile.setFullname(obj[7].toString());
			profile.setCompanyName(obj[8].toString());
			profile.setDob(Date.valueOf(obj[9].toString()).toLocalDate());
			profile.setPhoneNumber(obj[10].toString());
			profile.setCountry(obj[11].toString());
			profile.setCity(obj[12].toString());
			profile.setProvince(obj[13].toString());
			profile.setPostalCode(obj[14].toString());

			final MemberStatus memberStatus = new MemberStatus();
			memberStatus.setId(obj[15].toString());
			memberStatus.setCodeStatus(obj[16].toString());
			memberStatus.setStatusName(obj[17].toString());

			final Industry industry = new Industry();
			industry.setId(obj[18].toString());
			industry.setIndustryName(obj[19].toString());

			profile.setMemberStatus(memberStatus);

			final Position position = new Position();
			position.setId(obj[20].toString());
			profile.setPosition(position);
			profile.setIndustry(industry);

			user.setRole(role);
			user.setProfile(profile);

			userList.add(user);
		}
		return userList;
	}

	public User getUserByProfileId(String id) {
		final StringBuilder sqlQuery = new StringBuilder();
		final User user = new User();
		sqlQuery.append(
				"SELECT u.id as user_id, u.email, u.wallet_id, r.id as role_id, r.role_code, r.role_name, p.id as profile_id, p.fullname, p.company_name, p.dob, p.phone_number, p.country, p.city, p.province, p.postal_code, ps.id as member_status_id, ps.code_status, ps.status_name, i.id as industry_id, i.industry_name, p.position_id ");
		sqlQuery.append("FROM t_user u ");
		sqlQuery.append("INNER JOIN t_role r ON r.id = u.role_id ");
		sqlQuery.append("INNER JOIN t_profile p ON p.id = u.profile_id ");
		sqlQuery.append("INNER JOIN t_position po ON po.id = p.position_id ");
		sqlQuery.append("INNER JOIN t_member_status ps ON ps.id = p.member_status_id ");
		sqlQuery.append("INNER JOIN t_industry i ON i.id = p.industry_id ");
		sqlQuery.append("WHERE r.role_code = :roleCode AND u.is_active = TRUE");

		final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setParameter("id", id)
				.getSingleResult();

		final Profile profile = new Profile();
		final Object[] obj = (Object[]) result;
		user.setId(obj[0].toString());
		user.setEmail(obj[1].toString());

		if (obj[2].toString()!= null) {
			final Wallet wallet = new Wallet();
			wallet.setId(obj[2].toString());
			user.setWallet(wallet);
		}
		final Role role = new Role();
		role.setId((obj[3]).toString());
		role.setRoleCode(obj[4].toString());
		role.setRoleName(obj[5].toString());

		
		profile.setId(obj[6].toString());
		profile.setFullname(obj[7].toString());
		profile.setCompanyName(obj[8].toString());
		profile.setDob(Date.valueOf(obj[9].toString()).toLocalDate());
		profile.setPhoneNumber(obj[10].toString());
		profile.setCountry(obj[11].toString());
		profile.setCity(obj[12].toString());
		profile.setProvince(obj[13].toString());
		profile.setPostalCode(obj[14].toString());

		final MemberStatus memberStatus = new MemberStatus();
		memberStatus.setId(obj[15].toString());
		memberStatus.setCodeStatus(obj[16].toString());
		memberStatus.setStatusName(obj[17].toString());

		final Industry industry = new Industry();
		industry.setId(obj[18].toString());
		industry.setIndustryName(obj[19].toString());

		profile.setMemberStatus(memberStatus);

		final Position position = new Position();
		position.setId(obj[20].toString());
		profile.setPosition(position);
		profile.setIndustry(industry);

		user.setRole(role);
		user.setProfile(profile);


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
				.setParameter("email", email).getSingleResult();

		final Profile profile = new Profile();
		final Object[] obj = (Object[]) result;
		profile.setId(obj[0].toString());
		user.setProfile(profile);
		user.setId(obj[1].toString());
		user.setEmail(obj[2].toString());
		user.setUserPassword(obj[3].toString());

		return user;
	}

}
