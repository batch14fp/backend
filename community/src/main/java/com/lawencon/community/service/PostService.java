package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.FilePostDao;
import com.lawencon.community.dao.PostBookmarkDao;
import com.lawencon.community.dao.PostCommentDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostLikeDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.File;
import com.lawencon.community.model.FilePost;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostBookmark;
import com.lawencon.community.model.PostComment;
import com.lawencon.community.model.PostLike;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoPostBookmarkInsertReq;
import com.lawencon.community.pojo.post.PojoPostCommentInsertReq;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostLikeInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.community.pojo.post.PojoResGetFileData;
import com.lawencon.community.pojo.post.PojoResGetPost;
import com.lawencon.community.pojo.post.PojoResGetPostComment;
import com.lawencon.community.pojo.post.PojoResGetPostCommentReplyData;
import com.lawencon.community.util.GenerateString;
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
	private FilePostDao filePostDao;

	@Autowired
	private PrincipalService principalService;

	public PostService(final FilePostDao filePostDao, final PostDao postDao, final PostBookmarkDao postBookmarkDao,
			final PostCommentDao postCommentDao, final PostTypeDao postTypeDao, final FileDao fileDao,
			final PostLikeDao postLikeDao, final UserDao userDao, final CategoryDao categoryDao) {
		this.postDao = postDao;
		this.postTypeDao = postTypeDao;
		this.fileDao = fileDao;
		this.postLikeDao = postLikeDao;
		this.userDao = userDao;
		this.postBookmarkDao = postBookmarkDao;
		this.categoryDao = categoryDao;
		this.postCommentDao = postCommentDao;
		this.filePostDao = filePostDao;

	}

	public PojoResGetPost getById(String id) {
		final Post data = postDao.getByIdRef(id);
		final PojoResGetPost res = new PojoResGetPost();

		res.setId(data.getId());
		res.setTitle(data.getTitle());
		res.setContent(data.getContentPost());
		final List<PojoResGetFileData> files = new ArrayList<>();
		filePostDao.getAllFileByPostId(data.getId()).forEach(filesPost -> {
			final PojoResGetFileData filePostData = new PojoResGetFileData();
			filePostData.setFilePostId(filesPost.getId());
			filePostData.setFileId(filesPost.getFile().getId());
			filePostData.setVer(filesPost.getVersion());
			files.add(filePostData);
		});
		res.setTypeCode(data.getPostType().getTypeCode());
		res.setTypeName(data.getPostType().getTypeName());

		res.setUserId(data.getUser().getId());
		res.setFullname(data.getUser().getProfile().getFullname());
		res.setCategoryCode(data.getCategory().getCategoryCode());
		res.setCategoryName(data.getCategory().getCategoryName());
		res.setCountPostComment(postCommentDao.getCountPostComment(data.getId()));
		res.setCountPostLike(getCountPostLike(data.getId()));
		res.setBookmark(getIsBookmarkPost(data.getUser().getId(), data.getId()));
		res.setLike(getIsLike(data.getUser().getId(), data.getId()));

		return res;
	}

	private Boolean getIsLike(String userId, String postId) {
		final Boolean isLike = postLikeDao.getIsLike(userId, postId);
		return isLike;
	}

	private Boolean getIsBookmarkPost(String userId, String postId) {
		final Boolean isBookmark = postBookmarkDao.getIsBookmarkPost(userId, postId);
		return isBookmark;
	}

	private Long getCountPostLike(String postId) {
		Long countLike = postLikeDao.countPostLike(postId);
		return countLike;
	}

	public Long getCountComment(String postId) {
		Long countLike = postCommentDao.getCountPostComment(postId);
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
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		post.setUser(user);

		final PostType postType = postTypeDao.getByIdRef(data.getTypeId());
		post.setPostType(postType);
		final Category category = categoryDao.getByIdRef(data.getCategoryId());
		post.setCategory(category);

		final List<File> files = new ArrayList<>();
		data.getAttachmentPost().forEach(file -> {
			final File filePost = new File();
			filePost.setFileExtension(file.getExtensions());
			filePost.setFileName(GenerateString.generateFileName(file.getExtensions()));
			filePost.setFileContent(file.getFileContent());
			files.add(filePost);

		});
		final List<File> fileList = fileDao.saveAll(files);
		post.setIsActive(true);
		final Post postNew = postDao.save(post);
		final List<FilePost> filePostInsert = new ArrayList<>();
		fileList.forEach(filePostConten -> {
			final FilePost filePostData = new FilePost();
			filePostData.setFile(filePostConten);
			filePostData.setPost(post);
			filePostInsert.add(filePostData);
		});
		filePostDao.saveAll(filePostInsert);
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
			final List<File> files = new ArrayList<>();
			data.getAttachmentPost().forEach(file -> {
				final File filePost = new File();
				filePost.setId(data.getImagePostId());
				filePost.setFileExtension(file.getExtensions());
				filePost.setFileName(file.getFileName());
				filePost.setFileContent(file.getFileContent());
				filePost.setVersion(file.getVer());
				files.add(filePost);

			});
			final List<File> fileList = fileDao.saveAll(files);
			post.setIsActive(true);
			final Post postNew = postDao.save(post);
			final List<FilePost> filePostInsert = new ArrayList<>();
			fileList.forEach(filePostContent -> {
				final FilePost filePostData = new FilePost();
				filePostData.setId(filePostContent.getId());
				filePostData.setFile(filePostContent);
				filePostData.setPost(post);
				filePostInsert.add(filePostData);
			});
			filePostDao.saveAll(filePostInsert);

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

		final PojoInsertRes pojoRes = new PojoInsertRes();
		final PostLike postLike = new PostLike();

		final Post post = postDao.getByIdRef(data.getPostId());
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		if (getIsLike(user.getId(), post.getId())) {
			pojoRes.setMessage("The post has already been liked!");
		} else {
			ConnHandler.begin();
			postLike.setPost(post);
			postLike.setUser(user);
			postLike.setIsActive(true);
			final PostLike postLikeNew = postLikeDao.save(postLike);
			ConnHandler.commit();
			pojoRes.setId(postLikeNew.getId());
			pojoRes.setMessage("Save Success!");
		}

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
		final PojoInsertRes pojoRes = new PojoInsertRes();
		final PostBookmark postBookmark = new PostBookmark();
		final Post post = postDao.getByIdRef(data.getPostId());
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		if (getIsLike(user.getId(), post.getId())) {
			pojoRes.setMessage("The post has already been bookmark!");
		} else {
			ConnHandler.begin();

			postBookmark.setPost(post);
			postBookmark.setUser(user);
			postBookmark.setIsActive(true);
			final PostBookmark postBookmarkNew = postBookmarkDao.save(postBookmark);
			ConnHandler.commit();
			pojoRes.setId(postBookmarkNew.getId());
			pojoRes.setMessage("Save Success!");
		
		}
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
		final List<Post> posts = postDao.getGetAllPost(offset, limit);
		final List<PojoResGetPost> listPost = new ArrayList<>();
		for (Post data : posts) {
			PojoResGetPost res = new PojoResGetPost();
			res.setId(data.getId());
			res.setTitle(data.getTitle());
			res.setContent(data.getContentPost());
			List<PojoResGetFileData> files = new ArrayList<>();

			final List<FilePost> filePosts = filePostDao.getAllFileByPostId(data.getId());
			for (FilePost filePost : filePosts) {
				PojoResGetFileData filePostData = new PojoResGetFileData();
				filePostData.setFilePostId(filePost.getId());
				filePostData.setFileId(filePost.getFile().getId());
				filePostData.setVer(filePost.getVersion());
				files.add(filePostData);
			}

			res.setTypeCode(data.getPostType().getTypeCode());
			res.setTypeName(data.getPostType().getTypeName());
			res.setCategoryCode(data.getCategory().getCategoryCode());
			res.setCategoryName(data.getCategory().getCategoryName());
			res.setCountPostComment(postCommentDao.getCountPostComment(data.getId()));
			res.setCountPostLike(getCountPostLike(data.getId()));
			res.setTimeAgo(data.getCreatedAt());
			res.setBookmark(false);
			res.setLike(false);
			listPost.add(res);
		}

		return listPost;

	}

	public int getTotalCount() {

		return postDao.getTotalCount();
	}

	public int getTotalCountByUserId() {

		return postDao.getByUserIdTotalCount(principalService.getAuthPrincipal());
	}

	public List<PojoResGetPost> getMostLike(int offset, int limit) throws Exception {
		final List<Post> posts = postDao.getPostsByMostLikes(offset, limit);
		final List<PojoResGetPost> listPost = new ArrayList<>();
		for (Post data : posts) {
			PojoResGetPost res = new PojoResGetPost();
			res.setId(data.getId());
			res.setTitle(data.getTitle());
			res.setContent(data.getContentPost());
			List<PojoResGetFileData> files = new ArrayList<>();

			final List<FilePost> filePosts = filePostDao.getAllFileByPostId(data.getId());
			for (FilePost filePost : filePosts) {
				PojoResGetFileData filePostData = new PojoResGetFileData();
				filePostData.setFilePostId(filePost.getId());
				filePostData.setFileId(filePost.getFile().getId());
				filePostData.setVer(filePost.getVersion());
				files.add(filePostData);
			}

			res.setTypeCode(data.getPostType().getTypeCode());
			res.setTypeName(data.getPostType().getTypeName());
			res.setCategoryCode(data.getCategory().getCategoryCode());
			res.setCategoryName(data.getCategory().getCategoryName());
			res.setCountPostComment(postCommentDao.getCountPostComment(data.getId()));
			res.setCountPostLike(getCountPostLike(data.getId()));
			res.setTimeAgo(data.getCreatedAt());
			res.setBookmark(false);
			res.setLike(false);
			listPost.add(res);
		}

		return listPost;

	}

	public List<PojoResGetPost> getAllPostByUserId(int offset, int limit) throws Exception {
		final List<PojoResGetPost> listPost = new ArrayList<>();

		final List<Post> posts = postDao.getByUserId(principalService.getAuthPrincipal(), offset, limit);
		for (Post data : posts) {
			PojoResGetPost res = new PojoResGetPost();
			res.setId(data.getId());
			res.setTitle(data.getTitle());
			res.setContent(data.getContentPost());
			final List<PojoResGetFileData> files = new ArrayList<>();

			final List<FilePost> filePosts = filePostDao.getAllFileByPostId(data.getId());
			for (FilePost filePost : filePosts) {
				PojoResGetFileData filePostData = new PojoResGetFileData();
				filePostData.setFilePostId(filePost.getId());
				filePostData.setFileId(filePost.getFile().getId());
				filePostData.setVer(filePost.getVersion());
				files.add(filePostData);
			}

			res.setTypeCode(data.getPostType().getTypeCode());
			res.setTypeName(data.getPostType().getTypeName());
			res.setCategoryCode(data.getCategory().getCategoryCode());
			res.setCategoryName(data.getCategory().getCategoryName());
			res.setCountPostComment(postCommentDao.getCountPostComment(data.getId()));
			res.setCountPostLike(getCountPostLike(data.getId()));
			res.setTimeAgo(data.getCreatedAt());
			res.setBookmark(false);
			res.setLike(false);
			listPost.add(res);
		}
		return listPost;

	}

	public PojoInsertRes savePostComment(PojoPostCommentInsertReq data) {
		ConnHandler.begin();
		final PostComment postComment = new PostComment();
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		postComment.setUser(user);
		postComment.setBody(data.getContentComment());
		postComment.setIsActive(true);
		if (data.getCommentId() != null) {
			final PostComment postCommentReply = postCommentDao.getByIdRef(data.getCommentId());
			postComment.setComment(postCommentReply);
			final Post post = postDao.getByIdRef(postCommentReply.getPost().getId());
			postComment.setPost(post);
		} else {
			final Post post = postDao.getByIdRef(data.getPostId());
			postComment.setPost(post);
		}
		final PostComment postCommentNew = postCommentDao.save(postComment);

		ConnHandler.commit();

		final PojoInsertRes res = new PojoInsertRes();
		res.setId(postCommentNew.getId());
		res.setMessage("Save Success");
		return res;

	}

	public List<PojoResGetPostComment> getAllCommentByPostId(final String postId, int offset, int limit)
			throws Exception {
		final List<PojoResGetPostComment> listComment = new ArrayList<>();
		final List<PojoResGetPostCommentReplyData> listCommentData = new ArrayList<>();
		postCommentDao.getAllByPostId(postId, limit, offset).forEach(data -> {
			final PojoResGetPostComment postComment = new PojoResGetPostComment();
			postComment.setContentComment(data.getBody());
			postComment.setPostCommentId(data.getId());
			postComment.setUserId(data.getUser().getId());
			postComment.setFullname(data.getUser().getProfile().getFullname());
			postComment.setCreatedAt(data.getCreatedAt());
			postComment.setVer(data.getVersion());
			if (data.getComment() != null) {
				final PojoResGetPostCommentReplyData postCommentData = new PojoResGetPostCommentReplyData();

				postCommentData.setContentComment(data.getComment().getId());
				postCommentData.setUserId(data.getUser().getId());
				postCommentData.setContentComment(data.getComment().getBody());
				postCommentData.setFullname(data.getComment().getUser().getProfile().getFullname());
				postCommentData.setCreatedAt(data.getComment().getCreatedAt());
				listCommentData.add(postCommentData);
				postComment.setData(listCommentData);

			}

			listComment.add(postComment);
		});
		return listComment;
	}

}
