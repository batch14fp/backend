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
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaRes;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminReqInsert;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminReqUpdate;
import com.lawencon.community.service.SocialMediaService;

@RestController
@RequestMapping("admin/social-media")
public class AdminSocialMediaController {

	private SocialMediaService socialMediaService;

	public AdminSocialMediaController(final SocialMediaService socialMediaService) {
		this.socialMediaService = socialMediaService;
	}
	@GetMapping
	public ResponseEntity<List<PojoSocialMediaRes>> getAllSocialMedia() {
		List<PojoSocialMediaRes> resGet = socialMediaService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertSocialMedia(@RequestBody PojoSocialMediaAdminReqInsert data) {
		PojoInsertRes resGet = socialMediaService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateSocialMedia(@RequestBody PojoSocialMediaAdminReqUpdate data) {
		PojoUpdateRes resGet = socialMediaService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteSocialMedia(@PathVariable ("id")String id) {
		PojoRes resDelete = socialMediaService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}

}
