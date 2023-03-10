package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.model.PollingOption;

public class PollingOptionDao extends BaseMasterDao<PollingOption>{

	@Override
	public List<PollingOption> getAll() {
		return null;
	}

	@Override
	public Optional<PollingOption> getById(String id) {
		return Optional.ofNullable(super.getById(PollingOption.class, id));
	}

	@Override
	public Optional<PollingOption> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(PollingOption.class, id));
	}
	@Override
	public Optional<PollingOption> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PollingOption.class, id));

	}

}
