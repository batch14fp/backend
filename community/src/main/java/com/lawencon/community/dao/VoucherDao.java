package com.lawencon.community.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Voucher;
@Repository
public class VoucherDao extends AbstractJpaDao{
	

	public Optional<Voucher> getByCode(String code) {
		Voucher voucher = new Voucher();
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, voucher_code ");
		sb.append("FROM t_voucher ");
		sb.append("WHERE is_active = TRUE ");
		sb.append("AND voucher_code = :code ");
		
		try {

			final Object result = ConnHandler.getManager().createNativeQuery(sb.toString())
					.setParameter("code", code)
					.getSingleResult();
			Object[] obj = (Object[]) result;
			voucher.setId(obj[0].toString());
			voucher.setVoucherCode(obj[1].toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(voucher);
	}

	

}
