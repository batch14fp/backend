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
import com.lawencon.community.pojo.post.PojoPostBookmarkReqInsert;
import com.lawencon.community.pojo.post.PojoPostCommentReqInsert;
import com.lawencon.community.pojo.post.PojoPostReqInsert;
import com.lawencon.community.pojo.post.PojoPostLikeReqInsert;
import com.lawencon.community.pojo.post.PojoPostReqUpdate;
import com.lawencon.community.pojo.post.PojoPostRes;
import com.lawencon.community.pojo.post.PojoPostCommentRes;
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

	@GetMapping("/{id}")
	public ResponseEntity<PojoPostRes> getActivity(@PathVariable ("id")String id) throws Exception{
		PojoPostRes resGet = postService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPost(@RequestBody PojoPostReqInsert data){
		PojoInsertRes resGet = postService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PostMapping("/like")
	public ResponseEntity<PojoInsertRes> insertPostLike(@RequestBody PojoPostLikeReqInsert data){
		PojoInsertRes resGet = postService.savePostLike(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PostMapping("/bookmark")
	public ResponseEntity<PojoInsertRes> insertPostBookmark(@RequestBody PojoPostBookmarkReqInsert data){
		PojoInsertRes resGet = postService.savePostBookmark(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PostMapping("/comment")
	public ResponseEntity<PojoInsertRes> insertPostComment(@RequestBody PojoPostCommentReqInsert data){
		PojoInsertRes resGet = postService.savePostComment(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updatePost(@RequestBody PojoPostReqUpdate data){
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
	
	@GetMapping
	public ResponseEntity<List<PojoPostRes>> getData(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) throws Exception {
	        final List<PojoPostRes> dataList = postService.getData(page, size);
	        int totalCount = postService.getTotalCount();
	        int pageCount = paginationService.getPageCount(totalCount, size);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("X-Total-Count", String.valueOf(totalCount));
			headers.add("X-Total-Pages", String.valueOf(pageCount));
	        return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	    }
	
	@GetMapping("/most-like")
	public ResponseEntity<List<PojoPostRes>> getAllPostByMostLike(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) throws Exception{
	        final List<PojoPostRes> dataList = postService.getMostLike(page, size);
	        int totalCount = postService.getTotalCount();
	        int pageCount = paginationService.getPageCount(totalCount, size);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("X-Total-Count", String.valueOf(totalCount));
			headers.add("X-Total-Pages", String.valueOf(pageCount));
	        return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	    }
	@GetMapping("/{id}/comment")
	public ResponseEntity<List<PojoPostCommentRes>> getAllCommentByPostId(@PathVariable ("id")String id,@RequestParam("page") int page,
	                                         @RequestParam("size") int size) throws Exception{
	        final List<PojoPostCommentRes> dataList = postService.getAllCommentByPostId(id, page, size);
	        Long totalCount = postService.getCountComment(id);
	        int pageCount = paginationService.getPageCount(totalCount.intValue(), size);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("X-Total-Count", String.valueOf(totalCount));
			headers.add("X-Total-Pages", String.valueOf(pageCount));
	        return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	    }
	
	@GetMapping("/user")
	public ResponseEntity<List<PojoPostRes>> getAllPostByUser(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) throws Exception{
	        final List<PojoPostRes> dataList = postService.getAllPostByUserId(page, size);
	        int totalCount = postService.getTotalCountByUserId();
	        int pageCount = paginationService.getPageCount(totalCount, size);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("X-Total-Count", String.valueOf(totalCount));
			headers.add("X-Total-Pages", String.valueOf(pageCount));
	        return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	    }
}
