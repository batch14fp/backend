package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Activity;




@Repository
public class ActivityDao extends BaseMasterDao<Activity>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getAll() {
		
		final String sql = "SELECT * FROM t_activity WHERE is_active = TRUE";
		final List<Activity> res = 	ConnHandler.getManager().createNativeQuery(sql, Activity.class).getResultList();
		
		return res;
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
