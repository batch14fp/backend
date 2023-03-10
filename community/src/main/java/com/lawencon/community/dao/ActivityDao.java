package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.model.Activity;

public class ActivityDao extends BaseMasterDao<Activity>{

	@Override
	public List<Activity> getAll() {
	
		return null;
	}

	@Override
	public Optional<Activity> getById(String id) {
		return Optional.ofNullable(super.getById(Activity.class, id));
		}
	

	@Override
	public Activity getByIdRef(String id) {
		
		return super.getByIdRef(Activity.class, id);
	}
	
	@Override
	public Optional<Activity> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Activity.class, id));

	}

}
