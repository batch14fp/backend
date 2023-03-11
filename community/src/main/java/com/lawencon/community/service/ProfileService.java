package com.lawencon.community.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.model.Profile;
import com.lawencon.community.pojo.profile.PojoResGetProfileDetail;
import com.lawencon.security.principal.PrincipalService;



@Service
public class ProfileService {
	private ProfileDao profileDao;
	@Inject
	private PrincipalService principalService;
	public ProfileService(ProfileDao profileDao) {
		this.profileDao = profileDao;
	
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
		resGetProfile.setPhoneNumber(profile.getPhoneNumber());
		resGetProfile.setDob(profile.getDob());
		return resGetProfile;
	}
	
	
	
}
