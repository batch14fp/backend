package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.profile.PojoPasswordReqUpdate;
import com.lawencon.community.pojo.profile.PojoProfileDetailRes;
import com.lawencon.community.pojo.profile.PojoProfileReqUpdate;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaUserReqInsert;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaUserReqUpdate;
import com.lawencon.community.service.ProfileService;
import com.lawencon.community.service.SocialMediaService;
import com.lawencon.community.service.UserService;

@RestController
@RequestMapping("profiles")
public class ProfileController {
	private ProfileService profileService;
	private UserService userService;
	private SocialMediaService socialMediaService;
	
	public ProfileController(final SocialMediaService socialMediaService, final ProfileService profileService, final UserService userService ) {
		this.profileService = profileService;
		this.userService = userService;
		this.socialMediaService = socialMediaService;
	}
	
	@GetMapping
	public ResponseEntity<PojoProfileDetailRes> getDetailProfile() throws Exception{

		PojoProfileDetailRes resGet = profileService.getDetailProfile();

		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<PojoUpdateRes>updateProfile(@RequestBody PojoProfileReqUpdate data) throws Exception{
		PojoUpdateRes resGet = profileService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@PutMapping("edit/social-media")
	public ResponseEntity<PojoUpdateRes>updateUserProfileSocialMedia(@RequestBody PojoSocialMediaUserReqUpdate data) throws Exception{
		PojoUpdateRes resGet = socialMediaService.updateProfileSocialMedia(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@PostMapping("add/social-media")
	public ResponseEntity<PojoInsertRes>insertUserProfileSocialMedia(@RequestBody PojoSocialMediaUserReqInsert data) throws Exception{
		PojoInsertRes resGet = socialMediaService.userSaveSocialMedia(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@PutMapping("/edit/password")
	public ResponseEntity<PojoUpdateRes>updatePass(@RequestBody PojoPasswordReqUpdate data){
		PojoUpdateRes resGet = userService.updatePassword(data);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	
	
}
