package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PollingOption;

public class PollingOptionDao extends BaseMasterDao<PollingOption> {

	@Override
	public List<PollingOption> getAll() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<PollingOption> getAllOptionByPollingId(String id) {

		final List<PollingOption> listPollingOption = new ArrayList<>();

		try {

			final StringBuilder sql = new StringBuilder();
			sql.append("SELECT po.id,p.id, po.polling_id, po.content_polling,  po.created_by, po.created_at, po.isActive, po.ver ");
			sql.append("FROM t_polling_option po ");
			sql.append("INNER JOIN t_polling p ");
			sql.append("ON  p.id= po.polling_id WHERE po.id = :id");
			final List<Object> result = ConnHandler.getManager().createNativeQuery(sql.toString())
					.setParameter("id", id).getResultList();

			if (result != null) {
				for (Object objs : result) {
					final Object[] obj = (Object[]) objs;
					final PollingOption pollingOption = new PollingOption();
					pollingOption.setId(String.valueOf((obj[0])));
					pollingOption.setContentPolling(String.valueOf((obj[1])));
					listPollingOption.add(pollingOption);

				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
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
