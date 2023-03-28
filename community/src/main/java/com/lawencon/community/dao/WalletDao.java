package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.model.Wallet;

@Repository
public class WalletDao extends AbstractJpaDao {

	public Optional<Wallet> getByUserId(String userId) {
		final Wallet wallet = new Wallet();
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT w.id, w.balance, w.bank_payment_id ");
		sqlQuery.append("FROM t_user u ");
		sqlQuery.append("INNER JOIN t_wallet w ");
		sqlQuery.append("ON w.id = u.wallet_id ");
		sqlQuery.append("WHERE u.id = :userId");
		try {
			Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setParameter("userId", userId).getSingleResult();

			if (result != null) {
				final Object[] obj = (Object[]) result;
				wallet.setId(obj[0].toString());
				wallet.setBalance(BigDecimal.valueOf(Long.valueOf(obj[1].toString())));
				final BankPayment bankPayment = new BankPayment();
				if (obj[2] != null) {
					bankPayment.setId(obj[2].toString());
					wallet.setBankPayment(bankPayment);
				}
			}
			return Optional.ofNullable(wallet);
		} catch (NoResultException e) {
			return Optional.empty();
		}

	}

	public Wallet getByIdRef(String id) {
		return super.getByIdRef(Wallet.class, id);
	}

}
