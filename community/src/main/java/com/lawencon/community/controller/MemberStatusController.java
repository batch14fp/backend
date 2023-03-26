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
import com.lawencon.community.pojo.invoice.PojoInvoiceRes;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusReqInsert;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusReqUpdate;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusRes;
import com.lawencon.community.pojo.payment.PojoMembershipPaymentReqInsert;
import com.lawencon.community.service.InvoiceService;
import com.lawencon.community.service.MemberStatusService;

@RestController
@RequestMapping("members/status")
public class MemberStatusController {
	private MemberStatusService memberStatusService;
	private InvoiceService invoiceService;
	
	public MemberStatusController(final InvoiceService invoiceService, final MemberStatusService memberStatusService) {
		this.memberStatusService = memberStatusService;
		this.invoiceService = invoiceService;
	}
	
	
	@GetMapping
	public ResponseEntity<List<PojoMemberStatusRes>>getAll(){
		List<PojoMemberStatusRes> resGet = memberStatusService.getAll();;
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertMembershipStatus(@RequestBody PojoMemberStatusReqInsert data){
		PojoInsertRes resGet = memberStatusService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateMembershipStatus(@RequestBody PojoMemberStatusReqUpdate data){
		PojoUpdateRes resGet = memberStatusService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteMembershipStatus(@PathVariable ("id")String id){
		PojoRes resDelete = memberStatusService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
	
	@PostMapping("/subscription")
	public ResponseEntity<PojoInvoiceRes> subscribtionMembership(@RequestBody PojoMembershipPaymentReqInsert data){
		PojoInvoiceRes resGet = invoiceService.membershipSave(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	
	
	
	
	
	
	
	
	
	

}
