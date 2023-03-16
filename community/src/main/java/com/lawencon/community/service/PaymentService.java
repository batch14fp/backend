package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.BankPaymentDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Payment;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.payment.PojoConfirmPaymentUpdateReq;
import com.lawencon.community.pojo.payment.PojoPaymentInsertReq;
import com.lawencon.community.pojo.payment.PojoUserPaymentUpdateReq;

@Service
public class PaymentService {
	private PaymentDao paymentDao;
	private BankPaymentDao bankPaymentDao;
	private FileDao fileDao;
	
	public PaymentService(final FileDao fileDao, final BankPaymentDao bankPaymentDao, final PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
		this.bankPaymentDao = bankPaymentDao;
		this.fileDao = fileDao;
		
	}
	
	
	 public PojoRes save(PojoPaymentInsertReq data) {
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
	 public PojoRes updateByAdmin(PojoConfirmPaymentUpdateReq data) {
		 ConnHandler.begin();
		 final Payment payment = paymentDao.getByIdRef(data.getPaymentId());
		 paymentDao.getByIdAndDetach(payment.getId());
		 payment.setVersion(data.getVer());
		 payment.setIsPaid(data.getIsPaid());
		 paymentDao.saveAndFlush(payment);
		 ConnHandler.commit();
		 final PojoRes res = new PojoRes();
		 res.setMessage("Sava Success");
		return res;
		 
		 
	 }
	 
	 
	 
	 public PojoRes updateByUser(PojoUserPaymentUpdateReq data) {
		 ConnHandler.begin();
		 final Payment payment = paymentDao.getByIdRef(data.getPaymentId());
		 paymentDao.getByIdAndDetach(payment.getId());
		 payment.setVersion(data.getVer());
		 
		 final BankPayment bankPayment = bankPaymentDao.getByIdRef(data.getBankPaymentId());
		 payment.setBankPayment(bankPayment);
		 
		 final File file = new File();
		 file.setFileName(data.getFileName());
		 file.setFileExtension(data.getFileExtension());
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
