package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.BankPaymentDao;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.model.Category;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.bankpayment.PojoResGetBankPayment;

public class BankPaymentService extends BaseService<PojoResGetBankPayment> {
	private final BankPaymentDao bankPaymentDao;

	public BankPaymentService(final BankPaymentDao bankPaymentDao) {
		this.bankPaymentDao = bankPaymentDao;
	}

	@Override
	public List<PojoResGetBankPayment> getAll() {
		List<PojoResGetBankPayment> res = new ArrayList<>();
		bankPaymentDao.getAll().forEach(data -> {
			PojoResGetBankPayment bankPayment = new PojoResGetBankPayment();
			bankPayment.setBankPaymentId(data.getId());
			bankPayment.setBankPaymentName(data.getBankName());
			bankPayment.setIsActive(data.getIsActive());
			res.add(bankPayment);

		});

		return res;
	}
	
	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = bankPaymentDao.deleteById(Category.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}
	
	public PojoRes save(PojoResGetBankPayment data) {
		ConnHandler.begin();


		BankPayment bankPayment = new BankPayment();

		if (data.getBankPaymentId() != null) {

			bankPayment = bankPaymentDao.getByIdAndDetach(data.getBankPaymentId()).get();
			bankPayment.setBankName(data.getBankPaymentName());
			bankPayment.setIsActive(data.getIsActive());
			bankPayment.setVersion(data.getVer());;
			
			

		} else {
			bankPayment.setBankName(data.getBankPaymentName());
			bankPayment.setIsActive(true);
			
		}

		bankPaymentDao.save(bankPayment);
		ConnHandler.commit();

		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	
	
	
	

}
