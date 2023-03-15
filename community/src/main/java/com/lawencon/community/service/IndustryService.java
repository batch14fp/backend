package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.model.Industry;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.industry.PojoIndustryInsertReq;
import com.lawencon.community.pojo.industry.PojoIndustryUpdateReq;
import com.lawencon.community.pojo.industry.PojoResGetIndustry;

@Service
public class IndustryService extends BaseService<PojoResGetIndustry> {

	private IndustryDao industryDao;

	public IndustryService(final IndustryDao industryDao) {

		this.industryDao = industryDao;
	}

	@Override
	public List<PojoResGetIndustry> getAll() {

		final List<PojoResGetIndustry> res = new ArrayList<>();

		industryDao.getAll().forEach(data -> {
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

	public PojoInsertRes save(PojoIndustryInsertReq data) {
		ConnHandler.begin();
		final Industry industry = new Industry();

		industry.setIndustryName(data.getIndustryName());
		industry.setIsActive(true);

		final Industry industryNew = industryDao.save(industry);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(industryNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}

	public PojoUpdateRes update(PojoIndustryUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			final Industry industry  = industryDao.getByIdRef(data.getIndustryId());
			industryDao.getByIdAndDetach(Industry.class, industry.getId());
			industry.setId(industry.getId());
			industry.setIndustryName(data.getIndustryId());
			industry.setIsActive(data.getIsActive());
			industry.setVersion(data.getVer());

			final Industry industryNew = industryDao.saveAndFlush(industry);
			ConnHandler.commit();
	
			pojoUpdateRes.setId(industryNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(industryNew.getVersion());
		
		} catch (Exception e) {
			pojoUpdateRes.setId(data.getIndustryId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;
		
	}

}
