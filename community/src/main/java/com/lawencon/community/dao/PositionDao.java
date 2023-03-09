package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Position;

@Repository
public class PositionDao extends BaseMasterDao<Position> {

	@SuppressWarnings("unchecked")
	@Override
	List<Position> getAll() {
		final String sql = "SELECT * FROM t_position WHERE  is_active = TRUE";
		final List<Position> res = ConnHandler.getManager().createNativeQuery(sql, Position.class).getResultList();

		return res;
	}

	@Override
	Optional<Position> getById(String id) {
		return Optional.ofNullable(super.getById(Position.class, id));
	}

	@Override
	Optional<Position> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(Position.class, id));
	}
	
	

	@SuppressWarnings("unchecked")
	List<Position> getByOffsetLimit(Long offset, Long limit) {
		final String sql = "SELECT * FROM t_position WHERE is_active = TRUE LIMIT :limit OFFSET :offset";

		final List<Position> res = ConnHandler.getManager().createNativeQuery(sql, Position.class)
				.setParameter("offset", offset).setParameter("limit", limit).getResultList();

		return res;
	}
	
	
}
