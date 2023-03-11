package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.socialmedia.PojoResGetSocialMedia;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminInsertReq;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminUpdateReq;
import com.lawencon.community.service.SocialMediaService;

@RestController
@RequestMapping("social-media")
public class SocialMediaController {

	private SocialMediaService socialMediaService;

	public SocialMediaController(final SocialMediaService socialMediaService) {
		this.socialMediaService = socialMediaService;
	}

	@GetMapping
	public ResponseEntity<List<PojoResGetSocialMedia>> getAllSocialMedia() {
		List<PojoResGetSocialMedia> resGet = socialMediaService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertSocialMedia(@RequestBody PojoSocialMediaAdminInsertReq data) {
		PojoInsertRes resGet = socialMediaService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateSocialMedia(@RequestBody PojoSocialMediaAdminUpdateReq data) {
		PojoUpdateRes resGet = socialMediaService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<PojoRes> deleteSocialMedia(@RequestBody String id) {
		PojoRes resDelete = socialMediaService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}

}
