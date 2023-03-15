package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.community.dao.SalesSettingDao;
import com.lawencon.community.model.SalesSettings;
import com.lawencon.community.pojo.salessetting.PojoResGetSalesSetting;

@Service
public class SalesSettingService {
	private SalesSettingDao salesSettingDao;
	
	public SalesSettingService(final SalesSettingDao salesSettingDao) {
		this.salesSettingDao = salesSettingDao;
	}
	
	public PojoResGetSalesSetting getSalesSetting() {
		final SalesSettings data = salesSettingDao.getSalesSetting();
		final PojoResGetSalesSetting res = new PojoResGetSalesSetting();
		
		res.setTax(data.getTax());
		res.setSystemIncome(data.getSystemIncome());
		res.setMemberIncome(data.getMemberIncome());
		res.setIsActive(data.getIsActive());
		res.setVer(data.getVersion());
		
		return res;
	}
	
	
}
