package com.lawencon.community.service;

import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.model.Profile;
import com.lawencon.community.pojo.profile.PojoResGetProfileDetail;

public class ProfileService {
	private ProfileDao profileDao;
	private PrincipalService principalService;
	
	public ProfileService(ProfileDao profileDao, PrincipalService principalService) {
		this.profileDao = profileDao;
		this.principalService = principalService;
	}
	
	public PojoResGetProfileDetail getById(String id) {
		Profile profile = profileDao.getByIdAndDetach(id).get();
		PojoResGetProfileDetail resGetProfile = new PojoResGetProfileDetail();
		resGetProfile.setUserId(principalService.getAuthPrincipal());
		resGetProfile.setFullname(profile.getFullname());
		resGetProfile.setCompany(profile.getCompanyName());
		resGetProfile.setIndustryId(profile.getIndustry().getId());
		resGetProfile.setPositionId(profile.getPosition().getId());
		resGetProfile.setCountry(profile.getCountry());
		resGetProfile.setCity(profile.getCity());
		resGetProfile.setPostalCode(profile.getPostalCode());
//		resGetProfile.setPhoneNumber(profile.getPhoneNumber());
		resGetProfile.setDob(profile.getDob());
		return resGetProfile;
	}
	
	
	
}
