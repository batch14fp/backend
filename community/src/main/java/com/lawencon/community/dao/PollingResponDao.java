package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PollingRespon;

@Repository
public class PollingResponDao extends BaseMasterDao<PollingRespon>{

	@Override
	public List<PollingRespon> getAll() {
		return null;
	}

	@Override
	public Optional<PollingRespon> getById(String id) {
		return Optional.ofNullable(super.getById(PollingRespon.class, id));
	}

	@Override
	public PollingRespon getByIdRef(String id) {
		return super.getByIdRef(PollingRespon.class, id);
	}
	
	@Override
	public Optional<PollingRespon> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PollingRespon.class, id));

	}

}
