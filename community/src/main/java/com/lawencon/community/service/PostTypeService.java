package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.model.PostType;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.posttype.PojoPostTypeReqInsert;
import com.lawencon.community.pojo.posttype.PojoPostTypeReqUpdate;
import com.lawencon.community.pojo.posttype.PojoPostTypeRes;

@Service
public class PostTypeService {

	private PostTypeDao postTypeDao;

	public PostTypeService(final PostTypeDao postTypeDao) {
		this.postTypeDao = postTypeDao;

	}
	
	private void validateBkNotNull(PojoPostTypeReqInsert postType) {
		if (postType.getPostTypeCode() == null) {
			throw new RuntimeException("Post Type Code cannot be empty.");
		}

	}

	private void validateNonBk(PojoPostTypeReqInsert postType) {

		if (postType.getPostTypeName() == null) {
			throw new RuntimeException("Position Name cannot be empty.");
		}

	}

	private void validateNonBk(PojoPostTypeReqUpdate postType) {

		if (postType.getPostTypeId() == null) {
			throw new RuntimeException("Post type cannot be empty.");
		}

		if (postType.getVer() == null) {
			throw new RuntimeException("Post type version cannot be empty.");
		}
		if (postType.getPostTypeName() == null) {
			throw new RuntimeException("Post type name cannot be empty.");
		}
	}

	private void validateBkNotExist(String id) {
		if (postTypeDao.getById(id).isEmpty()) {
			throw new RuntimeException("Post Type cannot be empty.");
		}
	}


	
	public List<PojoPostTypeRes> getAll() {
		List<PojoPostTypeRes> res = new ArrayList<>();

		postTypeDao.getAll().forEach(data -> {
			PojoPostTypeRes postType = new PojoPostTypeRes();
			postType.setPostTypeId(data.getId());
			postType.setPostTypeCode(data.getTypeCode());
			postType.setPostTypeName(data.getTypeName());
			postType.setVer(data.getVersion());
			postType.setIsActive(data.getIsActive());
			
			res.add(postType);

		});
		return res;
	}
	
	
	public PojoRes deleteById(String id) {
		validateBkNotExist(id);
		
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = postTypeDao.deleteById(PostType.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}

	public PojoInsertRes save(PojoPostTypeReqInsert data) {
		validateBkNotNull(data);
		validateNonBk(data);
		
		ConnHandler.begin();

		final PostType postType = new PostType();

		postType.setTypeName(data.getPostTypeName());
		postType.setTypeCode(data.getPostTypeCode());
		postType.setIsActive(true);
		final PostType postTypeNew = postTypeDao.save(postType);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(postTypeNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	
	public PojoUpdateRes update(PojoPostTypeReqUpdate data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			validateNonBk(data);
			
			ConnHandler.begin();
			final PostType postType  = postTypeDao.getByIdRef(data.getPostTypeId());
			postTypeDao.getByIdAndDetach(PostType.class, postType.getId());
			postType.setId(postType.getId());
			postType.setTypeName(data.getPostTypeName());
			postType.setIsActive(data.getIsActive());
			postType.setVersion(data.getVer());
			final PostType postTypeNew = postTypeDao.saveAndFlush(postType);
			ConnHandler.commit();
	
			pojoUpdateRes.setId(postTypeNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(postTypeNew.getVersion());
		
		} catch (Exception e) {
			pojoUpdateRes.setId(data.getPostTypeId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;
		
	}
	
	
	
	

}
