package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.model.PollingRespon;

public class PollingResponDao extends BaseMasterDao<PollingRespon>{

	@Override
	List<PollingRespon> getAll() {
		return null;
	}

	@Override
	Optional<PollingRespon> getById(String id) {
		return Optional.ofNullable(super.getById(PollingRespon.class, id));
	}

	@Override
	Optional<PollingRespon> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(PollingRespon.class, id));
	}

}
