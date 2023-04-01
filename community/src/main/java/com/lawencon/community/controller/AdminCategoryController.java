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
import com.lawencon.community.pojo.category.PojoCategoryReqInsert;
import com.lawencon.community.pojo.category.PojoCategoryReqUpdate;
import com.lawencon.community.pojo.category.PojoCategoryRes;
import com.lawencon.community.service.CategoryService;

@RestController
@RequestMapping("admin/categories")
public class AdminCategoryController {
	private CategoryService categoryService;
	
	public AdminCategoryController(final CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<PojoCategoryRes>> getAllCategories() throws Exception{
		List<PojoCategoryRes> resGet = categoryService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertCategory(@RequestBody PojoCategoryReqInsert data){
		PojoInsertRes resGet = categoryService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateCategory(@RequestBody PojoCategoryReqUpdate data){
		PojoUpdateRes resGet = categoryService.update(data);	
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteCategory(@PathVariable ("id")String id){
		PojoRes resDelete = categoryService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
}
