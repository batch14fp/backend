package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoPollingReqInsert;
import com.lawencon.community.pojo.post.PojoPollingReqUpdate;
import com.lawencon.community.pojo.post.PojoPollingResponReq;
import com.lawencon.community.pojo.post.PojoPollingResponRes;
import com.lawencon.community.service.PollingService;
import com.lawencon.community.service.PostService;

@RestController
@RequestMapping("pollings")
public class PollingController {
	private PollingService pollingService;
	private PostService postService;
	
	public PollingController (final PollingService pollingService, final PostService postService) {
		this.pollingService = pollingService;
		this.postService = postService;
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPolling(@RequestBody PojoPollingReqInsert data){
		PojoInsertRes resGet = pollingService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updatePolling(@RequestBody PojoPollingReqUpdate data){
		PojoUpdateRes resGet = pollingService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deletePolling(@PathVariable ("id")String id) throws Exception{
		PojoRes resDelete = pollingService.delete(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
	@PostMapping("/vote")
	public ResponseEntity<PojoPollingResponRes> getAllCountOption(@RequestBody PojoPollingResponReq data) throws Exception{
		PojoPollingResponRes resGet = pollingService.insertOptionPolling(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@DeleteMapping("/unvote/{id}")
	public ResponseEntity<PojoRes> deletePollingRespon(@PathVariable ("id")String id) throws Exception{
		PojoRes resDelete = postService.deletePostResponById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
	
}
