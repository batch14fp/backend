package com.lawencon.community.dao;

import java.util.ArrayList;
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
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, bank_name,account_name, account_number, ver, is_active ");
		sb.append("FROM t_bank_payment ");
		sb.append("WHERE is_active = TRUE");

		final List<Object[]> resultList = ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();
		final List<BankPayment> bankPayments = new ArrayList<>();

		for (Object[] obj : resultList) {
			BankPayment bankPayment = new BankPayment();
			bankPayment.setId(obj[0].toString());
			bankPayment.setBankName(obj[1].toString());
			bankPayment.setAccountName(obj[2].toString());
			bankPayment.setAccountNumber(obj[3].toString());
			bankPayment.setVersion(Integer.valueOf(obj[4].toString()));
			bankPayment.setIsActive(Boolean.valueOf(obj[5].toString()));
			bankPayments.add(bankPayment);
		}

		return bankPayments;
	}

	@Override
	public Optional<BankPayment> getById(String id) {
		return Optional.ofNullable(super.getById(BankPayment.class, id));
	}

	@Override
	public BankPayment getByIdRef(String id) {
		return super.getByIdRef(BankPayment.class, id);
	}

	@Override
	public Optional<BankPayment> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(BankPayment.class, id));

	}

}
