package com.lawencon.community.dao;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.model.Wallet;

@Repository
public class WalletDao extends AbstractJpaDao {

	public Wallet getByUserId(String userId) {
		final Wallet wallet = new Wallet();
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT w.id, w.balance, w.bank_payment_id ");
		sqlQuery.append("FROM t_user u ");
		sqlQuery.append("INNER JOIN t_wallet w w.id = u.wallet_id ");
		sqlQuery.append("WHERE u.id = :userId");

		Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setParameter("userId", userId)
				.getResultList();

		if (result != null) {
			final Object[] obj = (Object[]) result;
			wallet.setId(obj[0].toString());
			wallet.setBalance(BigDecimal.valueOf(Long.valueOf(obj[1].toString())));
			final BankPayment bankPayment = new BankPayment();
			bankPayment.setId(obj[2].toString());
			wallet.setBankPayment(bankPayment);
		}
		return wallet;

	}

}
