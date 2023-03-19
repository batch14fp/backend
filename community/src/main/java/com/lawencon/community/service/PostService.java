package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.FilePostDao;
import com.lawencon.community.dao.PollingDao;
import com.lawencon.community.dao.PollingOptionDao;
import com.lawencon.community.dao.PostBookmarkDao;
import com.lawencon.community.dao.PostCommentDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostLikeDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.File;
import com.lawencon.community.model.FilePost;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PollingOption;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostBookmark;
import com.lawencon.community.model.PostComment;
import com.lawencon.community.model.PostLike;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoFileResData;
import com.lawencon.community.pojo.post.PojoPollingOptionReqInsert;
import com.lawencon.community.pojo.post.PojoPollingOptionReqUpdate;
import com.lawencon.community.pojo.post.PojoPollingOptionRes;
import com.lawencon.community.pojo.post.PojoPostBookmarkReqInsert;
import com.lawencon.community.pojo.post.PojoPostCommentReplyResData;
import com.lawencon.community.pojo.post.PojoPostCommentReqInsert;
import com.lawencon.community.pojo.post.PojoPostCommentRes;
import com.lawencon.community.pojo.post.PojoPostLikeReqInsert;
import com.lawencon.community.pojo.post.PojoPostReqInsert;
import com.lawencon.community.pojo.post.PojoPostReqUpdate;
import com.lawencon.community.pojo.post.PojoPostRes;
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
	private PollingDao pollingDao;
	private PollingOptionDao pollingOptionDao;

	@Autowired
	private PrincipalService principalService;

	public PostService(final PollingOptionDao pollingOptionDao, final PollingDao pollingDao,
			final FilePostDao filePostDao, final PostDao postDao, final PostBookmarkDao postBookmarkDao,
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
		this.pollingDao = pollingDao;
		this.pollingOptionDao = pollingOptionDao;

	}

	public PojoPostRes getById(String id) throws Exception {
		final Post data = postDao.getByIdRef(id);
		final PojoPostRes res = new PojoPostRes();
		res.setId(data.getId());
		res.setTitle(data.getTitle());
		res.setContent(data.getContentPost());
		res.setUserId(data.getUser().getId());
		res.setFullname(data.getUser().getProfile().getFullname());
		res.setTypeName(data.getPostType().getTypeName());
		res.setCategoryName(data.getCategory().getCategoryName());
		if (data.getPolling() != null) {
			final Polling polling = pollingDao.getByIdRef(data.getPolling().getId());
			res.setPollingId(polling.getId());
			res.setTitlePolling(polling.getTitle());
			final List<PojoPollingOptionRes> options = new ArrayList<>();
			final List<PollingOption> pollingOptions = pollingOptionDao.getAllOptionByPollingId(polling.getId());
			for (PollingOption pollingOption : pollingOptions) {
				final PojoPollingOptionRes option = new PojoPollingOptionRes();
				option.setPollingOptionId(pollingOption.getId());
				option.setVer(pollingOption.getVersion());
				option.setPollingContent(pollingOption.getContentPolling());
				options.add(option);
			}
			res.setPollingOption(options);
		}
		final List<PojoFileResData> files = new ArrayList<>();
		filePostDao.getAllFileByPostId(data.getId()).forEach(filesPost -> {
			final PojoFileResData filePostData = new PojoFileResData();
			filePostData.setFilePostId(filesPost.getId());
			filePostData.setFileId(filesPost.getFile().getId());
			filePostData.setVer(filesPost.getVersion());
			files.add(filePostData);
		});
		res.setData(files);
		res.setVer(data.getVersion());
		res.setTypeCode(data.getPostType().getTypeCode());
		res.setTypeName(data.getPostType().getTypeName());

		res.setUserId(data.getUser().getId());
		res.setFullname(data.getUser().getProfile().getFullname());
		res.setCategoryCode(data.getCategory().getCategoryCode());
		res.setCategoryName(data.getCategory().getCategoryName());
		res.setCountPostComment(postCommentDao.getCountPostComment(data.getId()));
		res.setCountPostLike(getCountPostLike(data.getId()));
		//res.setBookmark(getIsBookmarkPost(data.getUser().getId(), data.getId()));
		//res.setLike(getIsLike(data.getUser().getId(), data.getId()));

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

	public PojoInsertRes save(PojoPostReqInsert data) {
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
		final Polling polling = new Polling();
		polling.setTitle(data.getPollingInsert().getPollingTitle());
		polling.setIsOpen(true);
		polling.setEndAt(data.getPollingInsert().getEndAt());
		polling.setIsActive(true);

		final Polling pollingNew = pollingDao.save(polling);

		final List<PollingOption> options = new ArrayList<>();
		for (PojoPollingOptionReqInsert option : data.getPollingInsert().getPollingOptions()) {
			final PollingOption pollingOption = new PollingOption();
			pollingOption.setPolling(pollingNew);
			pollingOption.setContentPolling(option.getPollingContent());
			options.add(pollingOption);
		}
		pollingOptionDao.saveAll(options);

		final List<File> fileList = fileDao.saveAll(files);
		post.setIsActive(true);
		post.setPolling(pollingNew);
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

	public PojoUpdateRes update(PojoPostReqUpdate data) {
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
			final Polling polling = pollingDao.getByIdRef(data.getPollingUpdate().getPollingId());
			pollingDao.getByIdAndDetach(Polling.class, polling.getId());
			polling.setTitle(data.getPollingUpdate().getPollingTitle());
			polling.setIsOpen(data.getPollingUpdate().getIsOpen());
			polling.setEndAt(data.getPollingUpdate().getEndAt());
			polling.setVersion(data.getPollingUpdate().getVer());
			polling.setIsActive(data.getPollingUpdate().getIsActive());

			final List<PollingOption> options = new ArrayList<>();
			for (PojoPollingOptionReqUpdate option : data.getPollingUpdate().getPollingOptions()) {
				final PollingOption pollingOption = pollingOptionDao.getByIdRef(option.getPollingOptionId());
				pollingOptionDao.getByIdAndDetach(PollingOption.class, pollingOption.getId());
				pollingOption.setPolling(polling);
				pollingOption.setId(pollingOption.getId());
				pollingOption.setIsActive(pollingOption.getIsActive());
				pollingOption.setVersion(pollingOption.getVersion());
				pollingOption.setContentPolling(option.getPollingOptionContent());
				options.add(pollingOption);
			}

			pollingOptionDao.saveAll(options);

			pojoUpdateRes.setId(postNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(postNew.getVersion());

		} catch (Exception e) {
			pojoUpdateRes.setId(data.getPostId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	}

	public PojoInsertRes savePostLike(PojoPostLikeReqInsert data) {

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

	public PojoInsertRes savePostBookmark(PojoPostBookmarkReqInsert data) {
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

	public List<PojoPostRes> getData(int offset, int limit) throws Exception {
		final List<Post> posts = postDao.getGetAllPost(offset, limit);
		final List<PojoPostRes> listPost = new ArrayList<>();
		for (Post data : posts) {
			PojoPostRes res = new PojoPostRes();
			res.setId(data.getId());
			res.setTitle(data.getTitle());
			res.setContent(data.getContentPost());
			res.setUserId(data.getUser().getId());
			res.setFullname(data.getUser().getProfile().getFullname());
			res.setTypeName(data.getPostType().getTypeName());
			res.setCategoryName(data.getCategory().getCategoryName());
			final List<PojoFileResData> files = new ArrayList<>();
			if (data.getPolling() != null) {
				final Polling polling = pollingDao.getByIdRef(data.getPolling().getId());
				res.setPollingId(polling.getId());
				res.setTitlePolling(polling.getTitle());
				final List<PojoPollingOptionRes> options = new ArrayList<>();
				final List<PollingOption> pollingOptions = pollingOptionDao.getAllOptionByPollingId(polling.getId());
				for (PollingOption pollingOption : pollingOptions) {
					final PojoPollingOptionRes option = new PojoPollingOptionRes();
					option.setPollingOptionId(pollingOption.getId());
					option.setVer(pollingOption.getVersion());
					option.setPollingContent(pollingOption.getContentPolling());
					options.add(option);
				}
				res.setPollingOption(options);
			}

			final List<FilePost> filePosts = filePostDao.getAllFileByPostId(data.getId());
			for (FilePost filePost : filePosts) {
				PojoFileResData filePostData = new PojoFileResData();
				filePostData.setFilePostId(filePost.getId());
				filePostData.setFileId(filePost.getFile().getId());
				filePostData.setVer(filePost.getVersion());
				files.add(filePostData);
			}
			res.setVer(data.getVersion());
			res.setData(files);
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

	public List<PojoPostRes> getMostLike(int offset, int limit) throws Exception {
		final List<Post> posts = postDao.getPostsByMostLikes(offset, limit);
		final List<PojoPostRes> listPost = new ArrayList<>();
		for (Post data : posts) {
			PojoPostRes res = new PojoPostRes();
			res.setId(data.getId());
			res.setTitle(data.getTitle());
			res.setContent(data.getContentPost());
			res.setUserId(data.getUser().getId());

			res.setFullname(data.getUser().getProfile().getFullname());
			res.setTypeName(data.getPostType().getTypeName());
			res.setCategoryName(data.getCategory().getCategoryName());
			final List<PojoFileResData> files = new ArrayList<>();
			if (data.getPolling() != null) {
				final Polling polling = pollingDao.getByIdRef(data.getPolling().getId());
				res.setPollingId(polling.getId());
				res.setTitlePolling(polling.getTitle());
				final List<PojoPollingOptionRes> options = new ArrayList<>();
				final List<PollingOption> pollingOptions = pollingOptionDao.getAllOptionByPollingId(polling.getId());
				for (PollingOption pollingOption : pollingOptions) {
					final PojoPollingOptionRes option = new PojoPollingOptionRes();
					option.setPollingOptionId(pollingOption.getId());
					option.setPollingContent(pollingOption.getContentPolling());
					option.setVer(pollingOption.getVersion());
					options.add(option);
				}
				res.setPollingOption(options);
			}

			final List<FilePost> filePosts = filePostDao.getAllFileByPostId(data.getId());
			for (FilePost filePost : filePosts) {
				PojoFileResData filePostData = new PojoFileResData();
				filePostData.setFilePostId(filePost.getId());
				filePostData.setFileId(filePost.getFile().getId());
				filePostData.setVer(filePost.getVersion());
				files.add(filePostData);
			}
			res.setVer(data.getVersion());
			res.setData(files);
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

	public List<PojoPostRes> getAllPostByUserId(int offset, int limit) throws Exception {
		final List<PojoPostRes> listPost = new ArrayList<>();

		final List<Post> posts = postDao.getByUserId(principalService.getAuthPrincipal(), offset, limit);
		for (Post data : posts) {
			PojoPostRes res = new PojoPostRes();
			res.setId(data.getId());
			res.setTitle(data.getTitle());
			res.setContent(data.getContentPost());
			res.setUserId(data.getUser().getId());
			res.setFullname(data.getUser().getProfile().getFullname());
			res.setTypeName(data.getPostType().getTypeName());
			res.setCategoryName(data.getCategory().getCategoryName());
			final List<PojoFileResData> files = new ArrayList<>();
			if (data.getPolling() != null) {
				final Polling polling = pollingDao.getByIdRef(data.getPolling().getId());
				res.setPollingId(polling.getId());
				res.setTitlePolling(polling.getTitle());
				final List<PojoPollingOptionRes> options = new ArrayList<>();
				final List<PollingOption> pollingOptions = pollingOptionDao.getAllOptionByPollingId(polling.getId());
				for (PollingOption pollingOption : pollingOptions) {
					final PojoPollingOptionRes option = new PojoPollingOptionRes();
					option.setPollingOptionId(pollingOption.getId());
					option.setPollingContent(pollingOption.getContentPolling());
					option.setVer(pollingOption.getVersion());
					options.add(option);
				}
				res.setPollingOption(options);
			}
			final List<FilePost> filePosts = filePostDao.getAllFileByPostId(data.getId());
			for (FilePost filePost : filePosts) {
				PojoFileResData filePostData = new PojoFileResData();
				filePostData.setFilePostId(filePost.getId());
				filePostData.setFileId(filePost.getFile().getId());
				filePostData.setVer(filePost.getVersion());
				files.add(filePostData);
			}
			res.setData(files);
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

	public PojoInsertRes savePostComment(PojoPostCommentReqInsert data) {
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

	public List<PojoPostCommentRes> getAllCommentByPostId(final String postId, int offset, int limit) throws Exception {
		final List<PojoPostCommentRes> listComment = new ArrayList<>();
		final List<PojoPostCommentReplyResData> listCommentData = new ArrayList<>();
		postCommentDao.getAllByPostId(postId, limit, offset).forEach(data -> {
			final PojoPostCommentRes postComment = new PojoPostCommentRes();
			postComment.setContentComment(data.getBody());
			postComment.setPostCommentId(data.getId());
			postComment.setUserId(data.getUser().getId());
			postComment.setFullname(data.getUser().getProfile().getFullname());
			postComment.setCreatedAt(data.getCreatedAt());
			postComment.setVer(data.getVersion());
			if (data.getComment() != null) {
				final PojoPostCommentReplyResData postCommentData = new PojoPostCommentReplyResData();

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
