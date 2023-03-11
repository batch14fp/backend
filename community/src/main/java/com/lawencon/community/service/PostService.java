package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostType;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoResGetAllPost;


public class PostService extends BaseService<PojoResGetAllPost>{
	private PostDao postDao;
	private PostTypeDao postTypeDao;
	
	public PostService(final PostDao postDao, final PostTypeDao postTypeDao) {
		this.postDao = postDao;
		this.postTypeDao = postTypeDao;
	}
	
	@Override
	public List<PojoResGetAllPost> getAll() {
		final List<PojoResGetAllPost> res = new ArrayList<>();
		postDao.getAll().forEach(data->{
			final PojoResGetAllPost pojoResGetAllPost = new PojoResGetAllPost();
			pojoResGetAllPost.setPostId(data.getId());
			pojoResGetAllPost.setTitle(data.getTitle());
			pojoResGetAllPost.setContent(data.getContentPost());
			pojoResGetAllPost.setImgPostId(data.getFile().getId());
			pojoResGetAllPost.setTypeCode(data.getPostType().getTypeCode());
			pojoResGetAllPost.setTypeName(data.getPostType().getTypeName());
			pojoResGetAllPost.setCategoryCode(data.getCategory().getCategoryCode());
			pojoResGetAllPost.setCategoryName(data.getCategory().getCategoryName());
			res.add(pojoResGetAllPost);
			
		});
		return res;
	}
	
	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = postDao.deleteById(Post.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}
	}
	
	
	public PojoInsertRes save(PojoPostInsertReq data) {
		ConnHandler.begin();
			final Post post = new Post();
			post.setTitle(data.getTitle());
			post.setContentPost(data.getContent());
			
			final PostType postType = postTypeDao.getByIdRef(data.getTypeId());
			post.setPostType(postType);
			post.setIsActive(true);
		final Post postNew = postDao.save(post);
		ConnHandler.commit();

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(postNew.getId());
		pojoInsertRes.setMessage("Save Success!");
		return pojoInsertRes;
	}

	
	
}
