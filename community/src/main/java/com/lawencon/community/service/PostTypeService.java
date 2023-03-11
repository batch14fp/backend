package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.PostType;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.posttype.PojoPostTypeInsertReq;
import com.lawencon.community.pojo.posttype.PojoPostTypeUpdateReq;
import com.lawencon.community.pojo.posttype.PojoResGetPostType;

@Service
public class PostTypeService {

	private PostTypeDao postTypeDao;

	public PostTypeService(final PostTypeDao postTypeDao) {

	}
	public List<PojoResGetPostType> getAll() {
		List<PojoResGetPostType> res = new ArrayList<>();

		postTypeDao.getAll().forEach(data -> {

			PojoResGetPostType postType = new PojoResGetPostType();
			postType.setPostTypeCode(data.getTypeCode());
			postType.setPostTypeName(data.getTypeName());
			
			res.add(postType);

		});
		return res;
	}
	
	
	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = postTypeDao.deleteById(Position.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}

	public PojoInsertRes save(PojoPostTypeInsertReq data) {
		ConnHandler.begin();

		final PostType postType = new PostType();

		postType.setTypeName(data.getPostTypeName());
		postType.setTypeCode(data.getPostTypeCode());
		postType.setIsActive(true);

		postTypeDao.save(postType);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	
	public PojoUpdateRes update(PojoPostTypeUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			final PostType postType  = postTypeDao.getByIdRef(data.getPostTypeId());
			postTypeDao.getByIdAndDetach(Position.class, postType.getId());
			postType.setId(postType.getId());
			postType.setTypeName(data.getPostTypeName());
			postType.setTypeCode(data.getPostTypeCode());
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
