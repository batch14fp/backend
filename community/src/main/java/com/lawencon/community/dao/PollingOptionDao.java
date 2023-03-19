package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PollingOption;

@Repository
public class PollingOptionDao extends BaseMasterDao<PollingOption> {

	@Override
	public List<PollingOption> getAll() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<PollingOption> getAllOptionByPollingId(String id) throws Exception {

		final List<PollingOption> listPollingOption = new ArrayList<>();

		try {

			final StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("SELECT po.id as po_id , po.polling_id, po.content_polling,  po.ver ");
			sqlQuery.append("FROM t_polling_option po ");
			sqlQuery.append("INNER JOIN t_polling p ");
			sqlQuery.append("ON  p.id= po.polling_id ");
			sqlQuery.append("WHERE p.id = :id");
			final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setParameter("id", id).getResultList();

			if (result != null) {
				for (Object objs : result) {
					final Object[] obj = (Object[]) objs;
					final PollingOption pollingOption = new PollingOption();
					pollingOption.setId((obj[0].toString()));
					final Polling polling = new Polling();
					polling.setId(obj[1].toString());
					pollingOption.setPolling(polling);
					pollingOption.setContentPolling(obj[2].toString());
					pollingOption.setVersion(Integer.valueOf(obj[3].toString()));
					listPollingOption.add(pollingOption);

				}
		
		} 
		}catch (Exception e) {
	        throw new Exception("Failed to retrieve data from database", e);
	    
		}

		return listPollingOption;
	}

	@Override
	public Optional<PollingOption> getById(String id) {
		return Optional.ofNullable(super.getById(PollingOption.class, id));
	}

	@Override
	public PollingOption getByIdRef(String id) {
		return super.getByIdRef(PollingOption.class, id);
	}

	@Override
	public Optional<PollingOption> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PollingOption.class, id));

	}

}
