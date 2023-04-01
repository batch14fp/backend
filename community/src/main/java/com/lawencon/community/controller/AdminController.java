package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.user.PojoAllUsersRes;
import com.lawencon.community.pojo.user.PojoAllUsersResData;
import com.lawencon.community.pojo.user.PojoSignUpReqInsert;
import com.lawencon.community.service.UserService;

@RestController
@RequestMapping("admin")
public class AdminController {

	private UserService userService;

	
	public AdminController( UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/users")
	public ResponseEntity<PojoAllUsersRes>getAllUsersAdmin(@RequestParam("page") int page,
            @RequestParam("size") int size) {
			PojoAllUsersRes dataList = userService.getAllUser(page, size);
	        return new ResponseEntity<>(dataList,  HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<PojoInsertRes>insertUserAdmin(@RequestBody PojoSignUpReqInsert data){
		PojoInsertRes res = userService.insertUser(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	@GetMapping("/users/{roleCode}")
	public ResponseEntity<List<PojoAllUsersResData>>getAllUserCode(@PathVariable("roleCode")String roleCode){
		List<PojoAllUsersResData> resGet = userService.getAllUserByRole(roleCode);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@DeleteMapping("users/{id}")
	public ResponseEntity<PojoRes> deleteUserAdmin(@PathVariable ("id")String id){
		PojoRes resDelete = userService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}

}
	
	
	
	
	
	
	
	
	
	
	

	
	


