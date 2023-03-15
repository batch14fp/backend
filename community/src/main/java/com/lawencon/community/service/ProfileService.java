package com.lawencon.community.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.MemberStatusDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.profile.PojoProfileUpdateReq;
import com.lawencon.community.pojo.profile.PojoResGetProfileDetail;
import com.lawencon.security.principal.PrincipalService;



@Service
public class ProfileService {
	private ProfileDao profileDao;
	private MemberStatusDao memberStatusDao;
	private UserDao userDao;
	@Inject
	private PrincipalService principalService;
	public ProfileService(final ProfileDao profileDao, final UserDao userDao, final MemberStatusDao memberStatusDao) {
		this.profileDao = profileDao;
		this.memberStatusDao = memberStatusDao;	
		this.userDao = userDao;
	
	}
	
	public PojoResGetProfileDetail getById(String id) {
		final Profile profile = profileDao.getByIdRef(id);
		final PojoResGetProfileDetail resGetProfile = new PojoResGetProfileDetail();
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
		resGetProfile.setCity(profile.getCity());
		resGetProfile.setPostalCode(profile.getPostalCode());
		resGetProfile.setPhoneNumber(profile.getPhoneNumber());
		resGetProfile.setDob(profile.getDob());
		return resGetProfile;
	}
	
	
	public PojoUpdateRes update(PojoProfileUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
			ConnHandler.begin();
			final Profile profile = profileDao.getByIdRef(data.getProfileId());
			profileDao.getByIdAndDetach(Profile.class, profile.getId());
			profile.setFullname(profile.getFullname());
			profile.setCompanyName(profile.getCompanyName());
			profile.setCountry(profile.getCountry());
			profile.setCity(profile.getCity());
			profile.setProvince(profile.getProvince());
			final MemberStatus memberStatus = memberStatusDao.getByIdRef(data.getMemberStatusId());
			profile.setMemberStatus(memberStatus);
			profile.setPostalCode(profile.getPostalCode());
			profile.setPhoneNumber(profile.getPhoneNumber());
			profile.setDob(profile.getDob());
			final Profile profileNew = profileDao.saveAndFlush(profile);
			ConnHandler.commit();
			pojoUpdateRes.setId(profileNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(profileNew.getVersion());
		
		return pojoUpdateRes;
		
		
	}
	

	
	
}
