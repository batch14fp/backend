package com.lawencon.community.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.InvoiceDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.Invoice;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.invoice.PojoInvoiceInsertReq;
import com.lawencon.community.util.GenerateString;
import com.lawencon.security.principal.PrincipalService;

@Service
public class InvoiceService {
	private InvoiceDao invoiceDao;
	private UserDao userDao;
	private ActivityDao activityDao;
	private VoucherDao voucherDao;

	
	@Inject
	private PrincipalService principalService;
	
	public InvoiceService(final InvoiceDao invoiceDao, final UserDao userDao, 
			final ActivityDao activityDao, final VoucherDao voucherDao) {
		this.invoiceDao = invoiceDao;
		this.userDao = userDao;
		this.activityDao = activityDao;
		this.voucherDao = voucherDao;
	}
	
	public PojoInsertRes save(PojoInvoiceInsertReq data) {
		ConnHandler.begin();
		final Invoice invoice = new Invoice();
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		invoice.setUser(user);
		
		final Activity activity = activityDao.getByIdRef(data.getActivityId());
		invoice.setActivity(activity);
		
		final Voucher voucher = voucherDao.getByIdRef(Voucher.class, data.getVoucherId());
		invoice.setVoucher(voucher);
		
		invoice.setInvoiceCode(GenerateString.generateInvoice());
		
		final Invoice invoiceNew = invoiceDao.save(invoice);
		ConnHandler.commit();
		
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(invoiceNew.getId());
		pojoInsertRes.setMessage("Save Success!");

		return pojoInsertRes;
	}
	
}
