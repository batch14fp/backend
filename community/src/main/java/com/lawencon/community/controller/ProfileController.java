package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.profile.PojoPasswordUpdateReq;
import com.lawencon.community.pojo.profile.PojoProfileUpdateReq;
import com.lawencon.community.pojo.profile.PojoResGetProfileDetail;
import com.lawencon.community.service.ProfileService;
import com.lawencon.community.service.UserService;

@RestController
@RequestMapping("profiles")
public class ProfileController {
	private ProfileService profileService;
	private UserService userService;
	
	public ProfileController(final ProfileService profileService, final UserService userService ) {
		this.profileService = profileService;
		this.userService = userService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PojoResGetProfileDetail> getProfileDetail(@PathVariable("id") String id){
		PojoResGetProfileDetail resGet = profileService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<PojoUpdateRes>updateProfile(@RequestBody PojoProfileUpdateReq data){
		PojoUpdateRes resGet = profileService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PutMapping("/password")
	public ResponseEntity<PojoUpdateRes>updatePass(@RequestBody PojoPasswordUpdateReq data){
		PojoUpdateRes resGet = userService.updatePassword(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	
}
