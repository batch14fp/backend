package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.BankPayment;

@Repository
public class BankPaymentDao extends BaseMasterDao<BankPayment> {

	@SuppressWarnings("unchecked")
	@Override
	public List<BankPayment> getAll() {
		final String sql = "SELECT * FROM t_bank_payement WHERE  is_active = TRUE";
		final List<BankPayment> res = ConnHandler.getManager().createNativeQuery(sql, BankPayment.class)
				.getResultList();

		return res;
	}

	@Override
	Optional<BankPayment> getById(String id) {
		return Optional.ofNullable(super.getById(BankPayment.class, id));
	}

	@Override
	Optional<BankPayment> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(BankPayment.class, id));
	}
	
	@Override
	public Optional<BankPayment> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(BankPayment.class, id));

	}

}
