package com.lawencon.community.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.MemberStatusDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Profile;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.profile.PojoProfileUpdateReq;
import com.lawencon.community.pojo.profile.PojoResGetProfileDetail;
import com.lawencon.security.principal.PrincipalService;



@Service
public class ProfileService {
	private ProfileDao profileDao;
	private MemberStatusDao memberStatusDao;
	@Inject
	private PrincipalService principalService;
	public ProfileService(final ProfileDao profileDao, final MemberStatusDao memberStatusDao) {
		this.profileDao = profileDao;
		this.memberStatusDao = memberStatusDao;	
	
	}
	
	public PojoResGetProfileDetail getById(String id) {
		Profile profile = profileDao.getByIdAndDetach(id).get();
		PojoResGetProfileDetail resGetProfile = new PojoResGetProfileDetail();
		resGetProfile.setUserId(principalService.getAuthPrincipal());
		resGetProfile.setFullname(profile.getFullname());
		resGetProfile.setCompany(profile.getCompanyName());
		resGetProfile.setUserBalance(profile.getUserBalance());
		resGetProfile.setStatusMemberId(profile.getMemberStatus().getId());
		resGetProfile.setStatusMember(profile.getMemberStatus().getStatusName());
		resGetProfile.setIndustryId(profile.getIndustry().getId());
		resGetProfile.setPositionId(profile.getPosition().getId());
		resGetProfile.setCountry(profile.getCountry());
		resGetProfile.setCity(profile.getCity());
		resGetProfile.setPostalCode(profile.getPostalCode());
		resGetProfile.setPhoneNumber(profile.getPhoneNumber());
		resGetProfile.setDob(profile.getDob());
		return resGetProfile;
	}
	
	
	public PojoUpdateRes update(PojoProfileUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			final Profile profile = profileDao.getByIdRef(data.getProfileId());
			profileDao.getByIdAndDetach(Profile.class, profile.getId());
			profile.setFullname(profile.getFullname());
			profile.setCompanyName(profile.getCompanyName());
			profile.setUserBalance(profile.getUserBalance());
			profile.setCountry(profile.getCountry());
			profile.setCity(profile.getCity());
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
		
		} catch (Exception e) {
			pojoUpdateRes.setId(data.getProfileId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update the data");
		}
		return pojoUpdateRes;
		
		
	}
	

	
	
}
