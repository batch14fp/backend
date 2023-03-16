package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.invoice.PojoInvoiceInsertReq;
import com.lawencon.community.service.InvoiceService;

@RestController
@RequestMapping("invoices")
public class InvoiceController {
	private InvoiceService InvoiceService;
	
	public InvoiceController(final InvoiceService invoiceService) {
		this.InvoiceService = invoiceService;
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertInvoice(@RequestBody PojoInvoiceInsertReq data){
		PojoInsertRes resInsert = InvoiceService.save(data);
		return new ResponseEntity<>(resInsert, HttpStatus.CREATED);
	}
}
