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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoPostBookmarkReqInsert;
import com.lawencon.community.pojo.post.PojoPostCommentReqInsert;
import com.lawencon.community.pojo.post.PojoPostCommentReqUpdate;
import com.lawencon.community.pojo.post.PojoPostCommentRes;
import com.lawencon.community.pojo.post.PojoPostLikeReqInsert;
import com.lawencon.community.pojo.post.PojoPostReqInsert;
import com.lawencon.community.pojo.post.PojoPostReqUpdate;
import com.lawencon.community.pojo.post.PojoPostRes;
import com.lawencon.community.pojo.posttype.PojoPostTypeRes;
import com.lawencon.community.service.PostService;
import com.lawencon.community.service.PostTypeService;

@RestController
@RequestMapping("posts")
public class PostController {
	private PostService postService;
	private PostTypeService postTypeService;
	
	public PostController(final PostTypeService postTypeService, final PostService postService) {
		this.postService = postService;
		this.postTypeService = postTypeService;
	}


	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPost(@RequestBody PojoPostReqInsert data){
		PojoInsertRes resGet = postService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updatePost(@RequestBody PojoPostReqUpdate data){
		PojoUpdateRes resGet = postService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<PojoPostRes>> getData(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) throws Exception {
	        final List<PojoPostRes> dataList = postService.getData(page, size);
	        return new ResponseEntity<>(dataList,  HttpStatus.OK);
	    }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<PojoPostRes> getActivity(@PathVariable("id") String id) throws Exception {
	    PojoPostRes resGet = postService.getById(id);
	    if (resGet == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    } else {
	        return ResponseEntity.ok(resGet);
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deletePost(@PathVariable ("id")String id){
		PojoRes resDelete = postService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
	
	@PostMapping("/like")
	public ResponseEntity<PojoInsertRes> insertPostLike(@RequestBody PojoPostLikeReqInsert data){
		PojoInsertRes resGet = postService.savePostLike(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@DeleteMapping("/like/{id}")
	public ResponseEntity<PojoRes> deletePostLike(@PathVariable ("id")String id){
		PojoRes resDelete = postService.deletePostLikeById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	
	@PostMapping("/bookmark")
	public ResponseEntity<PojoInsertRes> insertPostBookmark(@RequestBody PojoPostBookmarkReqInsert data){
		PojoInsertRes resGet = postService.savePostBookmark(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@DeleteMapping("/bookmark/{id}")
	public ResponseEntity<PojoRes> deletePostBookmark(@PathVariable ("id")String id){
		PojoRes resDelete = postService.deletePostBookmarkById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	@PostMapping("/comment")
	public ResponseEntity<PojoInsertRes> insertPostComment(@RequestBody PojoPostCommentReqInsert data){
		PojoInsertRes resGet = postService.savePostComment(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@PutMapping("/comment")
	public ResponseEntity<PojoRes> updatePostComment(@RequestBody PojoPostCommentReqUpdate data){
		PojoRes resGet = postService.updatePostComment(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
		
	@GetMapping("/comment/{id}")
	public ResponseEntity<List<PojoPostCommentRes>> getAllCommentByPostId(@PathVariable ("id")String id,@RequestParam("page") int page,
	                                         @RequestParam("size") int size) throws Exception{
	        final List<PojoPostCommentRes> dataList = postService.getAllCommentByPostId(id, page, size);
	        return new ResponseEntity<>(dataList, HttpStatus.OK);
	   }
	@DeleteMapping("/comment/{id}")
	public ResponseEntity<PojoRes> deletePostComment(@PathVariable ("id")String id){
		PojoRes resDelete = postService.deletePostCommentById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	

	@GetMapping("/most-like")
	public ResponseEntity<List<PojoPostRes>> getAllPostByMostLike(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) throws Exception{
	        final List<PojoPostRes> dataList = postService.getMostLike(page, size);
	        return new ResponseEntity<>(dataList,  HttpStatus.OK);
	    }

	@GetMapping("/user")
	public ResponseEntity<List<PojoPostRes>> getAllPostByUser(@RequestParam("page") int page,
	                                         @RequestParam("size") int size) throws Exception{
	        final List<PojoPostRes> dataList = postService.getAllPostByUserId(page, size);

	        return new ResponseEntity<>(dataList,  HttpStatus.OK);
	    }
	

	@GetMapping("/my-post")
	public ResponseEntity<List<PojoPostRes>> getAllPostByisLikeOrisBookmark(@RequestParam("page") int page,
	                                         @RequestParam("size") int size, @RequestParam(defaultValue="bookmark") String criteria) throws Exception {
	        final List<PojoPostRes> dataList = postService.getAllPostByLikeOrBookmark(page, size, criteria);
	        return new ResponseEntity<>(dataList,  HttpStatus.OK);
	    }
	@GetMapping("/post-types")
	public ResponseEntity<List<PojoPostTypeRes>> getAllPostType(){
		List<PojoPostTypeRes> resGet = postTypeService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
}
