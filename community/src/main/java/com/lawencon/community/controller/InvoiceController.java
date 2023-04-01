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
import com.lawencon.community.pojo.invoice.PojoInvoiceMembershipRes;
import com.lawencon.community.pojo.invoice.PojoInvoiceReqInsert;
import com.lawencon.community.pojo.invoice.PojoInvoiceRes;
import com.lawencon.community.service.InvoiceService;

@RestController
@RequestMapping("invoices")
public class InvoiceController {
	private InvoiceService invoiceService;
	
	public InvoiceController(final InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertInvoice(@RequestBody PojoInvoiceReqInsert data){
		PojoInsertRes resInsert = invoiceService.save(data);
		return new ResponseEntity<>(resInsert, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PojoInvoiceRes> getInvoiceById(@PathVariable ("id")String id) {
		final PojoInvoiceRes resGet = invoiceService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@GetMapping("/activity")
	public ResponseEntity<PojoInvoiceRes> getInvoiceActivityByCode(@RequestParam String codeInvoice) throws Exception {
	final PojoInvoiceRes resGet = invoiceService.getByCode(codeInvoice);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@GetMapping("/membership")
	public ResponseEntity<PojoInvoiceMembershipRes> getInvoiceMemberByCode(@RequestParam String codeInvoice) throws Exception {
	final PojoInvoiceMembershipRes resGet = invoiceService.getMembershipInvoiceByCode(codeInvoice);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
}
