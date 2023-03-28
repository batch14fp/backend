package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.MemberStatus;

@Repository
public class MemberStatusDao extends AbstractJpaDao{
	
	
	
	@SuppressWarnings("unchecked")
	public List<MemberStatus> getAll() {
		final List<MemberStatus> listMemberStatus = new ArrayList<>();
		
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT id, status_name, code_status, period_day, price, ver, is_active FROM t_member_status ");
		sqlQuery.append("WHERE is_active = TRUE ");
		
		final List <Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.getResultList();
		
		for(Object objs : result) {
		if(result!=null) {
			final MemberStatus memberStatus = new MemberStatus();
			final Object[] obj = (Object[]) objs;
			memberStatus.setId(obj[0].toString());
			memberStatus.setStatusName(obj[1].toString());
			memberStatus.setCodeStatus(obj[2].toString());
			memberStatus.setPeriodDay(Integer.valueOf(obj[3].toString()));
			memberStatus.setPrice(BigDecimal.valueOf(Long.valueOf(obj[4].toString())));
			memberStatus.setVersion(Integer.valueOf(obj[5].toString()));
			memberStatus.setIsActive(Boolean.valueOf(obj[6].toString()));
			listMemberStatus.add(memberStatus);
		}
		}
		
		return listMemberStatus;
		
	}
	
	public MemberStatus getByCode(final String statusCode) {
		final MemberStatus memberStatus = new MemberStatus();
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT id, status_name, code_status, period_day,price, ver, is_active FROM t_member_status ");
		sqlQuery.append("WHERE code_status = :statusCode ");
		sqlQuery.append("AND is_active = TRUE ");
		
		final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setParameter("statusCode", statusCode)
				.getSingleResult();
		if(memberStatus!=null) {
			final Object[] obj = (Object[]) result;
			memberStatus.setId(obj[0].toString());
			memberStatus.setCodeStatus(obj[1].toString());
			memberStatus.setStatusName(obj[2].toString());
			memberStatus.setPeriodDay(Integer.valueOf(obj[3].toString()));
			memberStatus.setPrice(BigDecimal.valueOf(Long.valueOf(obj[4].toString())));
			memberStatus.setVersion(Integer.valueOf(obj[5].toString()));
			memberStatus.setIsActive(Boolean.valueOf(obj[6].toString()));
		}
		
		
		return memberStatus;
		
	}
	
	
	public MemberStatus getByIdRef(String id) {
		return super.getByIdRef(MemberStatus.class, id);
	}

	

}
