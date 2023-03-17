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
import com.lawencon.community.pojo.payment.PojoConfirmPaymentUpdateReq;
import com.lawencon.community.pojo.salessetting.PojoResGetSalesSetting;
import com.lawencon.community.pojo.salessetting.PojoSalesSettingUpdateReq;
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
	public ResponseEntity<PojoRes> updatByAdmin(@RequestBody PojoConfirmPaymentUpdateReq data){
		PojoRes resGet = paymentService.updateByAdmin(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@GetMapping("/sales-setting")
	public ResponseEntity<PojoResGetSalesSetting> getSalesSetting(){
		PojoResGetSalesSetting resGet = salesSettingService.getSalesSetting();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PutMapping("/sales-setting")
	public ResponseEntity<PojoUpdateRes> updatSetting(@RequestBody PojoSalesSettingUpdateReq data){
		PojoUpdateRes resGet = salesSettingService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@GetMapping("/bank-payments")
	public ResponseEntity<List<PojoResGetBankPayment>> getAllBankPayment() {
		List<PojoResGetBankPayment> resGet = bankPaymentService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	

	@PostMapping ("/bank-payments")
	public ResponseEntity<PojoInsertRes> insertBankPayment(@RequestBody PojoBankPaymentInsertReq data) {
		PojoInsertRes resGet = bankPaymentService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@PutMapping ("/bank-payments")
	public ResponseEntity<PojoUpdateRes> updateBankPayment(@RequestBody PojoBankPaymentUpdateReq data) {
		PojoUpdateRes resGet = bankPaymentService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteBankPayment(@PathVariable ("id")String id) {
		PojoRes resDelete = bankPaymentService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}


	
	

}
