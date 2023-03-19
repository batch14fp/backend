package com.lawencon.community.service;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.InvoiceDao;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.dao.SalesSettingDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.Invoice;
import com.lawencon.community.model.Payment;
import com.lawencon.community.model.SalesSettings;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.invoice.PojoInvoiceReqInsert;
import com.lawencon.community.pojo.invoice.PojoInvoiceRes;
import com.lawencon.community.util.GenerateString;
import com.lawencon.security.principal.PrincipalService;

@Service
public class InvoiceService {
	private InvoiceDao invoiceDao;
	private UserDao userDao;
	private ActivityDao activityDao;
	private VoucherDao voucherDao;
	private SalesSettingDao salesSettingDao;
	private PaymentDao paymentDao;

	
	@Inject
	private PrincipalService principalService;
	
	public InvoiceService(final PaymentDao paymentDao,final SalesSettingDao salesSettingDao, final InvoiceDao invoiceDao, final UserDao userDao, 
			final ActivityDao activityDao, final VoucherDao voucherDao) {
		this.invoiceDao = invoiceDao;
		this.userDao = userDao;
		this.activityDao = activityDao;
		this.voucherDao = voucherDao;
		this.paymentDao = paymentDao;
		this.salesSettingDao = salesSettingDao;
	}
	
	public PojoInsertRes save(PojoInvoiceReqInsert data) {
		ConnHandler.begin();
		final Invoice invoice = new Invoice();
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		invoice.setUser(user);
		
		final Voucher voucher = voucherDao.getByIdRef(Voucher.class, data.getVoucherId());
		invoice.setVoucher(voucher);
		final Activity activity = activityDao.getByIdRef(data.getActivityId());
		invoice.setActivity(activity);
		invoice.setInvoiceCode(GenerateString.generateInvoice());
		
		final Invoice invoiceNew = invoiceDao.save(invoice);
		
		
		
		final Payment payment = new Payment();
		final BigDecimal price = invoiceNew.getActivity().getPrice();
		final SalesSettings setting = salesSettingDao.getSalesSetting();
		final BigDecimal taxAmount = price.multiply(BigDecimal.valueOf(setting.getTax()));
		final BigDecimal discAmount =  price.multiply(BigDecimal.valueOf(invoice.getVoucher().getDiscountPercent()));
		
		 payment.setDiscAmount(discAmount);
		 payment.setSubtotal(price.subtract(discAmount));
		 payment.setTaxAmount(taxAmount);
		 payment.setExpired(invoiceNew.getCreatedAt().plusHours(24));
		 payment.setTotal(price.add(taxAmount));
		 payment.setInvoice(invoiceNew);
		 payment.setIsActive(true);
		 payment.setIsPaid(false);
		 paymentDao.save(payment);
		ConnHandler.commit();
		
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(invoiceNew.getId());
		pojoInsertRes.setMessage("Save Success!");

		return pojoInsertRes;
	}
	
	
	
	public PojoInvoiceRes getById (String id) {
		final PojoInvoiceRes res = new PojoInvoiceRes();
		final Invoice invoice = invoiceDao.getById(id).get();
		
		res.setActivityId(invoice.getActivity().getId());
		res.setActivityTitle(invoice.getActivity().getTitle());
		res.setEndDate(invoice.getActivity().getEndDate());
		res.setInvoiceCode(invoice.getInvoiceCode());
		res.setInvoiceId(invoice.getId());
		res.setPrice(invoice.getActivity().getPrice());
		res.setStartDate(invoice.getActivity().getStartDate());
		res.setVoucherId(invoice.getVoucher().getId());
		return res;
		
	}
	
	
	
	public PojoInvoiceRes getByCode(String code) throws Exception {
		final PojoInvoiceRes res = new PojoInvoiceRes();
		final Invoice invoice = invoiceDao.getByInvoiceCode(code);
		res.setActivityId(invoice.getActivity().getId());
		res.setActivityTitle(invoice.getActivity().getTitle());
			res.setEndDate(invoice.getActivity().getEndDate());
		res.setInvoiceCode(invoice.getInvoiceCode());
		res.setInvoiceId(invoice.getId());
		res.setPrice(invoice.getActivity().getPrice());
		res.setStartDate(invoice.getActivity().getStartDate());
		res.setVoucherId(invoice.getVoucher().getId());
		res.setVoucherCode(invoice.getVoucher().getVoucherCode());
		return res;
		
	}
	
	
	
}
