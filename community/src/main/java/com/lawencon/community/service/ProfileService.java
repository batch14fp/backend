package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.MemberStatusDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.ProfileSocialMediaDao;
import com.lawencon.community.dao.SocialMediaDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.profile.PojoProfileDetailRes;
import com.lawencon.community.pojo.profile.PojoProfileReqUpdate;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaRes;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ProfileService {
	private ProfileDao profileDao;
	private MemberStatusDao memberStatusDao;
	private UserDao userDao;
	private ProfileSocialMediaDao profileSocialMediaDao;

	private IndustryDao industryDao;
	private PositionDao positionDao;
	private FileDao fileDao;
	
	@Autowired
	private PrincipalService principalService;

	public ProfileService(final FileDao fileDao, final PositionDao positionDao, final IndustryDao industryDao, final SocialMediaDao socialMediaDao, final ProfileSocialMediaDao profileSocialMediaDao,
			final ProfileDao profileDao, final UserDao userDao, final MemberStatusDao memberStatusDao) {
		this.profileDao = profileDao;
		this.memberStatusDao = memberStatusDao;
		this.userDao = userDao;
		this.fileDao = fileDao;
		this.profileSocialMediaDao = profileSocialMediaDao;
		
		this.industryDao =  industryDao;
		this.positionDao = positionDao;
		

	}

	public PojoProfileDetailRes getById(String id) throws Exception {
		final Profile profile = profileDao.getByIdRef(id);
		final PojoProfileDetailRes resGetProfile = new PojoProfileDetailRes();
		resGetProfile.setUserId(principalService.getAuthPrincipal());
		resGetProfile.setFullname(profile.getFullname());
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		resGetProfile.setEmail(user.getEmail());
		resGetProfile.setCompany(profile.getCompanyName());
		resGetProfile.setStatusMemberId(profile.getMemberStatus().getId());
		resGetProfile.setStatusMember(profile.getMemberStatus().getStatusName());
		resGetProfile.setIndustryId(profile.getIndustry().getId());
		resGetProfile.setPositionId(profile.getPosition().getId());
		resGetProfile.setProvince(profile.getProvince());
		resGetProfile.setCountry(profile.getCountry());
		resGetProfile.setImageId(profile.getImageProfile().getId());
		resGetProfile.setUserBalance(user.getWallet().getBalance());
		resGetProfile.setCity(profile.getCity());
		final List<PojoSocialMediaRes> socialMediaList = new ArrayList<>();
		final PojoSocialMediaRes socialMedia = new PojoSocialMediaRes();
		profileSocialMediaDao.getByProfileId(id).forEach(data -> {
			socialMedia.setPlatformName(data.getSocialMedia().getPlatformName());
			socialMedia.setSocialMediaId(data.getSocialMedia().getId());
			socialMedia.setUrl(data.getUrl());
			socialMedia.setVer(data.getVersion());
			socialMedia.setIsActive(data.getIsActive());
			socialMediaList.add(socialMedia);
		});

		resGetProfile.setSocialMediaList(socialMediaList);
		resGetProfile.setPostalCode(profile.getPostalCode());
		resGetProfile.setPhoneNumber(profile.getPhoneNumber());
		resGetProfile.setDob(profile.getDob());
		return resGetProfile;
	}

	public PojoUpdateRes update(PojoProfileReqUpdate data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		ConnHandler.begin();
	
		final Profile profile = profileDao.getByIdRef(data.getProfileId());
		profileDao.getByIdAndDetach(Profile.class, profile.getId());
		profile.setId(profile.getId());
		profile.setFullname(data.getFullname());
		profile.setCompanyName(data.getCompany());
		profile.setCountry(data.getCountry());
		profile.setCity(data.getCity());
		if(data.getImageId()!=null) {
		final File file = fileDao.getByIdRef(data.getImageId());
		profile.setImageProfile(file);
		}
		profile.setProvince(data.getProvince());
		final Industry industry = industryDao.getByIdRef(data.getIndustryId());
		profile.setIndustry(industry);
		final MemberStatus memberStatus = memberStatusDao.getByIdRef(data.getMemberStatusId());
		profile.setMemberStatus(memberStatus);
		final Position position = positionDao.getByIdRef(data.getPositionId());
		profile.setPosition(position);
		profile.setPostalCode(data.getPostalCode());
		profile.setDob(data.getDob());
		profile.setVersion(data.getVer());
		profile.setIsActive(data.getIsActive());
		profileDao.saveAndFlush(profile);
	
		ConnHandler.commit();
		pojoUpdateRes.setId(profile.getId());
		pojoUpdateRes.setMessage("Save Success!");
		pojoUpdateRes.setVer(profile.getVersion());
	

		return pojoUpdateRes;

	}

}
