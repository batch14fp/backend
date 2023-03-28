package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PollingOption;
import com.lawencon.community.pojo.post.PojoOptionCountRes;

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
			sqlQuery.append("WHERE p.id = :id ");
			sqlQuery.append("GROUP BY po.id, po.content_polling ");
			sqlQuery.append("ORDER BY po.created_at ");
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
	@SuppressWarnings("unchecked")
	public List<PojoOptionCountRes> countPollingOptionUsers(String pollingId) throws Exception {
	    List<PojoOptionCountRes> pollingOptionUserCounts = new ArrayList<>();
	    try {
	        final StringBuilder sqlQuery = new StringBuilder();
	        sqlQuery.append("SELECT po.id, po.content_polling, COUNT(ps.id) as user_count ");
	        sqlQuery.append("FROM t_polling_option po ");
	        sqlQuery.append("LEFT JOIN t_polling_respon ps ");
	        sqlQuery.append("ON po.id = ps.polling_option_id ");
	        sqlQuery.append("WHERE po.polling_id = :pollingId ");
	        sqlQuery.append("GROUP BY po.id, po.content_polling ");
	        sqlQuery.append("ORDER BY po.created_at ");
	        final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	                .setParameter("pollingId", pollingId).getResultList();
	        if (result != null) {
	            for (Object objs : result) {
	                final Object[] obj = (Object[]) objs;
	                final String pollingOptionId = obj[0].toString();
	                final String pollingContent = obj[1].toString();
	                final int userCount = Integer.parseInt(obj[2].toString());
	                PojoOptionCountRes pojo = new PojoOptionCountRes();
	                pojo.setPollingOptionId(pollingOptionId);
	                pojo.setPollingContent(pollingContent);
	                pojo.setCount(userCount);
	                pollingOptionUserCounts.add(pojo);
	            }
	        }
	    } catch (Exception e) {
	        throw new Exception("Failed to retrieve data from database", e);
	    }
	    return pollingOptionUserCounts;
	}


	public int countTotalPollingUsers(String pollingId) throws Exception {
	    int totalPollingUsers = 0;
	    try {
	        final StringBuilder sqlQuery = new StringBuilder();
	        sqlQuery.append("SELECT COUNT(id) ");
	        sqlQuery.append("FROM t_polling_respon ");
	        sqlQuery.append("WHERE polling_option_id IN ( ");
	        sqlQuery.append("    SELECT id FROM t_polling_option WHERE polling_id = :pollingId ");
	        sqlQuery.append(")");
	        final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	                .setParameter("pollingId", pollingId).getSingleResult();
	        if (result != null) {
	            totalPollingUsers = Integer.parseInt(result.toString());
	        }
	    } catch (Exception e) {
	        throw new Exception("Failed to retrieve data from database", e);
	    }
	    return totalPollingUsers;
	}
	
	
	public Integer countOptionByPollingId(String id) throws Exception {
	    try {
	        final StringBuilder sqlQuery = new StringBuilder();
	        sqlQuery.append("SELECT COUNT(po.id) FROM t_polling_option po ");
	        sqlQuery.append("INNER JOIN t_polling p ");
	        sqlQuery.append("ON p.id= po.polling_id ");
	        sqlQuery.append("WHERE p.id = :id");

	        final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	                .setParameter("id", id).getSingleResult();	        
	        return Integer.valueOf(result.toString());
	    } catch (Exception e) {
	        throw new Exception("Failed to retrieve data from database", e);
	    }
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
