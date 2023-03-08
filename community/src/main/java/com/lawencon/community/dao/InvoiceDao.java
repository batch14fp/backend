package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Invoice;


@Repository
public class InvoiceDao extends BaseMasterDao<Invoice>{

	@SuppressWarnings("unchecked")
	@Override
	List<Invoice> getAll() {
		final String sql = "SELECT * FROM t_invoice WHERE  is_active = TRUE";	
		final List<Invoice> res = ConnHandler.getManager().createNativeQuery(sql, Invoice.class).getResultList();
		
		return res;
	}

	@Override
	Optional<Invoice> getById(Long id) {
		final Invoice invoice = ConnHandler.getManager().find(Invoice.class, id);
		return Optional.ofNullable(invoice);
	}
	@SuppressWarnings("hiding")
	@Override
	public <Invoice> Invoice getByIdRef(Class<Invoice> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}


	@SuppressWarnings("unchecked")
	List<Invoice> getByOffsetLimit(Long offset, Long limit) {
		  final String sql = "SELECT * FROM t_invoice WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
			
			final List<Invoice> res = ConnHandler.getManager().createNativeQuery(sql, Invoice.class)
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			
			return res;
	}

}