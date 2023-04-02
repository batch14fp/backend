package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.community.pojo.article.PojoArticleResData;
import com.lawencon.community.service.ArticleService;

@RestController
@RequestMapping("articles")
public class ArticleController {
	private ArticleService articleService;
	public ArticleController(final ArticleService articleService) {
		this.articleService = articleService;

	}
	@GetMapping
	public ResponseEntity<List<PojoArticleResData>> getAllArticles(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) {
	        final List<PojoArticleResData> dataList = articleService.getAllForMember(page, size);
	        return new ResponseEntity<>(dataList,HttpStatus.OK);
	    }
	@GetMapping("/most-viewer")
	public ResponseEntity<List<PojoArticleResData>> getAllByMostViewer(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) {
	        final List<PojoArticleResData> dataList = articleService.getAllByMostViewer(page, size);
	        return new ResponseEntity<>(dataList,HttpStatus.OK);
	    }
	@GetMapping("/{id}")
	public ResponseEntity<PojoArticleResData> getArticleIdForMember(@PathVariable ("id")String id){
		PojoArticleResData resGet = articleService.getByIdForMember(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	

}
