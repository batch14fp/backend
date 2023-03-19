package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	

}
