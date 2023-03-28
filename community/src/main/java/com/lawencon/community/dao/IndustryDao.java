package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Industry;

@Repository
public class IndustryDao extends BaseMasterDao<Industry> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Industry> getAll() {

		final StringBuilder sb = new StringBuilder();
		final List<Industry> industryList = new ArrayList<>();
		try {
		sb.append("SELECT id, industry_name, ver, is_active ");
		sb.append("FROM t_industry ");
		sb.append("WHERE  is_active = TRUE");
		List<Object[]> resultList = ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();

		for (Object[] obj : resultList) {
			Industry industry = new Industry();
			industry.setId(obj[0].toString());
			industry.setIndustryName(obj[1].toString());
			industry.setVersion(Integer.valueOf(obj[2].toString()));
			industry.setIsActive(Boolean.valueOf(obj[3].toString()));
			industryList.add(industry);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return industryList;

	}

	@Override
	public Optional<Industry> getById(String id) {
		return Optional.ofNullable(super.getById(Industry.class, id));
	}

	@Override
	public Industry getByIdRef(String id) {
		return super.getByIdRef(Industry.class, id);
	}

	@Override
	public Optional<Industry> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Industry.class, id));

	}

}
