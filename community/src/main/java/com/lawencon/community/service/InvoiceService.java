package com.lawencon.community.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.InvoiceDao;
import com.lawencon.community.dao.MemberStatusDao;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.dao.SalesSettingDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.Invoice;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Payment;
import com.lawencon.community.model.SalesSettings;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.invoice.PojoInvoiceMembershipRes;
import com.lawencon.community.pojo.invoice.PojoInvoiceReqInsert;
import com.lawencon.community.pojo.invoice.PojoInvoiceRes;
import com.lawencon.community.pojo.payment.PojoMembershipPaymentReqInsert;
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
	private MemberStatusDao memberStatusDao;

	@Autowired
	private PrincipalService principalService;

	

	
	public InvoiceService(final MemberStatusDao memberStatusDao, final PaymentDao paymentDao,
			final SalesSettingDao salesSettingDao, final InvoiceDao invoiceDao, final UserDao userDao,
			final ActivityDao activityDao, final VoucherDao voucherDao) {
		this.invoiceDao = invoiceDao;
		this.userDao = userDao;
		this.activityDao = activityDao;
		this.voucherDao = voucherDao;
		this.paymentDao = paymentDao;
		this.salesSettingDao = salesSettingDao;
		this.memberStatusDao = memberStatusDao;
	}
	
	
	private void validateNonBk(PojoInvoiceReqInsert invoice) {
	
	    if(invoice.getActivityId() == null) {
	        throw new RuntimeException("Activity ID cannot be empty.");
	    }

	}

	
	
	

	public PojoInsertRes save(PojoInvoiceReqInsert data) {
		validateNonBk(data);
		ConnHandler.begin();

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		final Invoice invoice = new Invoice();
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		invoice.setUser(user);

		if (data.getVoucherId() != null) {
			final Voucher voucher = voucherDao.getByIdRef(Voucher.class, data.getVoucherId());
			invoice.setVoucher(voucher);
		}
		final Activity activity = activityDao.getByIdRef(data.getActivityId());
		invoice.setActivity(activity);
		invoice.setInvoiceCode(GenerateString.generateInvoice());

		final Invoice invoiceNew = invoiceDao.save(invoice);

		final Payment payment = new Payment();
		final BigDecimal price = invoiceNew.getActivity().getPrice();
		final SalesSettings setting = salesSettingDao.getSalesSetting();
		final BigDecimal taxAmount = price.multiply(BigDecimal.valueOf(setting.getTax()));
		BigDecimal discAmount = BigDecimal.ZERO;
		BigDecimal subTotal = price;
		if (invoice.getVoucher() != null) {
			if (invoice.getVoucher().getUsedCount() <= invoice.getVoucher().getLimitApplied()) {
				discAmount = price.multiply(BigDecimal.valueOf(invoice.getVoucher().getDiscountPercent()));
				subTotal= price.subtract(discAmount);
			}
		}
			
			payment.setDiscAmount(discAmount);
			payment.setSubtotal(subTotal);
			payment.setTaxAmount(taxAmount);
			payment.setExpired(invoiceNew.getCreatedAt().plusHours(24));
			payment.setTotal(subTotal.add(taxAmount));
			payment.setInvoice(invoiceNew);
			payment.setIsActive(true);
			payment.setIsPaid(false);
			paymentDao.save(payment);
		

			pojoInsertRes.setId(invoiceNew.getId());
			pojoInsertRes.setMessage("Save Success!");
		
		
		ConnHandler.commit();
		return pojoInsertRes;
	}

	public PojoInvoiceRes membershipSave(PojoMembershipPaymentReqInsert data) {
		ConnHandler.begin();
		final PojoInvoiceRes res = new PojoInvoiceRes();

		final Invoice invoice = new Invoice();
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		invoice.setUser(user);

		final MemberStatus memberStatus = memberStatusDao.getByIdRef(data.getMembershipId());
		invoice.setMemberStatus(memberStatus);
		invoice.setInvoiceCode(GenerateString.generateInvoice());
		final Invoice invoiceNew = invoiceDao.save(invoice);

		final Payment payment = new Payment();
		final BigDecimal price = invoiceNew.getMemberStatus().getPrice();
		final SalesSettings setting = salesSettingDao.getSalesSetting();
		final BigDecimal taxAmount = price.multiply(BigDecimal.valueOf(setting.getTax()));
		payment.setSubtotal(price);
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
		res.setInvoiceId(invoiceNew.getId());
		res.setInvoiceCode(invoiceNew.getInvoiceCode());
		res.setMembershipId(invoiceNew.getMemberStatus().getId());
		res.setVer(invoiceNew.getMemberStatus().getVersion());
		res.setIsActive(invoiceNew.getMemberStatus().getIsActive());
		res.setPrice(price);

		return res;
	}

	public PojoInvoiceRes getById(String id) {
		final PojoInvoiceRes res = new PojoInvoiceRes();
		final Invoice invoice = invoiceDao.getById(id).get();
		res.setActivityId(invoice.getActivity().getId());
		res.setActivityTitle(invoice.getActivity().getTitle());
		res.setEndDate(invoice.getActivity().getEndDate());
		res.setInvoiceCode(invoice.getInvoiceCode());
		res.setInvoiceId(invoice.getId());
		res.setVer(invoice.getVersion());
		res.setIsActive(invoice.getIsActive());
		if(invoice.getMemberStatus() != null) {
			res.setMembershipId(invoice.getMemberStatus().getId());
		}

		Long discountNum=null;
		if(invoice.getActivity().getFile()!=null) {
		res.setImageId(invoice.getActivity().getFile().getId());
		}
		res.setPrice(invoice.getActivity().getPrice());
		res.setStartDate(invoice.getActivity().getStartDate());
		if(invoice.getVoucher()!=null) {
		res.setVoucherId(invoice.getVoucher().getId());
		res.setVoucherCode(invoice.getVoucher().getVoucherCode());
		discountNum= (long) (invoice.getVoucher().getDiscountPercent()*100);
		}
		
		res.setDiscountNum(discountNum);
		res.setProvider(invoice.getActivity().getProvider());
		res.setLocation(invoice.getActivity().getActivityLocation());
		return res;

	}
	
	
	
	
	
	
	

	public PojoInvoiceRes getByCode(String code) {
		final PojoInvoiceRes res = new PojoInvoiceRes();
		final Invoice invoice = invoiceDao.getByInvoiceCode(code).get();
		res.setActivityId(invoice.getActivity().getId());
		res.setActivityTitle(invoice.getActivity().getTitle());
		res.setEndDate(invoice.getActivity().getEndDate());
		res.setInvoiceCode(invoice.getInvoiceCode());
		res.setInvoiceId(invoice.getId());
		res.setPrice(invoice.getActivity().getPrice());
		res.setStartDate(invoice.getActivity().getStartDate());
		res.setVoucherId(invoice.getVoucher().getId());
		res.setVoucherCode(invoice.getVoucher().getVoucherCode());
		res.setVer(invoice.getVersion());
		res.setIsActive(invoice.getIsActive());
		return res;
	}

	public PojoInvoiceMembershipRes getMembershipInvoiceByCode(String code) {
		final PojoInvoiceMembershipRes res = new PojoInvoiceMembershipRes();
		final Invoice invoice = invoiceDao.getByInvoiceCode(code).get();
		res.setInvoiceId(invoice.getId());
		res.setMembershipCode(invoice.getMemberStatus().getCodeStatus());
		res.setMembershipId(invoice.getMemberStatus().getId());
		res.setMembershipName(invoice.getMemberStatus().getStatusName());
		res.setPeriod(invoice.getMemberStatus().getPeriodDay());
		res.setPrice(invoice.getMemberStatus().getPrice());
		res.setVer(invoice.getVersion());
		res.setIsActive(invoice.getIsActive());
		return res;
	}
	


}
