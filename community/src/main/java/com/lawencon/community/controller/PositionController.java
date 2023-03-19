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
import com.lawencon.community.pojo.position.PojoPositionReqInsert;
import com.lawencon.community.pojo.position.PojoPositionReqUpdate;
import com.lawencon.community.pojo.position.PojoPostionRes;
import com.lawencon.community.service.PositionService;

@RestController
@RequestMapping("positions")
public class PositionController {
	private PositionService positionService;
	
	public PositionController(final PositionService positionService) {
		this.positionService = positionService;
	}
	
	@GetMapping
	public ResponseEntity<List<PojoPostionRes>> getAllPosition(){
		List<PojoPostionRes> resGet = positionService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPosition(@RequestBody PojoPositionReqInsert data){
		PojoInsertRes resGet = positionService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updatePosition(@RequestBody PojoPositionReqUpdate data){
		PojoUpdateRes resGet = positionService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteCategory(@PathVariable ("id")String id) {
		PojoRes resDelete = positionService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}

}
