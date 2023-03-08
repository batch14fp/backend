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
		final VerifyToken verifyToken = ConnHandler.getManager().find(VerifyToken.class, id);
		return Optional.ofNullable(verifyToken);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <VerifyToken> VerifyToken getByIdRef(Class<VerifyToken> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}

}
