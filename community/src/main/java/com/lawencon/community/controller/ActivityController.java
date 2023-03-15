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
import com.lawencon.community.pojo.activity.PojoActivityInsertReq;
import com.lawencon.community.pojo.activity.PojoActivityUpdateReq;
import com.lawencon.community.pojo.activity.PojoResGetActivity;
import com.lawencon.community.service.ActivityService;

@RestController
@RequestMapping("activities")
public class ActivityController {
	private ActivityService activityService;
	
	public ActivityController(final ActivityService activityService) {
		this.activityService = activityService;
	}
	
	@GetMapping
	public ResponseEntity<List<PojoResGetActivity>> getAllActivity(){
		List<PojoResGetActivity> resGet = activityService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PojoResGetActivity> getActivity(@PathVariable ("id")String id){
		PojoResGetActivity resGet = activityService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertActivity(@RequestBody PojoActivityInsertReq data){
		PojoInsertRes resGet = activityService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateActivity(@RequestBody PojoActivityUpdateReq data){
		PojoUpdateRes resGet = activityService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteActivity(@PathVariable ("id")String id){
		PojoRes resDelete = activityService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
}
