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
import com.lawencon.community.pojo.activitytype.PojoActivityTypeReqInsert;
import com.lawencon.community.pojo.activitytype.PojoActivityTypeReqUpdate;
import com.lawencon.community.pojo.activitytype.PojoActivityTypeRes;


@Service
public class ActivityTypeService {

	
	private final ActivityTypeDao activityTypeDao;

	public ActivityTypeService(final ActivityTypeDao activityTypeDao) {
		this.activityTypeDao = activityTypeDao;
	}
	private void validateBkNotNull(PojoActivityTypeReqInsert activityType) {
	    if(activityType.getTypeCode() == null) {
	        throw new RuntimeException("Activity Type Code cannot be empty.");
	    }
	}

	private void validateNonBk(PojoActivityTypeReqInsert activityType) {

		if (activityType.getTypeName() == null) {
			throw new RuntimeException("Activity Type Name cannot be empty.");
		}
	}
	private void validateNonBk(PojoActivityTypeReqUpdate activityType) {
		if (activityType == null) {
			throw new RuntimeException("Activity cannot be empty.");
		}
		if (activityType.getVer() == null) {
			throw new RuntimeException("Activity version cannot be empty.");
		}
		if (activityType.getTypeName() == null) {
			throw new RuntimeException("Activity Type Name cannot be empty.");
		}
	}
	
	private void validateBkNotExist(String id) {
		if(activityTypeDao.getById(id).isEmpty()) {
			throw new RuntimeException("Activity Type cannot be empty.");
		}
	}

	
	public List<PojoActivityTypeRes> getAll() {
		final List<PojoActivityTypeRes> activityList = new ArrayList<>();
		activityTypeDao.getAll().forEach(data -> {
			PojoActivityTypeRes activityType = new PojoActivityTypeRes();
			activityType.setActivityTypeId(data.getId());
			activityType.setTypeCode(data.getTypeCode());
			activityType.setTypeName(data.getActivityName());
			activityType.setIsActive(true);
			activityType.setVer(data.getVersion());
			activityList.add(activityType);
		});
		return activityList;

	}
	public PojoActivityTypeRes getByCode(String typeCode) {
		final PojoActivityTypeRes activityType = new PojoActivityTypeRes();
			final ActivityType data = activityTypeDao.getByCode(typeCode);
			activityType.setActivityTypeId(data.getId());
			activityType.setTypeCode(data.getTypeCode());
			activityType.setTypeName(data.getActivityName());
			activityType.setIsActive(data.getIsActive());
			activityType.setVer(data.getVersion());
		
		return activityType;

	}

	public PojoRes deleteById(String id) {
		validateBkNotExist(id);
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

	public PojoInsertRes save(PojoActivityTypeReqInsert data) {
		validateBkNotNull(data);
		validateNonBk(data);
		
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

	public PojoUpdateRes update(PojoActivityTypeReqUpdate data) {
		
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			validateNonBk(data);
			
			ConnHandler.begin();
			final ActivityType  activityType = activityTypeDao.getByIdRef(data.getActivityTypeId());
			activityTypeDao.getByIdAndDetach(ActivityType.class, activityType.getId());
			activityType.setId(activityType.getId());
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
