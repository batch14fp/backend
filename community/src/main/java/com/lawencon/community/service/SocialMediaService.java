package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.community.dao.SocialMediaDao;
import com.lawencon.community.pojo.socialmedia.PojoResGetSocialMedia;

@Service
public class SocialMediaService {
	private final SocialMediaDao socialMediaDao;
	
	public SocialMediaService(SocialMediaDao socialMediaDao) {
		this.socialMediaDao = socialMediaDao;
	}
	
	public PojoResGetSocialMedia getByIdSocialMedia(Long id){
		return null;
	}
}
