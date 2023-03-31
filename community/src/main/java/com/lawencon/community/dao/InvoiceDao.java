package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.Invoice;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;

@Repository
public class InvoiceDao extends BaseMasterDao<Invoice> {

	@SuppressWarnings("unchecked")
	@Override
	List<Invoice> getAll() {
		final List<Invoice> invoiceList = new ArrayList<>();
		try {
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append(
					"SELECT i.id, i.invoice_code, i.user_id, i.voucher_id,v.voucher_code,  i.activity_id, a.title, a.price, a.start_date, a.end_date,i.membership_id, ms.status_name, ms.code_status, ms.period_day, ms.price, i.ver, i.is_active ");
			sqlQuery.append("FROM t_invoice i ");
			sqlQuery.append("INNER JOIN t_voucher v ON v.id = i.voucher_id ");
			sqlQuery.append("LEFT JOIN t_activity a ON i.activity_id = a.id ");
			sqlQuery.append("LEFT JOIN t_member_status ms ON ms.id =i.membership_id ");
			final List<Object[]> resultList = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.getResultList();

			if (resultList.size() == 0) {
				return null;
			}

			for (Object[] objs : resultList) {
				Object[] obj = objs;
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
				if (obj[5] != null) {
					final Activity activity = new Activity();
					activity.setId(obj[5].toString());
					activity.setTitle(obj[6].toString());
					activity.setPrice(BigDecimal.valueOf(Long.valueOf(obj[7].toString())));
					activity.setStartDate(Timestamp.valueOf(obj[8].toString()).toLocalDateTime());
					activity.setEndDate(Timestamp.valueOf(obj[9].toString()).toLocalDateTime());
					invoice.setActivity(activity);
				}
				if (obj[10] != null) {
					final MemberStatus memberStatus = new MemberStatus();
					memberStatus.setId(obj[10].toString());
					memberStatus.setStatusName(obj[11].toString());
					memberStatus.setCodeStatus(obj[12].toString());
					memberStatus.setPeriodDay(Integer.valueOf(obj[13].toString()));
					memberStatus.setPrice(BigDecimal.valueOf(Long.valueOf(obj[14].toString())));

				}
				invoice.setVersion(Integer.valueOf(obj[15].toString()));
				invoice.setIsActive(Boolean.valueOf(obj[16].toString()));
				invoiceList.add(invoice);

			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve invoice ", e);

		}

		return invoiceList;
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

	public Optional<Invoice> getByInvoiceCode(String invoiceCode) {
		StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append(
				"SELECT i.id, i.invoice_code, i.user_id, i.voucher_id,v.voucher_code,  i.activity_id, a.title, a.price, a.start_date, a.end_date,i.membership_id, ms.status_name, ms.code_status, ms.period_day, ms.price, i.ver, i.is_active ");
		sqlQuery.append("FROM t_invoice i ");
		sqlQuery.append("INNER JOIN t_voucher v ON v.id = i.voucher_id ");
		sqlQuery.append("LEFT JOIN t_activity a ON i.activity_id = a.id ");
		sqlQuery.append("LEFT JOIN t_member_status ms ON ms.id =i.membership_id ");
		sqlQuery.append("WHERE i.invoice_code = :invoiceCode ");
		final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("invoiceCode", invoiceCode).getResultList();
		Invoice invoice = new Invoice();
		try {
			Object[] obj = (Object[]) result;

			invoice.setId(obj[0].toString());
			invoice.setInvoiceCode(obj[1].toString());

			final User user = new User();
			user.setId(obj[2].toString());
			invoice.setUser(user);

			final Voucher voucher = new Voucher();
			voucher.setId(obj[3].toString());
			voucher.setUsedCount(Integer.valueOf(obj[4].toString()));
			invoice.setVoucher(voucher);
			if (obj[5] != null) {
				final Activity activity = new Activity();
				activity.setId(obj[5].toString());
				activity.setTitle(obj[6].toString());
				activity.setPrice(BigDecimal.valueOf(Long.valueOf(obj[7].toString())));
				activity.setStartDate(Timestamp.valueOf(obj[8].toString()).toLocalDateTime());
				activity.setEndDate(Timestamp.valueOf(obj[9].toString()).toLocalDateTime());
				invoice.setActivity(activity);
			}
			if (obj[10] != null) {
				final MemberStatus memberStatus = new MemberStatus();
				memberStatus.setId(obj[10].toString());
				memberStatus.setStatusName(obj[11].toString());
				memberStatus.setCodeStatus(obj[12].toString());
				memberStatus.setPeriodDay(Integer.valueOf(obj[13].toString()));
				memberStatus.setPrice(BigDecimal.valueOf(Long.valueOf(obj[14].toString())));

			}
			invoice.setVersion(Integer.valueOf(obj[15].toString()));
			invoice.setIsActive(Boolean.valueOf(obj[16].toString()));
			return Optional.ofNullable(invoice);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Invoice> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Invoice.class, id));

	}

	@SuppressWarnings("unused")
	public Boolean getIsBought(String activityId, String userId) {
		StringBuilder sqlQuery = new StringBuilder();
		 Boolean data = false;
		sqlQuery.append("SELECT i.id ");
		sqlQuery.append("FROM t_invoice i ");
		sqlQuery.append("INNER JOIN t_voucher v ON v.id = i.voucher_id ");
		sqlQuery.append("INNER JOIN t_activity a ON i.activity_id = a.id ");
		sqlQuery.append("WHERE i.i.activity_id = :activityId ");
		sqlQuery.append("AND i.user_id = :userId ");
		try {
		final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("activityId", activityId)
				.setParameter("userId", userId)
				.getSingleResult();
		data =true;

		} catch (Exception e) {
		}
		return data;
	}

}
