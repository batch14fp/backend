package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.activity.PojoActivityInsertReq;
import com.lawencon.community.pojo.activity.PojoActivityUpdateReq;
import com.lawencon.community.pojo.activity.PojoResGetActivity;

public class ActivityService {


	private final ActivityDao activityDao;
	private final CategoryDao categoryDao;
	private final ActivityTypeDao activityTypeDao;
	private final FileDao fileDao;

	public ActivityService(final ActivityDao activityDao, final CategoryDao categoryDao, final ActivityTypeDao activityTypeDao, final FileDao fileDao) {
		this.activityDao = activityDao;
		this.categoryDao = categoryDao;
		this.activityTypeDao = activityTypeDao;
		this.fileDao = fileDao;

	}

	
	public List<PojoResGetActivity> getAll() {
		final List<PojoResGetActivity> activityList = new ArrayList<>();
		activityDao.getAll().forEach(data -> {
			PojoResGetActivity activity = new PojoResGetActivity();
			activity.setActivityId(data.getId());
			activity.setCategoryCode(data.getCategory().getCategoryCode());
			activity.setCategoryName(data.getCategory().getCategoryName());
			activity.setTitle(data.getTitle());
			activity.setStartDate(data.getStartDate());
			activity.setEndDate(data.getEndDate());
			activity.setStartTime(data.getStartTime());
			activity.setPrice(data.getPrice());
			activity.setProviders(data.getProvider());
			activity.setEndTime(data.getEndTime());
			activity.setTypeCode(data.getTypeActivity().getTypeCode());
			activity.setTypeName(data.getTypeActivity().getActivityName());
			activity.setUserId(data.getCreatedBy());
			activity.setImgActivityId(data.getFile().getId());
			activity.setIsActive(data.getIsActive());
			activityList.add(activity);
		});
		return activityList;

	}

	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = activityDao.deleteById(Activity.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}
	}

	public PojoInsertRes save(PojoActivityInsertReq data) {
		ConnHandler.begin();
		final Activity activity = new Activity();
		activity.setCategory(categoryDao.getByIdRef(data.getCategoryId()));
		activity.setTitle(data.getTitle());
		activity.setStartDate(data.getStartDate());
		activity.setEndDate(data.getEndDate());
		activity.setStartTime(data.getStartTime());
		activity.setPrice(data.getPrice());
		activity.setProvider(data.getProviders());
		activity.setEndTime(data.getEndTime());
		activity.setTypeActivity(activityTypeDao.getByIdRef(data.getTypeId()));
		activity.setFile(fileDao.getByIdRef(data.getImgActivityId()));
		activity.setIsActive(true);
		final Activity activityNew = activityDao.save(activity);
		ConnHandler.commit();
		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(activityNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}

	public PojoUpdateRes update(PojoActivityUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			final Activity activity = activityDao.getByIdRef(data.getActivityId());
			
			activity.setCategory(categoryDao.getByIdRef(data.getCategoryId()));
			activity.setTitle(data.getTitle());
			activity.setStartDate(data.getStartDate());
			activity.setEndDate(data.getEndDate());
			activity.setStartTime(data.getStartTime());
			activity.setPrice(data.getPrice());
			activity.setProvider(data.getProviders());
			activity.setEndTime(data.getEndTime());
			activity.setTypeActivity(activityTypeDao.getByIdRef(data.getTypeId()));
			activity.setFile(fileDao.getByIdRef(data.getImgActivityId()));
			activity.setIsActive(true);
			activity.setIsActive(data.getIsActive());
			activity.setVersion(data.getVer());

			final Activity activityNew = activityDao.saveAndFlush(activity);
			ConnHandler.commit();
			pojoUpdateRes.setId(activityNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(activityNew.getVersion());

		} catch (Exception e) {
			pojoUpdateRes.setId(data.getActivityId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	}
	
	

	public PojoResGetActivity getById(String id) {
		final PojoResGetActivity activity = new PojoResGetActivity();
		    final Activity data =   activityDao.getByIdRef(id);
			activity.setActivityId(data.getId());
			activity.setCategoryCode(data.getCategory().getCategoryCode());
			activity.setCategoryName(data.getCategory().getCategoryName());
			activity.setTitle(data.getTitle());
			activity.setStartDate(data.getStartDate());
			activity.setEndDate(data.getEndDate());
			activity.setStartTime(data.getStartTime());
			activity.setPrice(data.getPrice());
			activity.setProviders(data.getProvider());
			activity.setEndTime(data.getEndTime());
			activity.setTypeCode(data.getTypeActivity().getTypeCode());
			activity.setTypeName(data.getTypeActivity().getActivityName());
			activity.setUserId(data.getCreatedBy());
			activity.setImgActivityId(data.getFile().getId());
			activity.setIsActive(data.getIsActive());
		return activity;

	}
	
	

}
