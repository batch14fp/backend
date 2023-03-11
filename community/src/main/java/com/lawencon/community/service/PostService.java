package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PostBookmarkDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostLikeDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostBookmark;
import com.lawencon.community.model.PostLike;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoPostBookmarkInsertReq;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostLikeInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.community.pojo.post.PojoResGetAllPost;
import com.lawencon.security.principal.PrincipalService;


public class PostService extends BaseService<PojoResGetAllPost>{
	private PostDao postDao;
	private PostTypeDao postTypeDao;
	private FileDao fileDao;
	private PostLikeDao postLikeDao;
	private PostBookmarkDao postBookmarkDao;
	private UserDao userDao;


	@Inject
	private PrincipalService principalService;
	
	public PostService(final PostDao postDao,final PostBookmarkDao postBookmarkDao, final PostTypeDao postTypeDao, final FileDao fileDao, final PostLikeDao postLikeDao, final UserDao userDao) {
		this.postDao = postDao;
		this.postTypeDao = postTypeDao;
		this.fileDao = fileDao;
		this.postLikeDao = postLikeDao;
		this.userDao = userDao;
		this.postBookmarkDao = postBookmarkDao;
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
			pojoResGetAllPost.setCountPostComment(0);
			pojoResGetAllPost.setCountPostLike(getCountPostLike(data.getId(), principalService.getAuthPrincipal()));
			pojoResGetAllPost.setBookmark(false);
			pojoResGetAllPost.setLike(false);
			res.add(pojoResGetAllPost);
			
		});
		return res;
	}

	public PojoResGetAllPost getById(String id) {
			final Post data = postDao.getByIdRef(id);
			final PojoResGetAllPost res = new PojoResGetAllPost();
			res.setPostId(data.getId());
			res.setTitle(data.getTitle());
			res.setContent(data.getContentPost());
			res.setImgPostId(data.getFile().getId());
			res.setTypeCode(data.getPostType().getTypeCode());
			res.setTypeName(data.getPostType().getTypeName());
			res.setCategoryCode(data.getCategory().getCategoryCode());
			res.setCategoryName(data.getCategory().getCategoryName());
			res.setCountPostComment(0);
			res.setCountPostLike(getCountPostLike(data.getId(), principalService.getAuthPrincipal()));
			res.setBookmark(false);
			res.setLike(false);
	
		return res;
	}
	private int getCountPostLike(String userId, String postId) {
		int countLike = postLikeDao.countPostLike(userId, postId);
		return countLike;
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
			final File file = fileDao.getByIdRef(data.getImagePostId());
			post.setFile(file);
			post.setIsActive(true);
		final Post postNew = postDao.save(post);
		ConnHandler.commit();

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(postNew.getId());
		pojoInsertRes.setMessage("Save Success!");
		return pojoInsertRes;
	}
	
	public PojoUpdateRes update(PojoPostUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			final Post post = postDao.getByIdRef(data.getPostId());
			postDao.getByIdAndDetach(Post.class, post.getId());
			post.setTitle(data.getTitle());
			post.setContentPost(data.getContent());
			final PostType postType = postTypeDao.getByIdRef(data.getTypeId());
			post.setPostType(postType);
			final File file = fileDao.getByIdRef(data.getImagePostId());
			post.setFile(file);
			post.setIsActive(true);

			final Post postNew = postDao.saveAndFlush(post);
			ConnHandler.commit();

			pojoUpdateRes.setId(postNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(postNew.getVersion());

		} catch (Exception e) {
			pojoUpdateRes.setId(data.getPostId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	}
	
	
	public PojoInsertRes savePostLike(PojoPostLikeInsertReq data) {
		ConnHandler.begin();

		final PostLike postLike = new PostLike();
		
		final Post post = postDao.getByIdRef(data.getPostId());
		final User user = userDao.getByIdRef(data.getUserId());	
		postLike.setPost(post);
		postLike.setUser(user);
		postLike.setIsActive(true);
		final PostLike postLikeNew = postLikeDao.save(postLike);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(postLikeNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	public PojoRes deletePostLikeById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = postLikeDao.deleteById(PostLike.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}
	}
	
	public PojoInsertRes savePostBookmark(PojoPostBookmarkInsertReq data) {
		ConnHandler.begin();
		final PostBookmark postBookmark = new PostBookmark();
		final Post post = postDao.getByIdRef(data.getPostId());
		final User user = userDao.getByIdRef(data.getUserId());	
		postBookmark.setPost(post);
		postBookmark.setUser(user);
		postBookmark.setIsActive(true);
		final PostBookmark postBookmarkNew = postBookmarkDao.save(postBookmark);
		ConnHandler.commit();
		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(postBookmarkNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	public PojoRes deletePostBookmarkById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = postBookmarkDao.deleteById(PostBookmark.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}
	}
	
	
	

	
	
	


	
	
}
