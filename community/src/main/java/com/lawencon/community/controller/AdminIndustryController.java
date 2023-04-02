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
import com.lawencon.community.pojo.industry.PojoIndustryReqInsert;
import com.lawencon.community.pojo.industry.PojoIndustryReqUpdate;
import com.lawencon.community.pojo.industry.PojoIndustryRes;
import com.lawencon.community.service.IndustryService;

@RestController
@RequestMapping("admin/industries")
public class AdminIndustryController {
	private IndustryService industryService;
	
	public AdminIndustryController(final IndustryService industryService) {
		this.industryService = industryService;
	}
	@GetMapping
	public ResponseEntity<List<PojoIndustryRes>> getAllIndustries(){
		List<PojoIndustryRes> resGet = industryService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertIndustry(@RequestBody PojoIndustryReqInsert data){
		PojoInsertRes resGet = industryService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateIndustry(@RequestBody PojoIndustryReqUpdate data){
		PojoUpdateRes resGet = industryService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteIndustry(@PathVariable ("id")String id){
		PojoRes resDelete = industryService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
}
