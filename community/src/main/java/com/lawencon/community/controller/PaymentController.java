package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.payment.PojoConfirmPaymentUpdateReq;
import com.lawencon.community.pojo.payment.PojoUserPaymentUpdateReq;
import com.lawencon.community.service.PaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {
	private PaymentService paymentService;
	
	public PaymentController(final  PaymentService paymentService) {
		this.paymentService = paymentService;
		
	}
	
	@PutMapping("/admin")
	public ResponseEntity<PojoRes> updatByAdmin(@RequestBody PojoConfirmPaymentUpdateReq data){
		PojoRes resGet = paymentService.updateByAdmin(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@PutMapping("/user")
	public ResponseEntity<PojoRes> updateByUser(@RequestBody PojoUserPaymentUpdateReq data){
		PojoRes resGet = paymentService.updateByUser(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

}
