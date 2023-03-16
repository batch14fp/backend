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
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusInsertReq;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusUpdateReq;
import com.lawencon.community.pojo.memberstatus.PojoResGetMemberStatus;
import com.lawencon.community.service.MemberStatusService;

@RestController
@RequestMapping("members/status")
public class MemberStatusController {
	private MemberStatusService memberStatusService;
	public MemberStatusController(final MemberStatusService memberStatusService) {
		this.memberStatusService = memberStatusService;
	}
	
	
	@GetMapping
	public ResponseEntity<List<PojoResGetMemberStatus>>getAll(){
		List<PojoResGetMemberStatus> resGet = memberStatusService.getAll();;
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertMembershipStatus(@RequestBody PojoMemberStatusInsertReq data){
		PojoInsertRes resGet = memberStatusService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateMembershipStatus(@RequestBody PojoMemberStatusUpdateReq data){
		PojoUpdateRes resGet = memberStatusService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteMembershipStatus(@PathVariable ("id")String id){
		PojoRes resDelete = memberStatusService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}

}
