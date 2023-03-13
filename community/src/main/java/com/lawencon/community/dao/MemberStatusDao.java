package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.MemberStatus;

@Repository
public class MemberStatusDao extends AbstractJpaDao{
	
	public MemberStatus getByCode(final String statusCode) {
		final MemberStatus memberStatus = new MemberStatus();
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT id, status_name, status_code, period_day FROM t_member_status ");
		sqlQuery.append("WHERE status_code = status_code ");
		sqlQuery.append("AND is_active = TRUE ");
		
		final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setParameter("statusCode", statusCode)
				.getSingleResult();
		if(memberStatus!=null) {
			final Object[] obj = (Object[]) result;
			memberStatus.setId(obj[0].toString());
			memberStatus.setCodeStatus(obj[1].toString());
			memberStatus.setStatusName(obj[2].toString());
			memberStatus.setPeriodDay(Integer.valueOf(obj[3].toString()));
		}
		
		
		return memberStatus;
		
	}
	
	
	public MemberStatus getByIdRef(String id) {
		return super.getByIdRef(MemberStatus.class, id);
	}

	

}
