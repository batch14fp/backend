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
import com.lawencon.community.pojo.payment.PojoConfirmPaymentReqUpdate;
import com.lawencon.community.pojo.salessetting.PojoSalesSettingRes;
import com.lawencon.community.pojo.salessetting.PojoSalesSettingReqUpdate;
import com.lawencon.community.service.BankPaymentService;
import com.lawencon.community.service.PaymentService;
import com.lawencon.community.service.SalesSettingService;

@RestController
@RequestMapping("admin")
public class AdminController {
	private PaymentService paymentService;
	private SalesSettingService salesSettingService;
	private BankPaymentService bankPaymentService;
	
	
	public AdminController(final BankPaymentService bankPaymentService, final  PaymentService paymentService, final SalesSettingService salesSettingService) {
		this.paymentService = paymentService;
		this.salesSettingService = salesSettingService;
		this.bankPaymentService = bankPaymentService;
		
	}
	
	@PutMapping("/payments")
	public ResponseEntity<PojoRes> updatByAdmin(@RequestBody PojoConfirmPaymentReqUpdate data){
		PojoRes resGet = paymentService.updateByAdmin(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@GetMapping("/sales-setting")
	public ResponseEntity<PojoSalesSettingRes> getSalesSetting(){
		PojoSalesSettingRes resGet = salesSettingService.getSalesSetting();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PutMapping("/sales-setting")
	public ResponseEntity<PojoUpdateRes> updatSetting(@RequestBody PojoSalesSettingReqUpdate data){
		PojoUpdateRes resGet = salesSettingService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@GetMapping("/bank-payments")
	public ResponseEntity<List<PojoBankPaymentRes>> getAllBankPayment() {
		List<PojoBankPaymentRes> resGet = bankPaymentService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	

	@PostMapping ("/bank-payments")
	public ResponseEntity<PojoInsertRes> insertBankPayment(@RequestBody PojoBankPaymentReqInsert data) {
		PojoInsertRes resGet = bankPaymentService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@PutMapping ("/bank-payments")
	public ResponseEntity<PojoUpdateRes> updateBankPayment(@RequestBody PojoBankPaymentReqUpdate data) {
		PojoUpdateRes resGet = bankPaymentService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteBankPayment(@PathVariable ("id")String id) {
		PojoRes resDelete = bankPaymentService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}


	
	

}
