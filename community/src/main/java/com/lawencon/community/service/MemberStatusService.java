package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.StatusEnum;
import com.lawencon.community.dao.MemberStatusDao;
import com.lawencon.community.dao.SubscriptionDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Subscription;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.memberstatus.PojoMemberPremiumRes;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusReqInsert;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusReqUpdate;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusRes;
import com.lawencon.security.principal.PrincipalService;

@Service
public class MemberStatusService {
	private MemberStatusDao memberStatusDao;
	private UserDao userDao;
	private SubscriptionDao subscriptionDao;

	@Autowired
	private PrincipalService principalService;

	public MemberStatusService(final SubscriptionDao subscriptionDao, final UserDao userDao,
			final MemberStatusDao memberStatusDao) {
		this.memberStatusDao = memberStatusDao;
		this.userDao = userDao;
		this.subscriptionDao = subscriptionDao;
	}

	private void validateBkNotNull(PojoMemberStatusReqInsert memberStatus) {
		if (memberStatus.getCodeStatus() == null) {
			throw new RuntimeException("Member Status cannot be empty.");
		}

	}

	private void validateNonBk(PojoMemberStatusReqInsert memberStatus) {

		if (memberStatus.getStatusName() == null) {
			throw new RuntimeException("Member Status Name cannot be empty.");
		}
		if (memberStatus.getPeriodDay() == null) {
			throw new RuntimeException("Member Status Period cannot be empty.");
		}
		if (memberStatus.getPrice() == null) {
			throw new RuntimeException("Member Status Price cannot be empty.");
		}

	}

	private void validateNonBk(PojoMemberStatusReqUpdate memberStatus) {

		if (memberStatus.getMemberStatusId() == null) {
			throw new RuntimeException("Member Status ID cannot be empty.");
		}

		if (memberStatus.getVer() == null) {
			throw new RuntimeException("Member Status Version cannot be empty.");
		}
		if (memberStatus.getStatusName() == null) {
			throw new RuntimeException("Member Status Name cannot be empty.");
		}
		if (memberStatus.getPeriodDay() == null) {
			throw new RuntimeException("Member Status Period cannot be empty.");
		}
		if (memberStatus.getPrice() == null) {
			throw new RuntimeException("Member Status Price cannot be empty.");
		}

	}
	
	private void validateBkNotExist(String id) {
		if (memberStatusDao.getById(id).isEmpty()) {
			throw new RuntimeException("Member Status cannot be empty.");
		}
	}

	public List<PojoMemberStatusRes> getAll() {

		final List<PojoMemberStatusRes> res = new ArrayList<>();

		memberStatusDao.getAll().forEach(data -> {
			final PojoMemberStatusRes memberStatus = new PojoMemberStatusRes();
			memberStatus.setMemberStatusId(data.getId());
			memberStatus.setCodeStatus(data.getCodeStatus());
			memberStatus.setPeriodDay(data.getPeriodDay());
			memberStatus.setStatusName(data.getStatusName());
			memberStatus.setPrice(data.getPrice());
			memberStatus.setIsActive(data.getIsActive());
			memberStatus.setVer(data.getVersion());
			res.add(memberStatus);

		});

		return res;
	}

	public PojoInsertRes save(PojoMemberStatusReqInsert data) {
		validateNonBk(data);
		validateBkNotNull(data);

		ConnHandler.begin();
		final MemberStatus memberStatus = new MemberStatus();
		memberStatus.setCodeStatus(data.getCodeStatus());
		memberStatus.setStatusName(data.getStatusName());
		memberStatus.setPeriodDay(data.getPeriodDay());
		memberStatus.setPrice(data.getPrice());
		memberStatus.setIsActive(true);
		final MemberStatus memberStatusNew = memberStatusDao.save(memberStatus);
		ConnHandler.commit();
		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(memberStatusNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}

	public PojoUpdateRes update(PojoMemberStatusReqUpdate data) {

		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			validateNonBk(data);
			ConnHandler.begin();
			final MemberStatus memberStatus = memberStatusDao.getByIdRef(data.getMemberStatusId());
			memberStatusDao.getByIdAndDetach(MemberStatus.class, memberStatus.getId());

			memberStatus.setStatusName(data.getStatusName());
			memberStatus.setPeriodDay(data.getPeriodDay());
			memberStatus.setVersion(data.getVer());
			memberStatus.setIsActive(data.getIsActive());
			final MemberStatus memberStatusNew = memberStatusDao.saveAndFlush(memberStatus);
			ConnHandler.commit();

			pojoUpdateRes.setId(memberStatusNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(memberStatusNew.getVersion());

		} catch (Exception e) {
			pojoUpdateRes.setId(data.getMemberStatusId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	}

	public PojoRes deleteById(String id) {
		validateBkNotExist(id);
		
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = memberStatusDao.deleteById(MemberStatus.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}

	public PojoMemberPremiumRes getIsPremiumMember() {
		PojoMemberPremiumRes res = new PojoMemberPremiumRes();
		final User userRef = userDao.getByIdRef(principalService.getAuthPrincipal());
		final Subscription subs = subscriptionDao.getByProfileId(userRef.getProfile().getId()).get();
		final Subscription subsRef = subscriptionDao.getByIdRef(subs.getId());

		if (subsRef.getMemberStatus().getCodeStatus().equalsIgnoreCase(StatusEnum.REGULAR.getStatusCode())) {
			res.setIsPremiumMember(false);
		} else {
			res.setIsPremiumMember(true);
		}
		return res;
	}

}
