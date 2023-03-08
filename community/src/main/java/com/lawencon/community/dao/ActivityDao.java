package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Activity;

public class ActivityDao extends BaseMasterDao<Activity>{

	@Override
	List<Activity> getAll() {
	
		return null;
	}

	@Override
	Optional<Activity> getById(Long id) {
		final Activity activity = ConnHandler.getManager().find(Activity.class, id);
		return Optional.ofNullable(activity);
		}
	
	@SuppressWarnings("hiding")
	@Override
	public <Activity> Activity getByIdRef(Class<Activity> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}

}
