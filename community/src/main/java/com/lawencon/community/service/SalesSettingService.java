package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.SalesSettingDao;
import com.lawencon.community.model.SalesSettings;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.salessetting.PojoSalesSettingReqUpdate;
import com.lawencon.community.pojo.salessetting.PojoSalesSettingRes;

@Service
public class SalesSettingService {
	private SalesSettingDao salesSettingDao;
	
	public SalesSettingService(final SalesSettingDao salesSettingDao) {
		this.salesSettingDao = salesSettingDao;
	}
	
	private void validateNonBk(PojoSalesSettingReqUpdate salesSettings) {
		if (salesSettings.getSalesSettingId() == null) {
			throw new RuntimeException("Sales Settings ID cannot be empty.");
		}
		
		if (salesSettings.getVer() == null) {
			throw new RuntimeException("Sales Settings version cannot be empty.");
		}
		
		if (salesSettings.getMemberIncome() == null) {
			throw new RuntimeException("Sales settings member income cannot be empty.");
		}
		
		if (salesSettings.getSystemIncome()== null) {
			throw new RuntimeException("Sales Settings System Income cannot be empty.");
		}
		if (salesSettings.getTax()== null) {
			throw new RuntimeException("Sales Settings Tax cannot be empty.");
		}
		
	}


	public PojoSalesSettingRes getSalesSetting() {
		final SalesSettings data = salesSettingDao.getSalesSetting();
		final PojoSalesSettingRes res = new PojoSalesSettingRes();
		
		res.setTax(data.getTax());
		res.setSystemIncome(data.getSystemIncome());
		res.setMemberIncome(data.getMemberIncome());
		res.setIsActive(data.getIsActive());
		res.setVer(data.getVersion());
		
		return res;
	}
	
	public PojoUpdateRes update(PojoSalesSettingReqUpdate data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			validateNonBk(data);
			
			ConnHandler.begin();
			final SalesSettings salesSetting = salesSettingDao.getByIdRef(data.getSalesSettingId());
			salesSettingDao.getByIdAndDetach(SalesSettings.class, data.getSalesSettingId());
			salesSetting.setId(data.getSalesSettingId());
			salesSetting.setMemberIncome(data.getMemberIncome());
			salesSetting.setSystemIncome(data.getSystemIncome());
			salesSetting.setTax(data.getTax());
			salesSetting.setVersion(data.getVer());
			
			final SalesSettings salesSettingsNew = salesSettingDao.saveAndFlush(salesSetting);
			ConnHandler.commit();

			pojoUpdateRes.setId(salesSettingsNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(salesSettingsNew.getVersion());

		} catch (Exception e) {
			e.printStackTrace();
			pojoUpdateRes.setId(data.getSalesSettingId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	}
	
	
}
