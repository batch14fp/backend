package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.VerifyToken;


@Repository
public class VerifyTokenDao extends BaseMasterDao<VerifyToken>{

	@SuppressWarnings("unchecked")
	@Override
	List<VerifyToken> getAll() {
		final String sql = "SELECT * FROM t_verify_token WHERE  is_active = TRUE";	
		final List<VerifyToken> res = ConnHandler.getManager().createNativeQuery(sql, VerifyToken.class).getResultList();
		
		return res;
	}

	@Override
	Optional<VerifyToken> getById(Long id) {
		return Optional.ofNullable(super.getById(VerifyToken.class, id));
	}

	@Override
	Optional<VerifyToken> getByIdRef(Long id) {
			return Optional.ofNullable(super.getByIdRef(VerifyToken.class, id));
	}
	
	
}
