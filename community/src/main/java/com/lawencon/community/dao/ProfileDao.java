package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Profile;

public class ProfileDao extends BaseMasterDao<Profile>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Profile> getAll() {
		final String sql = "SELECT * FROM t_profile WHERE  is_active = TRUE";	
		final List<Profile> res = ConnHandler.getManager().createNativeQuery(sql, Profile.class).getResultList();
		return res;
	}

	@Override
	public Optional<Profile> getById(String id) {
		return Optional.ofNullable(super.getById(Profile.class, id));
	}
	
	
	@Override
	public Optional<Profile> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(Profile.class, id));
	}
	
	@Override
	public Optional<Profile> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Profile.class, id));

	}
	

	
	
	

}
