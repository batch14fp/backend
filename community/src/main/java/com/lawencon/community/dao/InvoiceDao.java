package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.Invoice;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;

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
	public List<Invoice> getByOffsetLimit(int offset, int limit) {
		final String sql = "SELECT * FROM t_invoice WHERE is_active = TRUE LIMIT :limit OFFSET :offset";

		final List<Invoice> res = ConnHandler.getManager().createNativeQuery(sql, Invoice.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();

		return res;
	}
	@SuppressWarnings("unchecked")
	public Invoice getByInvoiceCode(String invoiceCode) throws Exception {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT i.id, i.invoice_code, i.user_id, i.voucher_id,v.voucher_code,  i.activity_id, a.title, a.price, a.start_date, a.end_date ");
		sqlQuery.append("FROM t_invoice i ");
		sqlQuery.append("INNER JOIN t_voucher v ON v.id = i.voucher_id ");
		sqlQuery.append("INNER JOIn t_activity a ON activity_id = a.id ");
		sqlQuery.append("WHERE i.invoiceCode = :invoiceCode ");

		final List<Object[]> resultList = 
				ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setParameter("invoiceCode", invoiceCode).getResultList();

		if (resultList.size() == 0) {
			return null;
		}

		Object[] obj = resultList.get(0);
		Invoice invoice = new Invoice();
		invoice.setId(obj[0].toString());
		invoice.setInvoiceCode(obj[1].toString());
		
		final User user = new User();
		user.setId(obj[2].toString());
		invoice.setUser(user);
		
		final Voucher voucher = new Voucher();
		voucher.setId(obj[3].toString());
		voucher.setUsedCount(Integer.valueOf(obj[4].toString()));
		invoice.setVoucher(voucher);
		
		final Activity activity = new Activity();
		activity.setId(obj[5].toString());
		activity.setTitle(obj[6].toString());
		activity.setPrice(BigDecimal.valueOf(Long.valueOf(obj[7].toString())));
		activity.setStartDate(Timestamp.valueOf(obj[8].toString()).toLocalDateTime());
		activity.setEndDate(Timestamp.valueOf(obj[9].toString()).toLocalDateTime());
		invoice.setActivity(activity);

		return invoice;
	}
	@Override
	public Optional<Invoice> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Invoice.class, id));

	}

}
