package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.category.PojoCategoryReq;
import com.lawencon.community.pojo.category.PojoResGetCategory;
import com.lawencon.community.service.CategoryService;

@RestController
@RequestMapping("categories")
public class CategoryController {
	private CategoryService categoryService;
	
	public CategoryController(final CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<PojoResGetCategory>> getCategory(){
		List<PojoResGetCategory> resGet = categoryService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertCategory(@RequestBody PojoCategoryReq data){
		PojoInsertRes resGet = categoryService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
}
