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
	List<Payment> getAll() {
		final String sql = "SELECT * FROM t_payment WHERE  is_active = TRUE";	
		final List<Payment> res = ConnHandler.getManager().createNativeQuery(sql, Payment.class).getResultList();
		
		return res;
	}

	@Override
	Optional<Payment> getById(Long id) {
		final Payment payment = ConnHandler.getManager().find(Payment.class, id);
		return Optional.ofNullable(payment);
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <Payment> Payment getByIdRef(Class<Payment> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}


	@SuppressWarnings("unchecked")
	List<Payment> getByOffsetLimit(Long offset, Long limit) {
		  final String sql = "SELECT * FROM t_payment WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
			
			final List<Payment> res = ConnHandler.getManager().createNativeQuery(sql, Payment.class)
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			
			return res;
	}

}
