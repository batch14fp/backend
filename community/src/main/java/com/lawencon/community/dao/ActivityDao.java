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
	Optional<Activity> getById(Long id) {
		return Optional.ofNullable(super.getById(Activity.class, id));
		}
	

	@Override
	Optional<Activity> getByIdRef(Long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(super.getByIdRef(null, id));
	}

}
