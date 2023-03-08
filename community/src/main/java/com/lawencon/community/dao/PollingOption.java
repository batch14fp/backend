package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

public class PollingOption extends BaseMasterDao<Polling>{

	@Override
	List<Polling> getAll() {
		return null;
	}

	@Override
	Optional<Polling> getById(Long id) {
		return Optional.ofNullable(super.getById(Polling.class, id));
	}

	@Override
	Optional<Polling> getByIdRef(Long id) {
		return Optional.ofNullable(super.getByIdRef(Polling.class, id));
	}

}
