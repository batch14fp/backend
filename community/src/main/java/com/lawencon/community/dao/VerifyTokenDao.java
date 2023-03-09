package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.CodeVerification;


@Repository
public class VerifyTokenDao extends BaseMasterDao<CodeVerification>{

	@SuppressWarnings("unchecked")
	@Override
	List<CodeVerification> getAll() {
		final String sql = "SELECT * FROM t_verify_token WHERE  is_active = TRUE";	
		final List<CodeVerification> res = ConnHandler.getManager().createNativeQuery(sql, CodeVerification.class).getResultList();
		
		return res;
	}

	@Override
	Optional<CodeVerification> getById(String id) {
		return Optional.ofNullable(super.getById(CodeVerification.class, id));
	}

	@Override
	Optional<CodeVerification> getByIdRef(String id) {
			return Optional.ofNullable(super.getByIdRef(CodeVerification.class, id));
	}
	
	
}
