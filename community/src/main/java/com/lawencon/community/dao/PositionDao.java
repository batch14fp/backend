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
		final List<Position> result = new ArrayList<>();
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, position_code, position_name, ver ");
		sb.append("FROM t_position ");
		sb.append("WHERE is_active = TRUE");
		try {

			final List<Object[]> positionList = ConnHandler.getManager().createNativeQuery(sb.toString())
					.getResultList();

			for (Object[] obj : positionList) {
				Position position = new Position();
				position.setId(obj[0].toString());
				position.setPositionCode(obj[1].toString());
				position.setPositionName(obj[2].toString());
				position.setVersion(Integer.valueOf(obj[3].toString()));
				result.add(position);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
