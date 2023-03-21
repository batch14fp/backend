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
public class PollingResponDao extends BaseMasterDao<PollingRespon>{

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
		  final List<Object> listObj = ConnHandler.getManager().createNativeQuery(sql.toString()).getResultList();
		  for (Object obj : listObj) {
		    final  Object[] objArr = (Object[]) obj;
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

		  return listResult;
	}
	
	


	public Long getCountPollingOption(final String pollingOptionId) {
		final StringBuilder sql = new StringBuilder();
		Long count =null;
		sql.append("SELECT COUNT(id) FROM t_polling_respon ");
		sql.append("WHERE polling_option_id = :pollingOptionId ");

		count= Long.valueOf(ConnHandler.getManager().createNativeQuery(sql.toString())
	
				.setParameter("pollingOptionId",pollingOptionId)
				.getSingleResult().toString());
	
	return count;	
		
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
