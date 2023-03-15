package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PostBookmarkDao;
import com.lawencon.community.dao.PostCommentDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostLikeDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Category;
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
import com.lawencon.community.pojo.post.PojoResGetPost;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PostService {
	private PostDao postDao;
	private PostTypeDao postTypeDao;
	private FileDao fileDao;
	private PostLikeDao postLikeDao;
	private PostCommentDao postCommentDao;
	private PostBookmarkDao postBookmarkDao;
	private UserDao userDao;
	private CategoryDao categoryDao;


	@Inject
	private PrincipalService principalService;
	
	public PostService(final PostDao postDao,final PostBookmarkDao postBookmarkDao, final PostCommentDao postCommentDao ,final PostTypeDao postTypeDao, final FileDao fileDao, final PostLikeDao postLikeDao, final UserDao userDao, final CategoryDao categoryDao) {
		this.postDao = postDao;
		this.postTypeDao = postTypeDao;
		this.fileDao = fileDao;
		this.postLikeDao = postLikeDao;
		this.userDao = userDao;
		this.postBookmarkDao = postBookmarkDao;
		this.categoryDao = categoryDao;
		this.postCommentDao = postCommentDao;
	}


	public PojoResGetPost getById(String id) {
			final Post data = postDao.getByIdRef(id);
			final PojoResGetPost res = new PojoResGetPost();
			
			res.setId(data.getId());
			res.setTitle(data.getTitle());
			res.setContent(data.getContentPost());
			res.setImgPostId(data.getFile().getId());
			res.setTypeCode(data.getPostType().getTypeCode());
			res.setTypeName(data.getPostType().getTypeName());
			
			res.setUserId(data.getUser().getId());
			res.setFullname(data.getUser().getProfile().getFullname());
			res.setCategoryCode(data.getCategory().getCategoryCode());
			res.setCategoryName(data.getCategory().getCategoryName());
			res.setCountPostComment(getCountPostComment(data.getId()));
			res.setCountPostLike(getCountPostLike(data.getId()));
			res.setBookmark(false);
			res.setLike(false);
	
		return res;
	}
	private Long getCountPostLike(String postId) {
		Long countLike = postLikeDao.countPostLike(postId);
		return countLike;
	}
	private Long getCountPostComment(String postId) {
		Long postComment = postCommentDao.countPostComment( postId);
		return postComment;
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
			final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
			post.setUser(user);
			post.setEndAt(data.getEndAt());
			final PostType postType = postTypeDao.getByIdRef(data.getTypeId());
			post.setPostType(postType);
			final Category category = categoryDao.getByIdRef(data.getCategoryId());
			post.setCategory(category);
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
			final Category category = categoryDao.getByIdRef(data.getCategoryId());
			post.setCategory(category);
			post.setEndAt(data.getEndAt());
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
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());	
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
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());	
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
	
	    
	    public List<PojoResGetPost> getData(int offset, int limit) {
	    	final List<PojoResGetPost>  listPost= new ArrayList<>();
	    	postDao.getGetAllPost(offset, limit).forEach(data->{
	    	 final PojoResGetPost res = new PojoResGetPost();
	    	 	res.setId(data.getId());
				res.setTitle(data.getTitle());
				res.setContent(data.getContentPost());
				res.setImgPostId(data.getFile().getId());
				res.setTypeCode(data.getPostType().getTypeCode());
				res.setTypeName(data.getPostType().getTypeName());
				res.setUserId(data.getUser().getId());
				res.setFullname(data.getUser().getProfile().getFullname());
				res.setCategoryCode(data.getCategory().getCategoryCode());
				res.setCategoryName(data.getCategory().getCategoryName());
				res.setCountPostComment(getCountPostComment(data.getId()));
				res.setCountPostLike(getCountPostLike(data.getId()));
				res.setBookmark(false);
				res.setLike(false); 
				listPost.add(res);
	     });;

		return listPost;
	      
	    }
	    public int getTotalCount() {
	      
	        return postDao.getTotalCount();
	    }
	    public int getTotalCountByUserId() {
		      
	        return postDao.getByUserIdTotalCount(principalService.getAuthPrincipal());
	    }
	    
	    
	    
	    
	    
	    public List<PojoResGetPost> getMostLike(int offset, int limit) throws Exception {
	    	final List<PojoResGetPost>  listPost= new ArrayList<>();
	    	postDao.getPostsByMostLikes(offset, limit).forEach(data->{
	    	 final PojoResGetPost res = new PojoResGetPost();
	    	 	res.setId(data.getId());
				res.setTitle(data.getTitle());
				res.setContent(data.getContentPost());
				res.setImgPostId(data.getFile().getId());
				res.setTypeCode(data.getPostType().getTypeCode());
				res.setTypeName(data.getPostType().getTypeName());
				res.setCategoryCode(data.getCategory().getCategoryCode());
				res.setCategoryName(data.getCategory().getCategoryName());
				res.setCountPostComment(getCountPostComment(data.getId()));
				res.setCountPostLike(getCountPostLike(data.getId()));
				res.setTimeAgo(data.getCreatedAt());
				res.setBookmark(false);
				res.setLike(false); 
				listPost.add(res);
	     });;

		return listPost;
	      
	    }
	    
	    
	    
	    public List<PojoResGetPost> getAllPostByUserId(int offset, int limit) throws Exception {
	    	final List<PojoResGetPost>  listPost= new ArrayList<>();
	    	postDao.getByUserId(principalService.getAuthPrincipal(),offset, limit).forEach(data->{
	    	 final PojoResGetPost res = new PojoResGetPost();
	    	 	res.setId(data.getId());
				res.setTitle(data.getTitle());
				res.setContent(data.getContentPost());
				res.setImgPostId(data.getFile().getId());
				res.setTypeCode(data.getPostType().getTypeCode());
				res.setTypeName(data.getPostType().getTypeName());
				res.setUserId(data.getUser().getId());
				res.setFullname(data.getUser().getProfile().getFullname());
				res.setCategoryCode(data.getCategory().getCategoryCode());
				res.setCategoryName(data.getCategory().getCategoryName());
				res.setCountPostComment(getCountPostComment(data.getId()));
				res.setCountPostLike(getCountPostLike(data.getId()));
				res.setBookmark(false);
				res.setLike(false); 
				listPost.add(res);
	     });;

		return listPost;
	      
	    }
	    
}
