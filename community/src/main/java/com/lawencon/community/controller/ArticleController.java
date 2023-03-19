package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
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
import com.lawencon.community.pojo.article.PojoArticleInsertReq;
import com.lawencon.community.pojo.article.PojoArticleUpdateReq;
import com.lawencon.community.pojo.article.PojoResGetArticle;
import com.lawencon.community.pojo.article.PojoResGetArticleData;
import com.lawencon.community.service.ArticleService;
import com.lawencon.community.service.PaginationService;

@RestController
@RequestMapping("articles")
public class ArticleController {
	private ArticleService articleService;
	private PaginationService paginationService;
	
	public ArticleController(final ArticleService articleService, final  PaginationService paginationService) {
		this.articleService = articleService;
		this.paginationService = paginationService;
	}
	
	@GetMapping
	public ResponseEntity<PojoResGetArticle> getData(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) {
	        final PojoResGetArticle dataList = articleService.getAll(page, size);
	        int totalCount = articleService.getTotalCount();
	        int pageCount = paginationService.getPageCount(totalCount, size);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("X-Total-Count", String.valueOf(totalCount));
			headers.add("X-Total-Pages", String.valueOf(pageCount));
	        return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	    }
	
	
	
	@GetMapping("/most-viewer")
	public ResponseEntity<List<PojoResGetArticleData>> getAllByMostViewer(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) {
	        final List<PojoResGetArticleData> dataList = articleService.getAllByMostViewer(page, size);
	        int totalCount = articleService.getTotalCount();
	        int pageCount = paginationService.getPageCount(totalCount, size);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("X-Total-Count", String.valueOf(totalCount));
			headers.add("X-Total-Pages", String.valueOf(pageCount));
	        return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	    }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<PojoResGetArticleData> getArticle(@PathVariable ("id")String id){
		PojoResGetArticleData resGet = articleService.getById(id);
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteArticle(@PathVariable ("id")String id){
		PojoRes resDelete = articleService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
}
