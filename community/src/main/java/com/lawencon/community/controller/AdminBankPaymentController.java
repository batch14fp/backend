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
import com.lawencon.community.pojo.bankpayment.PojoBankPaymentReqInsert;
import com.lawencon.community.pojo.bankpayment.PojoBankPaymentReqUpdate;
import com.lawencon.community.pojo.bankpayment.PojoBankPaymentRes;
import com.lawencon.community.service.BankPaymentService;

@RestController
@RequestMapping("admin/bank-payments")
public class AdminBankPaymentController {

	private BankPaymentService bankPaymentService;

	public AdminBankPaymentController(final BankPaymentService bankPaymentService) {
		this.bankPaymentService = bankPaymentService;
	}

	@GetMapping
	public ResponseEntity<List<PojoBankPaymentRes>> getAllBankPayments() {
		List<PojoBankPaymentRes> resGet = bankPaymentService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertBankPayment(@RequestBody PojoBankPaymentReqInsert data) {
		PojoInsertRes resGet = bankPaymentService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateBankPayment(@RequestBody PojoBankPaymentReqUpdate data) {
		PojoUpdateRes resGet = bankPaymentService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteBankPayment(@PathVariable("id") String id) {
		PojoRes resDelete = bankPaymentService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}

}
