package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostType;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.post.PojoPostReq;
import com.lawencon.community.pojo.post.PojoResGetAllPost;


public class PostService extends BaseService<PojoResGetAllPost>{
	private PostDao postDao;
	
	public PostService(final PostDao postDao) {
		this.postDao = postDao;
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
	
	public PojoRes save(PojoPostReq data) {
		ConnHandler.begin();


		Post post = new Post();

		if (data.getPostId() != null) {

			post = postDao.getByIdAndDetach(data.getPostId()).get();
			post.setTitle(data.getTitle());
			post.setContentPost(data.getContent());
//			post.setPostType(data.getTypeId());
			post.setIsActive(data.getIsActive());
			post.setVersion(data.getVer());

		} else {
			post.setTitle(data.getTitle());
			post.setContentPost(data.getContent());
	
			
		//	post.setPostType(data.getTypeId());
			post.setIsActive(true);
			
		}

		postDao.save(post);
		ConnHandler.commit();

		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}

	
	
}
