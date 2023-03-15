package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Position;

@Repository
public class PositionDao extends BaseMasterDao<Position> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> getAll() {
		final StringBuilder sb = new StringBuilder();
	    sb.append("SELECT id, positionCode, positionName ");
	    sb.append("FROM t_position ");
	    sb.append("WHERE is_active = TRUE");

	    final List<Object[]> positionList = ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();
	    final List<Position> result = new ArrayList<>();
	    for (Object[] obj : positionList) {
	        Position position = new Position();
	        position.setId((String) obj[0]);
	        position.setPositionCode((String) obj[1]);
	        position.setPositionName((String) obj[2]);
	        result.add(position);
	    }
	    return result;
	}

	@Override
	public Optional<Position> getById(String id) {
		return Optional.ofNullable(super.getById(Position.class, id));
	}

	@Override
	public Position getByIdRef(String id) {
		return super.getByIdRef(Position.class, id);
	}

	
	@Override
	public Optional<Position> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Position.class, id));

	}

}
