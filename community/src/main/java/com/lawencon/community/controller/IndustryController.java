package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.industry.PojoIndustryReq;
import com.lawencon.community.pojo.industry.PojoResGetIndustry;
import com.lawencon.community.service.IndustryService;

@RestController
@RequestMapping("industries")
public class IndustryController {
	private IndustryService industryService;
	
	public IndustryController(final IndustryService industryService) {
		this.industryService = industryService;
	}
	
	@GetMapping
	public ResponseEntity<List<PojoResGetIndustry>> getIndustry(){
		List<PojoResGetIndustry> resGet = industryService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertIndustry(@RequestBody PojoIndustryReq data){
		PojoInsertRes resGet = industryService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@PutMapping
	public ResponseEntity<PojoInsertRes> updateIndustry(@RequestBody PojoIndustryReq data){
		PojoInsertRes resGet = industryService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@DeleteMapping
	public ResponseEntity<PojoRes> deleteIndustry(@RequestBody  String id){
		PojoRes resDelete = industryService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
}
