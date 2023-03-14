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
import com.lawencon.community.pojo.article.PojoArticleInsertReq;
import com.lawencon.community.pojo.article.PojoArticleUpdateReq;
import com.lawencon.community.pojo.article.PojoResGetArticle;
import com.lawencon.community.service.ArticleService;

@RestController
@RequestMapping("articles")
public class ArticleController {
	private ArticleService articleService;
	
	public ArticleController(final ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@GetMapping
	public ResponseEntity<List<PojoResGetArticle>> getAllArticle(){
		List<PojoResGetArticle> resGet = articleService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PojoResGetArticle> getActivity(@PathVariable ("id")String id){
		PojoResGetArticle resGet = articleService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertArticle(@RequestBody PojoArticleInsertReq data){
		PojoInsertRes resGet = articleService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateArticle(@RequestBody PojoArticleUpdateReq data){
		PojoUpdateRes resGet = articleService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<PojoRes> deletePost(@RequestBody String id){
		PojoRes resDelete = articleService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
}
