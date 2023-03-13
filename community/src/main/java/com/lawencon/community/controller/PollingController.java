package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoPollingInsertReq;
import com.lawencon.community.pojo.post.PojoPollingUpdateReq;
import com.lawencon.community.service.PollingService;

@RestController
@RequestMapping("pollings")
public class PollingController {
	private PollingService pollingService;
	
	public PollingController (final PollingService pollingService) {
		this.pollingService = pollingService;
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPolling(@RequestBody PojoPollingInsertReq data){
		PojoInsertRes resGet = pollingService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updatePolling(@RequestBody PojoPollingUpdateReq data){
		PojoUpdateRes resGet = pollingService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<PojoRes> deletePost(@RequestBody String id){
		PojoRes resDelete = pollingService.delete(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
}
