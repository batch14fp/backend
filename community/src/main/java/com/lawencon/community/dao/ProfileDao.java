package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.BaseEntity;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Profile;

public class ProfileDao extends BaseMasterDao<Profile>{

	@SuppressWarnings("unchecked")
	@Override
	List<Profile> getAll() {
		final String sql = "SELECT * FROM t_profile WHERE  is_active = TRUE";	
		final List<Profile> res = ConnHandler.getManager().createNativeQuery(sql, Profile.class).getResultList();
		return res;
	}

	@Override
	Optional<Profile> getById(Long id) {
		return Optional.ofNullable(super.getById(Profile.class, id));
	}
	
	
	@Override
	Optional<Profile> getByIdRef(Long id) {
		return Optional.ofNullable(super.getByIdRef(Profile.class, id));
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <Profile extends BaseEntity> Profile save(Profile entity) {
		return super.save(entity);
	}
	
	
	@SuppressWarnings("hiding")
	@Override
	public <Profile extends BaseEntity> Profile saveAndFlush(Profile entity) {
		return super.saveAndFlush(entity);
	}
	
	
	

}
