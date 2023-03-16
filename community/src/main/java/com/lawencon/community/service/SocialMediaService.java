package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.ProfileSocialMediaDao;
import com.lawencon.community.dao.SocialMediaDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.ProfileSocialMedia;
import com.lawencon.community.model.SocialMedia;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.socialmedia.PojoResGetSocialMedia;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminInsertReq;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminUpdateReq;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaUserInsertReq;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaUserUpdateReq;
import com.lawencon.security.principal.PrincipalService;


@Service
public class SocialMediaService {
	private SocialMediaDao socialMediaDao;
	private ProfileSocialMediaDao profileSocialMediaDao;
	private UserDao  userDao;
	private ProfileDao profileDao;
	
	@Inject
	private PrincipalService principalService;
	
	public SocialMediaService(final ProfileDao profileDao, final UserDao  userDao, final SocialMediaDao socialMediaDao, final ProfileSocialMediaDao profileSocialMediaDao) {
		this.socialMediaDao = socialMediaDao;
		this.profileSocialMediaDao = profileSocialMediaDao;
		this.userDao = userDao;
		this.profileDao = profileDao;
				
	}
	


	public List<PojoResGetSocialMedia> getAll() {
		final List<PojoResGetSocialMedia> socialMedias = new ArrayList<>();
		socialMediaDao.getAll().forEach(data -> {
			PojoResGetSocialMedia pojoResGetSocialMedia = new PojoResGetSocialMedia();
			pojoResGetSocialMedia.setSocialMediaId(socialMediaDao.getByIdRef(data.getId()).getId());
			pojoResGetSocialMedia.setPlatformName(data.getPlatformName());
			socialMedias.add(pojoResGetSocialMedia);
		});
		return socialMedias;

	}

	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = socialMediaDao.deleteById(SocialMedia.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}

	public PojoInsertRes save(PojoSocialMediaAdminInsertReq data) {
		ConnHandler.begin();

		SocialMedia socialMedia = new SocialMedia();

		socialMedia.setPlatformName(data.getPlatformName());

		socialMedia.setIsActive(true);

		final SocialMedia socialMediaNew = socialMediaDao.save(socialMedia);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(socialMediaNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	
	
	public PojoInsertRes userSaveSocialMedia(PojoSocialMediaUserInsertReq data) {
		ConnHandler.begin();

		final ProfileSocialMedia profileSocialMedia = new ProfileSocialMedia();
		final User user = userDao.getById(principalService.getAuthPrincipal()).get();
		final Profile profile = profileDao.getByIdRef(user.getProfile().getId());
		profileSocialMedia.setProfile(profile);
		profileSocialMedia.setUrl(data.getUrl());
		
		
		
		final SocialMedia socialMedia = socialMediaDao.getByIdRef(data.getSocialMediaId());
		profileSocialMedia.setSocialMedia(socialMedia);
		profileSocialMedia.setIsActive(true);

		final ProfileSocialMedia socialMediaNew = profileSocialMediaDao.save(profileSocialMedia);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(socialMediaNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	public PojoUpdateRes  updateProfileSocialMedia(PojoSocialMediaUserUpdateReq data){
			
		
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
		
		ConnHandler.begin();
			final ProfileSocialMedia profileSocialMedia = profileSocialMediaDao.getByIdRef(data.getProfileSocialMediaId());
			profileSocialMediaDao.getByIdAndDetach(ProfileSocialMedia.class, profileSocialMedia.getId());
			
			profileSocialMedia.setUrl(data.getUrl());
			profileSocialMedia.setIsActive(data.getIsActive());
			profileSocialMedia.setVersion(data.getVer());
			socialMediaDao.saveAndFlush(profileSocialMedia);
			ConnHandler.commit();

			pojoUpdateRes.setId(profileSocialMedia.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(profileSocialMedia.getVersion());

		} catch (Exception e) {
			pojoUpdateRes.setId(data.getProfileSocialMediaId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	
	}

	public PojoUpdateRes update(PojoSocialMediaAdminUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			final SocialMedia socialMedia = socialMediaDao.getByIdRef(data.getSocialMediaId());
			socialMediaDao.getByIdAndDetach(SocialMedia.class, socialMedia.getId());
			socialMedia.setId(socialMedia.getId());
			socialMedia.setPlatformName(data.getPlatformName());
			socialMedia.setIsActive(data.getIsActive());
			socialMedia.setVersion(data.getVer());

			final SocialMedia poistionNew = socialMediaDao.saveAndFlush(socialMedia);
			ConnHandler.commit();

			pojoUpdateRes.setId(poistionNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(poistionNew.getVersion());

		} catch (Exception e) {
			pojoUpdateRes.setId(data.getSocialMediaId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	}

}
