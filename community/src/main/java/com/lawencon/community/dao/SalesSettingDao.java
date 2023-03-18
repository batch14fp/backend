package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.SalesSettings;

@Repository
public class SalesSettingDao extends AbstractJpaDao{
	
	public SalesSettings getSalesSetting() {
		final StringBuilder sqlQuery = new StringBuilder();
		final SalesSettings salesSetting = new SalesSettings();
		sqlQuery.append("SELECT tss.tax, tss.system_income, tss.member_income, tss.is_active, tss.ver ");
		sqlQuery.append("FROM t_sales_settings tss ");
		final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setMaxResults(1)
				.setFirstResult(0)
				.getSingleResult();
		final Object[] obj = (Object[]) result;
		salesSetting.setTax(Float.valueOf(obj[0].toString()));
		salesSetting.setSystemIncome(Float.valueOf(obj[1].toString()));
		salesSetting.setMemberIncome(Float.valueOf(obj[2].toString()));
		salesSetting.setIsActive(Boolean.valueOf(obj[3].toString()));
		salesSetting.setVersion(Integer.valueOf(obj[4].toString()));
		
	return salesSetting;
	}
	
	public SalesSettings getByIdRef(String id) {
		return super.getByIdRef(SalesSettings.class, id);
	}
	
	
	
	
	
}
