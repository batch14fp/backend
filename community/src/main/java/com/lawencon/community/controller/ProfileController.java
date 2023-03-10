package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.profile.PojoPasswordUpdateReq;
import com.lawencon.community.pojo.profile.PojoProfileUpdateReq;
import com.lawencon.community.pojo.profile.PojoProfileVerificationReqInsert;
import com.lawencon.community.pojo.profile.PojoProfileVerifyReqInsert;
import com.lawencon.community.pojo.profile.PojoResGetProfileDetail;
import com.lawencon.community.service.ProfileService;

@RestController
@RequestMapping("profiles")
public class ProfileController {
	private ProfileService profileService;
	
	public ProfileController(final ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PojoResGetProfileDetail> getProfileDetail(@PathVariable("id") String id){
		PojoResGetProfileDetail resGet = profileService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<PojoProfileUpdateReq>updateProfile(@RequestBody PojoProfileUpdateReq data){
		PojoProfileUpdateReq resGet = profileService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PutMapping("/password")
	public ResponseEntity<PojoPasswordUpdateReq>updatePass(@RequestBody PojoPasswordUpdateReq data){
		PojoPasswordUpdateReq resGet = profileService.updatePassword(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
//	@PostMapping("/verification")
//	public ResponseEntity<PojoInsertRes>getVerification(@RequestBody PojoProfileVerificationReqInsert data){
//		 PojoInsertRes resGet = profileService.insertVerification(data);
//		 return new ResponseEntity<>(resGet, HttpStatus.CREATED);
//	}
//	
//	@PostMapping("/verification/verify")
//	public ResponseEntity<PojoInsertRes>getVerify(@RequestBody PojoProfileVerifyReqInsert data){
//		 PojoInsertRes resGet = profileService.insertVerify(data);
//		 return new ResponseEntity<>(resGet, HttpStatus.CREATED);
//	}
}
