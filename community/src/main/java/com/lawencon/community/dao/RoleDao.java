package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Role;


@Repository
public class RoleDao extends AbstractJpaDao{
	
	@SuppressWarnings("unchecked")
	public Optional<Role> getRoleByCode(String roleCode) {
		List<Role> roles = new ArrayList<>();
		try {

			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("SELECT *  FROM t_role r ");
			sqlQuery.append("WHERE r.role_code = :roleCode ");
			sqlQuery.append("AND r.is_active = TRUE ");

			roles = ConnHandler.getManager().createNativeQuery((sqlQuery.toString()), Role.class)
					.setParameter("roleCode", roleCode).getResultList();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(roles.get(0));

	}
	

}
