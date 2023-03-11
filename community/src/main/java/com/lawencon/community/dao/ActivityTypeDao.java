package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.ActivityType;


@Repository
public class ActivityTypeDao extends BaseMasterDao<ActivityType>{

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityType> getAll() {
		final String sql = "SELECT * FROM t_activity_type WHERE is_active = TRUE";
		final List<ActivityType> res = 	ConnHandler.getManager().createNativeQuery(sql, ActivityType.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<ActivityType> getById(String id) {
		return Optional.ofNullable(super.getById(ActivityType.class, id));
	}
	
	@Override
	public ActivityType getByIdRef(String id) {
		return super.getByIdRef(ActivityType.class, id);
	}
	@Override
	public Optional<ActivityType> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(ActivityType.class, id));

	}
	

}
