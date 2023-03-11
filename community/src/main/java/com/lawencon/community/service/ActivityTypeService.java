package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.activitytype.PojoActivityTypeInsertReq;
import com.lawencon.community.pojo.activitytype.PojoActivityTypeUpdateReq;
import com.lawencon.community.pojo.activitytype.PojoResGetActivityType;


@Service
public class ActivityTypeService {

	
	private final ActivityTypeDao activityTypeDao;

	public ActivityTypeService(final ActivityTypeDao activityTypeDao) {
		this.activityTypeDao = activityTypeDao;

	}

	
	public List<PojoResGetActivityType> getAll() {
		final List<PojoResGetActivityType> activityList = new ArrayList<>();
		activityTypeDao.getAll().forEach(data -> {
			PojoResGetActivityType activityType = new PojoResGetActivityType();
			activityType.setActivityTypeId(data.getId());
			activityType.setTypeCode(data.getTypeCode());
			activityType.setTypeName(data.getActivityName());
			activityType.setIsActive(true);
			activityList.add(activityType);
		});
		return activityList;

	}

	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = activityTypeDao.deleteById(ActivityType.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}
	}

	public PojoInsertRes save(PojoActivityTypeInsertReq data) {
		ConnHandler.begin();
		final ActivityType activityType = new ActivityType();
		activityType.setTypeCode(data.getTypeCode());
		activityType.setActivityName(data.getTypeName());
		activityType.setIsActive(true);
		final ActivityType activityTypeNew = activityTypeDao.save(activityType);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(activityTypeNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}

	public PojoUpdateRes update(PojoActivityTypeUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			final ActivityType  activityType = activityTypeDao.getByIdRef(data.getActivityTypeId());
			activityTypeDao.getByIdAndDetach(ActivityType.class, activityType.getId());
			activityType.setId(activityType.getId());
			activityType.setTypeCode(data.getTypeCode());
			activityType.setActivityName(data.getTypeName());
			activityType.setIsActive(data.getIsActive());
			activityType.setVersion(data.getVer());

			final ActivityType activityTypeNew = activityTypeDao.saveAndFlush(activityType);
			ConnHandler.commit();

			pojoUpdateRes.setId(activityTypeNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(activityTypeNew.getVersion());

		} catch (Exception e) {
			pojoUpdateRes.setId(data.getActivityTypeId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	}
	
	

}
