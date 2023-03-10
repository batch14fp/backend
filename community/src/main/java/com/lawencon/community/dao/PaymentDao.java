package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Payment;



@Repository
public class PaymentDao extends BaseMasterDao<Payment>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getAll() {
		final String sql = "SELECT * FROM t_payment WHERE  is_active = TRUE";	
		final List<Payment> res = ConnHandler.getManager().createNativeQuery(sql, Payment.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<Payment> getById(String id) {
		return Optional.ofNullable(super.getById(Payment.class, id));
	}
	

	@Override
	public Payment getByIdRef(String id) {
		return super.getByIdRef(Payment.class, id);
	}
	

	@SuppressWarnings("unchecked")
	public List<Payment> getByOffsetLimit(Long offset, Long limit) {
		  final String sql = "SELECT * FROM t_payment WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
			
			final List<Payment> res = ConnHandler.getManager().createNativeQuery(sql, Payment.class)
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			
			return res;
	}

	
	@Override
	public Optional<Payment> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Payment.class, id));

	}
	
	
}
