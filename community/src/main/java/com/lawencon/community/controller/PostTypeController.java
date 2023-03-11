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
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.posttype.PojoPostTypeInsertReq;
import com.lawencon.community.pojo.posttype.PojoPostTypeUpdateReq;
import com.lawencon.community.pojo.posttype.PojoResGetPostType;
import com.lawencon.community.service.PostTypeService;


@RestController
@RequestMapping("post-type")
public class PostTypeController {
	private PostTypeService postTypeService;
	
	public PostTypeController(final PostTypeService postTypeService) {
		this.postTypeService = postTypeService;
	}
	
	@GetMapping
	public ResponseEntity<List<PojoResGetPostType>> getPosition(){
		List<PojoResGetPostType> resGet = postTypeService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPosition(@RequestBody PojoPostTypeInsertReq data){
		PojoInsertRes resGet = postTypeService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updatePosition(@RequestBody PojoPostTypeUpdateReq data){
		PojoUpdateRes resGet = postTypeService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping
	public ResponseEntity<PojoRes> deleteCategory(@RequestBody String id) {
		PojoRes resDelete = postTypeService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
}
