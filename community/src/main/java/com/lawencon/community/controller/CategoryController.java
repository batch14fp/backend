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
import com.lawencon.community.pojo.category.PojoCategoryInsertReq;
import com.lawencon.community.pojo.category.PojoCategoryUpdateReq;
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
	public ResponseEntity<List<PojoResGetCategory>> getAllCategory() throws Exception{
		List<PojoResGetCategory> resGet = categoryService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertCategory(@RequestBody PojoCategoryInsertReq data){
		PojoInsertRes resGet = categoryService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateCategory(@RequestBody PojoCategoryUpdateReq data){
		PojoUpdateRes resGet = categoryService.update(data);	
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteCategory(@PathVariable ("id")String id){
		PojoRes resDelete = categoryService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
}
