package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.model.ActivityVoucher;
import com.lawencon.community.model.Voucher;



@Repository
public class ActivityVoucherDao extends AbstractJpaDao{
	
	public List<ActivityVoucher> getListActivityVoucher(String activityId) {
		if (activityId == null || activityId.isEmpty()) {
		        throw new IllegalArgumentException("activityId cannot be null or empty");
		    }
		
		StringBuilder sqlQuery = new StringBuilder();

		List<ActivityVoucher> listActivityVoucher = new ArrayList<>();
		try {
		sqlQuery.append("SELECT av.id,  v.id, v.voucher_code,v.limit_applied, v.used_count, a.id, a.type_activity_id ");
		sqlQuery.append("FROM t_activity_voucher av ");
		sqlQuery.append("JOIN t_voucher v ON av.voucher_id = v.id ");
		sqlQuery.append("JOIN t_activity a ON av.activity_id = a.id");
		sqlQuery.append("WHERE av.activity_id = :activityId ");
		
		Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("activityId", activityId);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		for (Object[] obj : resultList) {
			ActivityVoucher activityVoucher = new ActivityVoucher();
			activityVoucher.setId(obj[0].toString());
	
			final Voucher voucher = new Voucher();
			voucher.setId(obj[1].toString());
			voucher.setVoucherCode(obj[2].toString());
			voucher.setLimitApplied(Integer.valueOf(obj[3].toString()));
			voucher.setUsedCount(Integer.valueOf(obj[4].toString()));
			
			
			activityVoucher.setVoucher(voucher);
			
			final Activity activity = new Activity();
			activity.setId(obj[5].toString());
			final ActivityType activityType = new ActivityType();  
			activityType.setId(obj[6].toString());
			
			activity.setTypeActivity(activityType);
			
			activityVoucher.setActivity(activity);
			listActivityVoucher.add(activityVoucher);
		}
		}catch (Exception e) {
	        throw new RuntimeException("Failed to retrieve activity vouchers", e);
	    
		}
		return listActivityVoucher;
	}
	


}
