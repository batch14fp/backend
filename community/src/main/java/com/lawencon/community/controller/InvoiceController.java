package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.invoice.PojoInvoiceInsertReq;
import com.lawencon.community.pojo.invoice.PojoResGetInvoice;
import com.lawencon.community.service.InvoiceService;

@RestController
@RequestMapping("invoices")
public class InvoiceController {
	private InvoiceService invoiceService;
	
	public InvoiceController(final InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertInvoice(@RequestBody PojoInvoiceInsertReq data){
		PojoInsertRes resInsert = invoiceService.save(data);
		return new ResponseEntity<>(resInsert, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PojoResGetInvoice> getInvoiceById(@PathVariable ("id")String id) {
		final PojoResGetInvoice resGet = invoiceService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}


	@GetMapping
	public ResponseEntity<PojoResGetInvoice> getInvoiceByCode(@RequestParam String codeInvoice) throws Exception {
	final PojoResGetInvoice resGet = invoiceService.getByCode(codeInvoice);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	
	

}
