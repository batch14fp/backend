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
import com.lawencon.community.pojo.post.PojoPostBookmarkInsertReq;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostLikeInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.community.pojo.post.PojoResGetAllPost;
import com.lawencon.community.pojo.post.PojoResGetPost;
import com.lawencon.community.service.PaginationService;
import com.lawencon.community.service.PostService;

@RestController
@RequestMapping("posts")
public class PostController {
	private PostService postService;
	private PaginationService paginationService;
	
	public PostController(final PostService postService, final PaginationService paginationService) {
		this.postService = postService;
		this.paginationService = paginationService;
	}
	
//	@GetMapping
//	public ResponseEntity<List<PojoResGetAllPost>> getAllPost(){
//		List<PojoResGetAllPost> resGet = postService.getAll();
//		return new ResponseEntity<>(resGet, HttpStatus.OK);
//	}
//	
	@GetMapping("/{id}")
	public ResponseEntity<PojoResGetPost> getActivity(@PathVariable ("id")String id){
		PojoResGetPost resGet = postService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPost(@RequestBody PojoPostInsertReq data){
		PojoInsertRes resGet = postService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PostMapping("/like")
	public ResponseEntity<PojoInsertRes> insertPostLike(@RequestBody PojoPostLikeInsertReq data){
		PojoInsertRes resGet = postService.savePostLike(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PostMapping("/bookmark")
	public ResponseEntity<PojoInsertRes> insertPostBookmark(@RequestBody PojoPostBookmarkInsertReq data){
		PojoInsertRes resGet = postService.savePostBookmark(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updatePost(@RequestBody PojoPostUpdateReq data){
		PojoUpdateRes resGet = postService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deletePost(@PathVariable ("id")String id){
		PojoRes resDelete = postService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
	@DeleteMapping("/like/{id}")
	public ResponseEntity<PojoRes> deletePostLike(@PathVariable ("id")String id){
		PojoRes resDelete = postService.deletePostLikeById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
	@DeleteMapping("/bookmark/{id}")
	public ResponseEntity<PojoRes> deletePostBookmark(@PathVariable ("id")String id){
		PojoRes resDelete = postService.deletePostBookmarkById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<PojoResGetAllPost>> getData(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) {
	        int offset = (page - 1) * size;
	        final List<PojoResGetAllPost> dataList = postService.getData(offset, size);
	        int totalCount = postService.getTotalCount();
	        int pageCount = paginationService.getPageCount(totalCount, size);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("X-Total-Count", String.valueOf(totalCount));
			headers.add("X-Total-Pages", String.valueOf(pageCount));
	        return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	    }
	
	@GetMapping("/")
	public ResponseEntity<List<PojoResGetAllPost>> getMostLike(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) throws Exception{
	        int offset = (page - 1) * size;
	        final List<PojoResGetAllPost> dataList = postService.getMostLike(offset, size);
	        int totalCount = postService.getTotalCount();
	        int pageCount = paginationService.getPageCount(totalCount, size);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("X-Total-Count", String.valueOf(totalCount));
			headers.add("X-Total-Pages", String.valueOf(pageCount));
	        return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	    }
}
