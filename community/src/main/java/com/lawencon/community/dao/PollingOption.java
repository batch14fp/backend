package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

public class PollingOption extends BaseMasterDao<Polling>{

	@Override
	List<Polling> getAll() {
		return null;
	}

	@Override
	Optional<Polling> getById(String id) {
		return Optional.ofNullable(super.getById(Polling.class, id));
	}

	@Override
	Optional<Polling> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(Polling.class, id));
	}

}
