package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.SalesSettingDao;
import com.lawencon.community.model.SalesSettings;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.salessetting.PojoResGetSalesSetting;
import com.lawencon.community.pojo.salessetting.PojoSalesSettingUpdateReq;

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
	
	public PojoUpdateRes update(PojoSalesSettingUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
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
