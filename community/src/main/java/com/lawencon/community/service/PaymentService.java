package com.lawencon.community.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.RoleEnum;
import com.lawencon.community.dao.BankPaymentDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.dao.SalesSettingDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.WalletDao;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Payment;
import com.lawencon.community.model.SalesSettings;
import com.lawencon.community.model.Wallet;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.payment.PojoConfirmPaymentReqUpdate;
import com.lawencon.community.pojo.payment.PojoPaymentReqInsert;
import com.lawencon.community.pojo.payment.PojoUserPaymentReqUpdate;
import com.lawencon.community.util.GenerateString;

@Service
public class PaymentService {
	private PaymentDao paymentDao;
	private BankPaymentDao bankPaymentDao;
	private FileDao fileDao;
	private WalletDao walletDao;
	private SalesSettingDao salesSettingDao;
	private UserDao userDao;
	
	public PaymentService(final UserDao userDao, final SalesSettingDao salesSettingDao, final WalletDao walletDao, final FileDao fileDao, final BankPaymentDao bankPaymentDao, final PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
		this.bankPaymentDao = bankPaymentDao;
		this.fileDao = fileDao;
		this.walletDao = walletDao;
		this.salesSettingDao = salesSettingDao;
		this.userDao = userDao;
		
	}
	
	
	 public PojoRes save(PojoPaymentReqInsert data) {
		 ConnHandler.begin();
		 final Payment payment = new Payment();
		 
		 payment.setSubtotal(null);
		 payment.setTaxAmount(null);
		 payment.setExpired(null);
		 payment.setTotal(null);
		 payment.setDiscAmount(null);
		 payment.setIsPaid(false);
		 payment.setBankPayment(null);
		 
		 
		 
		final PojoRes res = new PojoRes();
		
		
		res.setMessage("Transaction proof uploaded successfully!");
		return res;
		 
		 
	 }
	 public PojoRes updateByAdmin(PojoConfirmPaymentReqUpdate data) {
		 ConnHandler.begin();
		 final Payment payment = paymentDao.getByIdRef(data.getPaymentId());
		 paymentDao.getByIdAndDetach(payment.getId());
		 payment.setVersion(data.getVer());
		 payment.setIsPaid(data.getIsPaid());
		 
		 if(data.getIsPaid()) {
			 
			 final SalesSettings setting = salesSettingDao.getSalesSetting();
			 
			 final Wallet wallet = walletDao.getByUserId(payment.getInvoice().getActivity().getUser().getId());
			 walletDao.getByIdAndDetach(Wallet.class, wallet.getId());
			 wallet.setBalance(payment.getDiscAmount().multiply(BigDecimal.valueOf(setting.getMemberIncome())));
			 
			 walletDao.saveAndFlush(wallet);
			 
			 
			 
			 final Wallet walletSystem = walletDao.getByUserId(userDao.getAllUserByRoleId(RoleEnum.SYSTEM.getRoleCode()).get(0).getId());
			 walletDao.getByIdAndDetach(Wallet.class, walletSystem.getId());
			 walletSystem.setBalance(payment.getDiscAmount().multiply(BigDecimal.valueOf(setting.getSystemIncome())));
			 
			 walletDao.saveAndFlush(wallet);
		
		 
		 }
		 paymentDao.saveAndFlush(payment);
		 ConnHandler.commit();
		 final PojoRes res = new PojoRes();
		 res.setMessage("Sava Success");
		return res;
		 
		 
	 }
	 
	 
	 
	 public PojoRes updateByUser(PojoUserPaymentReqUpdate data) {
		 ConnHandler.begin();
		 final Payment payment = paymentDao.getByIdRef(data.getPaymentId());
		 paymentDao.getByIdAndDetach(payment.getId());
		 payment.setVersion(data.getVer());
		 
		 final BankPayment bankPayment = bankPaymentDao.getByIdRef(data.getBankPaymentId());
		 payment.setBankPayment(bankPayment);
		 
		 final File file = new File();
		 file.setFileExtension(data.getFileExtension());
		 file.setFileName(GenerateString.generateFileName(data.getFileExtension()));
		 file.setFileContent(data.getFileContent());
		 final File fileNew = fileDao.save(file);
		 
		 payment.setFile(fileNew);
		 
		 paymentDao.saveAndFlush(payment);
		 ConnHandler.commit();
		 final PojoRes res = new PojoRes();
		 res.setMessage("Transaction proof uploaded successfully!");
		return res;
		
		 
		 
	 }
	 
	 
	 
	
	

}
