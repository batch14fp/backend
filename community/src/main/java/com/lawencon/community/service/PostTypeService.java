package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.community.dao.PostTypeDao;
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

}
