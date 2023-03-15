package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Invoice;

@Repository
public class InvoiceDao extends BaseMasterDao<Invoice> {

	@SuppressWarnings("unchecked")
	@Override
	List<Invoice> getAll() {
		final String sql = "SELECT * FROM t_invoice WHERE  is_active = TRUE";
		final List<Invoice> res = ConnHandler.getManager().createNativeQuery(sql, Invoice.class).getResultList();

		return res;
	}

	@Override
	public Optional<Invoice> getById(String id) {
		final Invoice invoice = ConnHandler.getManager().find(Invoice.class, id);
		return Optional.ofNullable(invoice);
	}

	@Override
	public Invoice getByIdRef(String id) {
		return super.getByIdRef(Invoice.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Invoice> getByOffsetLimit(Long offset, Long limit) {
		final String sql = "SELECT * FROM t_invoice WHERE is_active = TRUE LIMIT :limit OFFSET :offset";

		final List<Invoice> res = ConnHandler.getManager().createNativeQuery(sql, Invoice.class)
				.setParameter("offset", offset).setParameter("limit", limit).getResultList();

		return res;
	}

	@Override
	public Optional<Invoice> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Invoice.class, id));

	}

}
