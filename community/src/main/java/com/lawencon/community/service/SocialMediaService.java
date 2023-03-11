package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.SocialMediaDao;
import com.lawencon.community.model.SocialMedia;
<<<<<<< HEAD
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
=======
>>>>>>> 5befb5d6f4a860f48c84b32f9231fc0582a52662
import com.lawencon.community.pojo.socialmedia.PojoResGetSocialMedia;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminInsertReq;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaAdminUpdateReq;


@Service
public class SocialMediaService {
	private final SocialMediaDao socialMediaDao;
	
	public SocialMediaService(SocialMediaDao socialMediaDao) {
		this.socialMediaDao = socialMediaDao;
	}
	
<<<<<<< HEAD


	public List<PojoResGetSocialMedia> getAll() {
		final List<PojoResGetSocialMedia> socialMedias = new ArrayList<>();
		socialMediaDao.getAll().forEach(data -> {
			PojoResGetSocialMedia pojoResGetSocialMedia = new PojoResGetSocialMedia();
			pojoResGetSocialMedia.setSocialMediaId(socialMediaDao.getByIdRef(data.getId()).getId());
			pojoResGetSocialMedia.setPlatformName(data.getPlatformName());
			socialMedias.add(pojoResGetSocialMedia);
		});
		return socialMedias;

=======
	public PojoResGetSocialMedia getById(String id) {
		SocialMedia socialMedia = socialMediaDao.getByIdRef(id).get();
		PojoResGetSocialMedia resGetSocialMedia = new PojoResGetSocialMedia();
		resGetSocialMedia.setPlatformName(socialMedia.getPlatformName());
		resGetSocialMedia.setUrl(socialMedia.getUrl());
		return resGetSocialMedia;
>>>>>>> 5befb5d6f4a860f48c84b32f9231fc0582a52662
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
