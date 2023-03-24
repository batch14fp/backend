package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.ProfileSocialMediaDao;
import com.lawencon.community.dao.SocialMediaDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.ProfileSocialMedia;
import com.lawencon.community.model.SocialMedia;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminReqInsert;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminReqUpdate;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaRes;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaUserReqInsert;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaUserReqUpdate;
import com.lawencon.community.util.GenerateString;
import com.lawencon.security.principal.PrincipalService;


@Service
public class SocialMediaService {
	private SocialMediaDao socialMediaDao;
	private ProfileSocialMediaDao profileSocialMediaDao;
	private UserDao  userDao;
	private ProfileDao profileDao;
	private FileDao fileDao;
	
	@Autowired
	private PrincipalService principalService;
	
	public SocialMediaService(final FileDao fileDao,final ProfileDao profileDao, final UserDao  userDao, final SocialMediaDao socialMediaDao, final ProfileSocialMediaDao profileSocialMediaDao) {
		this.socialMediaDao = socialMediaDao;
		this.profileSocialMediaDao = profileSocialMediaDao;
		this.userDao = userDao;
		this.profileDao = profileDao;
		this.fileDao = fileDao;
				
	}
	
	public List<PojoSocialMediaRes> getAll() {
		final List<PojoSocialMediaRes> socialMedias = new ArrayList<>();
		socialMediaDao.getAll().forEach(data -> {
			PojoSocialMediaRes pojoResGetSocialMedia = new PojoSocialMediaRes();
			pojoResGetSocialMedia.setSocialMediaId(socialMediaDao.getByIdRef(data.getId()).getId());
			pojoResGetSocialMedia.setPlatformName(data.getPlatformName());
			pojoResGetSocialMedia.setIsActive(data.getIsActive());
			pojoResGetSocialMedia.setVer(data.getVersion());
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

	public PojoInsertRes save(PojoSocialMediaAdminReqInsert data) {
		ConnHandler.begin();

		SocialMedia socialMedia = new SocialMedia();
		final File file = new File();
		file.setFileExtension(data.getFile().getExtension());
		file.setFileName(GenerateString.generateFileName(data.getFile().getExtension()));
		file.setFileContent(data.getFile().getFileContent());
		file.setIsActive(true);
		final File fileNew = fileDao.save(file);
		socialMedia.setFile(fileNew);
		socialMedia.setPlatformName(data.getPlatformName());

		socialMedia.setIsActive(true);

		final SocialMedia socialMediaNew = socialMediaDao.save(socialMedia);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(socialMediaNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	
	
	public PojoInsertRes userSaveSocialMedia(PojoSocialMediaUserReqInsert data) {
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
	
	public PojoUpdateRes  updateProfileSocialMedia(PojoSocialMediaUserReqUpdate data){
			
		
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

	public PojoUpdateRes update(PojoSocialMediaAdminReqUpdate data) {
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
