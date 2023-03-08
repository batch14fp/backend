package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.ActivityType;

public class ActivityTypeDao extends BaseMasterDao<ActivityType>{

	@SuppressWarnings("unchecked")
	@Override
	List<ActivityType> getAll() {
		final String sql = "SELECT * FROM t_activity_type WHERE is_active = TRUE";
		final List<ActivityType> res = ConnHandler.getManager().createNativeQuery(sql, ActivityType.class).getResultList();
		
		return res;
	}

	@Override
	Optional<ActivityType> getById(Long id) {
	final ActivityType activityType = ConnHandler.getManager().find(ActivityType.class, id);
		return Optional.ofNullable(activityType);
	}

}
