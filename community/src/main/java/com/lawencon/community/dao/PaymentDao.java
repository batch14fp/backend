package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Invoice;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Payment;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;

@Repository
public class PaymentDao extends BaseMasterDao<Payment> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getAll() {
		final String sql = "SELECT * FROM t_payment WHERE  is_active = TRUE";
		final List<Payment> res = ConnHandler.getManager().createNativeQuery(sql, Payment.class).getResultList();

		return res;
	}

	@Override
	public Optional<Payment> getById(String id) {
		return Optional.ofNullable(super.getById(Payment.class, id));
	}

	@Override
	public Payment getByIdRef(String id) {
		return super.getByIdRef(Payment.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Payment> getByOffsetLimit(Integer offset, Integer limit) {
		String sql = "SELECT * FROM t_payment WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
		List<Payment> paymentList = new ArrayList<>();	
		try {
			paymentList = ConnHandler.getManager().createNativeQuery(sql, Payment.class)
					.setFirstResult(offset)
					.setMaxResults(limit)
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymentList;
	}
	
	
	@SuppressWarnings("unchecked")
	List<Payment> getAllPaymentByIsPaid(Boolean isPaid) {
		final List<Payment> paymentList = new ArrayList<>();
		try {
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append(
					"SELECT i.id, i.invoice_code, i.user_id, i.voucher_id,v.voucher_code,  i.activity_id, a.title, a.price, a.start_date, a.end_date,i.membership_id, ms.status_name, ms.code_status, ms.period_day, ms.price, i.ver, i.is_active, p.id as payment_id, p.file_id, p.bank_payment_id, p.total ");
			sqlQuery.append("FROM t_payment p ");
			sqlQuery.append("INNER JOIN t_invoice i ON i.id = p.invoice_id ");
			sqlQuery.append("INNER JOIN t_voucher v ON v.id = i.voucher_id ");
			sqlQuery.append("LEFT JOIN t_activity a ON i.activity_id = a.id ");
			sqlQuery.append("LEFT JOIN t_member_status ms ON ms.id =i.membership_id ");
			
			if(isPaid!=null) {
			sqlQuery.append("WHERE p.is_paid = :isPaid ");
			}
			
			Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString());
			
			if(isPaid!=null) {
				query.setParameter("isPaid", isPaid);
			}
			
			final List<Object[]> resultList = query.getResultList();

			if (resultList.size() == 0) {
				return null;
			}

			for (Object[] objs : resultList) {
				Object[] obj = objs;
				Payment payment = new Payment();
				Invoice invoice = new Invoice();
				invoice.setId(obj[0].toString());
				invoice.setInvoiceCode(obj[1].toString());

				final User user = new User();
				user.setId(obj[2].toString());
				invoice.setUser(user);

				final Voucher voucher = new Voucher();
				voucher.setId(obj[3].toString());
				voucher.setVoucherCode(obj[4].toString());
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
				payment.setId(obj[17].toString());
				final File file = new File();
				file.setId(obj[18].toString());
				payment.setFile(file);
				final BankPayment bankPayment = new BankPayment();
				bankPayment.setId(obj[19].toString());
				payment.setBankPayment(bankPayment);
				payment.setTotal(BigDecimal.valueOf(Long.valueOf(obj[20].toString())));
				payment.setInvoice(invoice);
				paymentList.add(payment);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve invoice ", e);

		}

		return paymentList;
	}
	@SuppressWarnings("unchecked")
	public List<Payment> getAllPaymentByInvoiceId(String invoiceId) {
		final List<Payment> paymentList = new ArrayList<>();
		try {
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append(
					"SELECT i.id as invoice_id, i.invoice_code, i.user_id, i.voucher_id,v.voucher_code,  i.activity_id, a.title, a.price as activity_price, a.start_date, a.end_date,i.membership_id, ms.status_name, ms.code_status, ms.period_day, ms.price as ms_price, i.ver, i.is_active, p.id as payment_id, p.file_id, p.bank_payment_id, p.total,p.expired, p.subtotal, p.tax_amount, p.disc_amount, bp.account_name, bp.account_number, bp.bank_name ");
			sqlQuery.append("FROM t_payment p ");
			sqlQuery.append("INNER JOIN t_invoice i ON i.id = p.invoice_id ");
			sqlQuery.append("INNER JOIN t_voucher v ON v.id = i.voucher_id ");
			sqlQuery.append("LEFT JOIN t_activity a ON i.activity_id = a.id ");
			sqlQuery.append("INNER JOIN t_bank_payment bp ON p.bank_payment_id = bp.id ");
			sqlQuery.append("LEFT JOIN t_member_status ms ON ms.id =i.membership_id ");
			sqlQuery.append("WHERE p.invoice_id = :invoiceId ");
			
			final List<Object[]> resultList = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setParameter("invoiceId", invoiceId)
					.getResultList();
			
			if (resultList.size() == 0) {
				return null;
			}

			for (Object[] objs : resultList) {
				Object[] obj = objs;
				Payment payment = new Payment();
				Invoice invoice = new Invoice();
				invoice.setId(obj[0].toString());
				invoice.setInvoiceCode(obj[1].toString());

				final User user = new User();
				user.setId(obj[2].toString());
				invoice.setUser(user);

				final Voucher voucher = new Voucher();
				voucher.setId(obj[3].toString());
				voucher.setVoucherCode(obj[4].toString());
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
				payment.setId(obj[17].toString());
				final File file = new File();
				file.setId(obj[18].toString());
				payment.setFile(file);
				final BankPayment bankPayment = new BankPayment();
				bankPayment.setId(obj[19].toString());
				bankPayment.setAccountName(obj[25].toString());
				bankPayment.setAccountNumber(obj[26].toString());
				bankPayment.setBankName(obj[27].toString());
				payment.setBankPayment(bankPayment);
				payment.setTotal(BigDecimal.valueOf(Long.valueOf(obj[20].toString())));
				payment.setExpired(Timestamp.valueOf(obj[21].toString()).toLocalDateTime());
				payment.setSubtotal(BigDecimal.valueOf(Long.valueOf(obj[22].toString())));
				payment.setTaxAmount(BigDecimal.valueOf(Long.valueOf(obj[23].toString())));
				payment.setDiscAmount(BigDecimal.valueOf(Long.valueOf(obj[24].toString())));
				payment.setInvoice(invoice);
				paymentList.add(payment);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve invoice ", e);

		}
		return paymentList;
	}

	

	@Override
	public Optional<Payment> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Payment.class, id));

	}

}
