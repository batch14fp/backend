package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.CodeVerification;


@Repository
public class CodeVerificationDao extends BaseMasterDao<CodeVerification>{

	@SuppressWarnings("unchecked")
	@Override
	public List<CodeVerification> getAll() {
		final String sql = "SELECT * FROM t_code_verification";	
		final List<CodeVerification> res = ConnHandler.getManager().createNativeQuery(sql, CodeVerification.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<CodeVerification> getById(String id) {
		return Optional.ofNullable(super.getById(CodeVerification.class, id));
	}

	@Override
	public CodeVerification getByIdRef(String id) {
			return super.getByIdRef(CodeVerification.class, id);
	}
	
	@Override
	public Optional<CodeVerification> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(CodeVerification.class, id));

	}
	
	
	@SuppressWarnings("unchecked")
	public Optional<CodeVerification> getByCode(String code){
		try {
		List<CodeVerification> codeVerifications = new ArrayList<>();
		
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("SELECT * FROM t_code_verification c ");
			sqlQuery.append("WHERE c.code = :code ");
			sqlQuery.append("AND  NOW() <= c.expired_at ");

			codeVerifications = ConnHandler.getManager().createNativeQuery((sqlQuery.toString()), CodeVerification.class)
					.setParameter("code", code).getResultList();
			
			if(codeVerifications.size() == 0 ) {
				return Optional.ofNullable(null);
			}
			
			return Optional.ofNullable(codeVerifications.get(0));
		}  catch (Exception e) {
	        return Optional.empty();
	    }
	}
	
}
