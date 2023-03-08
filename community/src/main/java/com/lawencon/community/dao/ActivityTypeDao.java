package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.ActivityType;

public class ActivityTypeDao extends BaseMasterDao<ActivityType>{

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityType> getAll() {
		final String sql = "SELECT * FROM t_activity_type WHERE is_active = TRUE";
		final List<ActivityType> res = 	ConnHandler.getManager().createNativeQuery(sql, ActivityType.class).getResultList();
		
		return res;
	}

	@Override
	Optional<ActivityType> getById(Long id) {
		return Optional.ofNullable(super.getById(ActivityType.class, id));
	}
	
	@Override
	Optional<ActivityType> getByIdRef(Long id) {
		return Optional.ofNullable(super.getByIdRef(ActivityType.class, id));
	}
	

}
