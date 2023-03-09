package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Polling;


@Repository
public class PollingDao extends BaseMasterDao<Polling>{
	@SuppressWarnings("unchecked")
	@Override
	List<Polling> getAll() {
		final String sql = "SELECT * FROM t_poliing WHERE  is_active = TRUE";	
		final List<Polling> res = ConnHandler.getManager().createNativeQuery(sql, Polling.class).getResultList();
		
		return res;
	}

	@Override
	Optional<Polling> getById(String id) {
		return Optional.ofNullable(super.getById(Polling.class, id));
	}
	

	@Override
	Optional<Polling> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(Polling.class, id));
	}
	
	@Override
	public Optional<Polling> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Polling.class, id));

	}
	


}
