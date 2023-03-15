package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.bankpayment.PojoBankPaymentInsertReq;
import com.lawencon.community.pojo.bankpayment.PojoBankPaymentUpdateReq;
import com.lawencon.community.pojo.bankpayment.PojoResGetBankPayment;
import com.lawencon.community.service.BankPaymentService;

@RestController
@RequestMapping("bank-payments")
public class BankPaymentController {

	private BankPaymentService bankPaymentService;

	public BankPaymentController(final BankPaymentService bankPaymentService) {
		this.bankPaymentService = bankPaymentService;
	}

	@GetMapping
	public ResponseEntity<List<PojoResGetBankPayment>> getAllBankPayment() {
		List<PojoResGetBankPayment> resGet = bankPaymentService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertBankPayment(@RequestBody PojoBankPaymentInsertReq data) {
		PojoInsertRes resGet = bankPaymentService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateBankPayment(@RequestBody PojoBankPaymentUpdateReq data) {
		PojoUpdateRes resGet = bankPaymentService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<PojoRes> deleteBankPayment(@PathVariable ("id")String id) {
		PojoRes resDelete = bankPaymentService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}

}
