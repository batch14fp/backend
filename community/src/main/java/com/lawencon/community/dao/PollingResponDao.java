package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PollingOption;
import com.lawencon.community.model.PollingRespon;
import com.lawencon.community.model.User;

@Repository
public class PollingResponDao extends BaseMasterDao<PollingRespon> {

	@SuppressWarnings("unchecked")
	@Override
	public List<PollingRespon> getAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT pr.id, po.id AS polling_option_id, po.content_polling, ");
		sql.append("u.id AS user_id ");
		sql.append("FROM t_polling_respon pr ");
		sql.append("INNER JOIN t_polling_option po ON pr.polling_option_id = po.id ");
		sql.append("INNER JOIN t_user u ON pr.user_id = u.id ");

		final List<PollingRespon> listResult = new ArrayList<>();
		try {
			final List<Object> listObj = ConnHandler.getManager().createNativeQuery(sql.toString()).getResultList();
			for (Object obj : listObj) {
				final Object[] objArr = (Object[]) obj;
				PollingRespon pollingRespon = new PollingRespon();
				pollingRespon.setId((String) objArr[0]);

				final PollingOption pollingOption = new PollingOption();
				pollingOption.setId((String) objArr[1]);
				pollingOption.setContentPolling((String) objArr[2]);
				pollingRespon.setPollingOption(pollingOption);

				final User user = new User();
				user.setId((String) objArr[3]);
				pollingRespon.setUser(user);

				listResult.add(pollingRespon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listResult;
	}

	public Long getCountPollingOption(final String pollingOptionId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(id) FROM t_polling_respon ");
		sql.append("WHERE polling_option_id = :pollingOptionId ");

		try {
			Object result = ConnHandler.getManager().createNativeQuery(sql.toString())
					.setParameter("pollingOptionId", pollingOptionId).getSingleResult();
			if (result != null) {
				return Long.valueOf(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unused")
	public Boolean getIsVote(String userId, String pollingId) {
		Boolean data = false;
		try {
			final StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("SELECT DISTINCT pr.user_id ");
			sqlQuery.append("FROM t_polling_respon pr ");
			sqlQuery.append("INNER JOIN t_polling_option po ");
			sqlQuery.append("ON po.id = pr.polling_option_id ");
			sqlQuery.append("INNER JOIN t_polling p ");
			sqlQuery.append("ON p.id = po.polling_id ");
			sqlQuery.append("WHERE pr.user_id= :userId ");
			sqlQuery.append("AND po.polling_id = :pollingId ");
			final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setParameter("userId", userId).setParameter("pollingId", pollingId).getSingleResult();
			data = true;

		} catch (final Exception e) {
		}
		return data;

	}

	@Override
	public Optional<PollingRespon> getById(String id) {
		return Optional.ofNullable(super.getById(PollingRespon.class, id));
	}

	@Override
	public PollingRespon getByIdRef(String id) {
		return super.getByIdRef(PollingRespon.class, id);
	}

	@Override
	public Optional<PollingRespon> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PollingRespon.class, id));

	}

}
