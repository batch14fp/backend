package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.article.PojoArticleReqInsert;
import com.lawencon.community.pojo.article.PojoArticleReqUpdate;
import com.lawencon.community.pojo.article.PojoArticleRes;
import com.lawencon.community.pojo.article.PojoArticleResData;
import com.lawencon.community.service.ArticleService;

@RestController
@RequestMapping("admin/articles")
public class AdminArticleController {
	private ArticleService articleService;
	
	public AdminArticleController(final ArticleService articleService) {
		this.articleService = articleService;
	
	}
	
	@GetMapping
	public ResponseEntity<PojoArticleRes> getAllArticles(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) {
	        final PojoArticleRes dataList = articleService.getAll(page, size);
	        return new ResponseEntity<>(dataList, HttpStatus.OK);
	    }
	
	@GetMapping("/{id}")
	public ResponseEntity<PojoArticleResData> getDetailArticle(@PathVariable ("id")String id){
		PojoArticleResData resGet = articleService.getByIdForAdmin(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertArticle(@RequestBody PojoArticleReqInsert data){
		PojoInsertRes resGet = articleService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateArticle(@RequestBody PojoArticleReqUpdate data){
		PojoUpdateRes resGet = articleService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteArticle(@PathVariable ("id")String id){
		PojoRes resDelete = articleService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
}
