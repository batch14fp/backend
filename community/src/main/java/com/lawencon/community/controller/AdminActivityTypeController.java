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
import com.lawencon.community.pojo.activitytype.PojoActivityTypeReqInsert;
import com.lawencon.community.pojo.activitytype.PojoActivityTypeReqUpdate;
import com.lawencon.community.pojo.activitytype.PojoActivityTypeRes;
import com.lawencon.community.service.ActivityTypeService;

@RestController
@RequestMapping("admin/activity-types")
public class AdminActivityTypeController {
	private ActivityTypeService activityTypeService;
	public AdminActivityTypeController(final ActivityTypeService activityTypeService) {
		this.activityTypeService = activityTypeService;
	}
	@GetMapping
	public ResponseEntity<List<PojoActivityTypeRes>> getAllActivityTypes(){
		List<PojoActivityTypeRes> resGet = activityTypeService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertActivityType(@RequestBody PojoActivityTypeReqInsert data){
		PojoInsertRes resGet = activityTypeService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateActivityType(@RequestBody PojoActivityTypeReqUpdate data){
		PojoUpdateRes resGet = activityTypeService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteActivityType(@PathVariable ("id")String id){
		PojoRes resDelete = activityTypeService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
}
