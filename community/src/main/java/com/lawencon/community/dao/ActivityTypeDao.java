package com.lawencon.community.dao;

import java.util.ArrayList;
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
		final List<ActivityType>  activityTypes = new ArrayList<>();
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT id, type_code, activity_name,ver,is_active ");
		sqlQuery.append("FROM t_activity_type ");
		sqlQuery.append("WHERE is_active = TRUE");
		
		final List<Object> result = 	ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).getResultList();
		try {
			for (final Object objs : result) {
				final Object[] obj = (Object[]) objs;
				final ActivityType activityType = new ActivityType();
				activityType.setId( obj[0].toString());
				activityType.setTypeCode( obj[1].toString());
				activityType.setActivityName( obj[2].toString());
				activityType.setVersion(Integer.valueOf(obj[3].toString()));
				activityType.setIsActive(Boolean.valueOf(obj[4].toString()));
			
				activityTypes.add(activityType);
			}
			
		}catch(final Exception e) {
			e.printStackTrace();
		}
		return activityTypes;
	}
	
	public ActivityType getByCode(String typeCode) {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT id, type_code, activity_name,ver,is_active ");
		sqlQuery.append("FROM t_activity_type ");
		sqlQuery.append("WHERE is_active = TRUE AND ");
		sqlQuery.append("type_code = :typeCode "); 
		final ActivityType activityType = new ActivityType();
		final Object result = 	ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setParameter("typeCode", typeCode).getSingleResult();
		try {
				final Object[] obj = (Object[]) result;
		
				activityType.setId( obj[0].toString());
				activityType.setTypeCode( obj[1].toString());
				activityType.setActivityName( obj[2].toString());
				activityType.setVersion(Integer.valueOf(obj[3].toString()));
				activityType.setIsActive(Boolean.valueOf(obj[4].toString()));
			
		}catch(final Exception e) {
			e.printStackTrace();
		}
		return activityType;
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
