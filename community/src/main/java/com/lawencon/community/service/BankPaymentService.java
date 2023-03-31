package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.BankPaymentDao;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.bankpayment.PojoBankPaymentReqInsert;
import com.lawencon.community.pojo.bankpayment.PojoBankPaymentReqUpdate;
import com.lawencon.community.pojo.bankpayment.PojoBankPaymentRes;

@Service
public class BankPaymentService extends BaseService<PojoBankPaymentRes> {
	private final BankPaymentDao bankPaymentDao;

	public BankPaymentService(final BankPaymentDao bankPaymentDao) {
		this.bankPaymentDao = bankPaymentDao;
	}

	private void validateBkNotNull(PojoBankPaymentReqInsert bankPayment) {
		if (bankPayment.getAccountNumber() == null) {
			throw new RuntimeException("Account Number be empty.");
		}

	}

	private void validateNonBk(PojoBankPaymentReqInsert bankPayment) {

		if (bankPayment.getBankPaymentName() == null) {
			throw new RuntimeException("Bank Name cannot be empty.");
		}
		if (bankPayment.getAccountName() == null) {
			throw new RuntimeException("Account Name cannot be empty.");
		}
	}

	private void validateNonBk(PojoBankPaymentReqUpdate bankPayment) {

		if (bankPayment.getBankPaymentId() == null) {
			throw new RuntimeException("Bank Payment cannot be empty.");
		}

		if (bankPayment.getVer() == null) {
			throw new RuntimeException("Bank Payment version cannot be empty.");
		}
	}

	private void validateBkNotExist(String id) {
		if (bankPaymentDao.getById(id).isEmpty()) {
			throw new RuntimeException("Bank Payment cannot be empty.");
		}
	}

	@Override
	public List<PojoBankPaymentRes> getAll() {
		final List<PojoBankPaymentRes> res = new ArrayList<>();
		bankPaymentDao.getAll().forEach(data -> {
			PojoBankPaymentRes bankPayment = new PojoBankPaymentRes();
			bankPayment.setBankPaymentId(data.getId());
			bankPayment.setAccountName(data.getAccountName());
			bankPayment.setAccountNumber(data.getAccountNumber());
			bankPayment.setBankPaymentName(data.getBankName());
			bankPayment.setIsActive(data.getIsActive());
			bankPayment.setVer(data.getVersion());
			res.add(bankPayment);
		});

		return res;
	}

	public PojoRes deleteById(String id) {
		validateBkNotExist(id);
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = bankPaymentDao.deleteById(BankPayment.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}

	public PojoInsertRes save(PojoBankPaymentReqInsert data) {
		validateNonBk(data);
		validateBkNotNull(data);

		ConnHandler.begin();
		final BankPayment bankPayment = new BankPayment();
		bankPayment.setBankName(data.getBankPaymentName());
		bankPayment.setAccountName(data.getAccountName());
		bankPayment.setAccountNumber(data.getAccountNumber());
		bankPayment.setIsActive(true);
		final BankPayment bankPaymentNew = bankPaymentDao.save(bankPayment);
		ConnHandler.commit();
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(bankPaymentNew.getId());
		pojoInsertRes.setMessage("Save Success!");
		return pojoInsertRes;
	}

	public PojoUpdateRes update(PojoBankPaymentReqUpdate data) {

		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();

		try {
			validateNonBk(data);

			ConnHandler.begin();
			final BankPayment bankPayment = bankPaymentDao.getByIdRef(data.getBankPaymentId());
			bankPaymentDao.getByIdAndDetach(BankPayment.class, bankPayment.getId());
			bankPayment.setId(bankPayment.getId());
			bankPayment.setBankName(data.getBankPaymentName());
			bankPayment.setAccountName(data.getAccountName());
			bankPayment.setAccountNumber(data.getAccountNumber());
			bankPayment.setIsActive(data.getIsActive());
			bankPayment.setVersion(data.getVer());
			final BankPayment bankPaymentUpdated = bankPaymentDao.save(bankPayment);
			ConnHandler.commit();
			pojoUpdateRes.setId(bankPaymentUpdated.getId());
			pojoUpdateRes.setMessage("Update Success!");
			pojoUpdateRes.setVer(bankPaymentUpdated.getVersion());

		} catch (Exception e) {
			pojoUpdateRes.setId(data.getBankPaymentId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update the data");
		}
		return pojoUpdateRes;
	}

}
