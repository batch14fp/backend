package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusInsertReq;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusUpdateReq;
import com.lawencon.community.service.MemberStatusService;

@RestController
@RequestMapping("members/status")
public class MemberStatusController {
	private MemberStatusService memberStatusService;
	public MemberStatusController(final MemberStatusService memberStatusService) {
		this.memberStatusService = memberStatusService;
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPolling(@RequestBody PojoMemberStatusInsertReq data){
		PojoInsertRes resGet = memberStatusService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updatePolling(@RequestBody PojoMemberStatusUpdateReq data){
		PojoUpdateRes resGet = memberStatusService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

}
