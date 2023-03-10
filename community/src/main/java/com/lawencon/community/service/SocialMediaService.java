package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.community.dao.SocialMediaDao;
import com.lawencon.community.model.SocialMedia;
import com.lawencon.community.pojo.socialmedia.PojoResGetSocialMedia;

@Service
public class SocialMediaService {
	private final SocialMediaDao socialMediaDao;
	
	public SocialMediaService(SocialMediaDao socialMediaDao) {
		this.socialMediaDao = socialMediaDao;
	}
	
	public PojoResGetSocialMedia getById(String id) {
		SocialMedia socialMedia = socialMediaDao.getByIdRef(id).get();
		PojoResGetSocialMedia resGetSocialMedia = new PojoResGetSocialMedia();
		resGetSocialMedia.setPlatformName(socialMedia.getPlatformName());
		resGetSocialMedia.setUrl(socialMedia.getUrl());
		return resGetSocialMedia;
	}
}
