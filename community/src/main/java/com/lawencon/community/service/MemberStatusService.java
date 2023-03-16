package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.MemberStatusDao;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusInsertReq;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusUpdateReq;
import com.lawencon.community.pojo.memberstatus.PojoResGetMemberStatus;

@Service
public class MemberStatusService {
	private MemberStatusDao memberStatusDao;
	
	public MemberStatusService(final MemberStatusDao memberStatusDao) {
		this.memberStatusDao = memberStatusDao;
	}

	public List<PojoResGetMemberStatus> getAll() {

		final List<PojoResGetMemberStatus> res = new ArrayList<>();

		memberStatusDao.getAll().forEach(data -> {
			final PojoResGetMemberStatus memberStatus = new PojoResGetMemberStatus();
			memberStatus.setMemberStatusId(data.getId());
			memberStatus.setCodeStatus(data.getCodeStatus());
			memberStatus.setPeriodDay(data.getPeriodDay());
			memberStatus.setStatusName(data.getStatusName());
			memberStatus.setIsActive(data.getIsActive());
			res.add(memberStatus);

		});

		return res;
	}

	
	
	public PojoInsertRes save(PojoMemberStatusInsertReq data) {
		ConnHandler.begin();
		final MemberStatus memberStatus = new MemberStatus();
		memberStatus.setCodeStatus(data.getCodeStatus());
		memberStatus.setStatusName(data.getStatusName());
		memberStatus.setPeriodDay(data.getPeriodDay());
		memberStatus.setIsActive(true);
		final MemberStatus memberStatusNew = memberStatusDao.save(memberStatus);
		ConnHandler.commit();
		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(memberStatusNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	public PojoUpdateRes update(PojoMemberStatusUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
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
	

}
