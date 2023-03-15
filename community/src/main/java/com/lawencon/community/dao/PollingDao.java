package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Polling;


@Repository
public class PollingDao extends BaseMasterDao<Polling>{
	@SuppressWarnings("unchecked")
	@Override
	public List<Polling> getAll() throws Exception  {
	    final StringBuilder sql = new StringBuilder();
	    sql.append("SELECT id, title, ver,is_active FROM t_polling");

	    final List<Polling> listResult = new ArrayList<>();

	    try {
	        final List<Object[]> listObj = ConnHandler.getManager().createNativeQuery(sql.toString()).getResultList();

	        for (Object[] obj : listObj) {
	            final Polling polling = new Polling();
	            polling.setId(obj[0].toString());
	            polling.setTitle(obj[1].toString());
	            polling.setVersion(Integer.valueOf(obj[2].toString()));
	            polling.setIsActive(Boolean.valueOf(obj[3].toString()));
	            listResult.add(polling);
	        }
	    } catch (Exception e) {
	        throw new Exception("Failed to retrieve data from database", e);
	    }

	    return listResult;
	}

	@Override
	public Optional<Polling> getById(String id) {
		return Optional.ofNullable(super.getById(Polling.class, id));
	}
	

	@Override
	public Polling getByIdRef(String id) {
		return super.getByIdRef(Polling.class, id);
	}
	
	@Override
	public Optional<Polling> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Polling.class, id));

	}
	


}
