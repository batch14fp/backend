package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.model.Industry;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.industry.PojoIndustryReq;
import com.lawencon.community.pojo.industry.PojoResGetIndustry;

public class IndustryService extends BaseService<PojoResGetIndustry>{
	
	
	private IndustryDao industryDao;
	
	public IndustryService(final  IndustryDao industryDao) {
		
		this.industryDao = industryDao;
	}
	

	@Override
	public List<PojoResGetIndustry> getAll() {

		final List<PojoResGetIndustry> res = new ArrayList<>();
		
		industryDao.getAll().forEach(data->{
			final PojoResGetIndustry pojoResGetIndustry = new PojoResGetIndustry();
			pojoResGetIndustry.setIndustryId(data.getId());
			pojoResGetIndustry.setIndustryName(data.getIndustryName());
			pojoResGetIndustry.setIsActive(data.getIsActive());
			res.add(pojoResGetIndustry);
			
		});
		
		return res;
	}
	
	
	
	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = industryDao.deleteById(Industry.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}
	
	public PojoRes save(PojoIndustryReq data) {
		ConnHandler.begin();


		Industry industry = new Industry();

		if (data.getIndustryId() != null) {

			industry = industryDao.getByIdAndDetach(data.getIndustryId()).get();
			industry.setIndustryName(data.getIndustryName());
			industry.setIsActive(data.getIsActive());
			industry.setVersion(data.getVer());
			
			

		} else {
			industry.setIndustryName(data.getIndustryName());
			industry.setIsActive(true);
			
		}

		industryDao.save(industry);
		ConnHandler.commit();

		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}

}
