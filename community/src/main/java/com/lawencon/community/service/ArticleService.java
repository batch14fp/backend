package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ArticleDao;
import com.lawencon.community.dao.ArticleViewerDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.ArticleViewer;
import com.lawencon.community.model.File;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.article.PojoArticleReqInsert;
import com.lawencon.community.pojo.article.PojoArticleReqUpdate;
import com.lawencon.community.pojo.article.PojoArticleRes;
import com.lawencon.community.pojo.article.PojoArticleResData;
import com.lawencon.community.util.GenerateString;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ArticleService {

	private final ArticleDao articleDao;
	private final FileDao fileDao;
	private final UserDao userDao;
	private final ArticleViewerDao articleViewerDao;

	
	@Autowired
	private PrincipalService principalService;
	public ArticleService(final ArticleViewerDao articleViewerDao, final UserDao userDao, final ArticleDao articleDao, final FileDao fileDao) {
		this.articleDao = articleDao;
		this.fileDao = fileDao;
		this.userDao = userDao;
		this.articleViewerDao = articleViewerDao;}



	private void validateNonBk(PojoArticleReqInsert article) {

		if (article.getContent() == null) {
			throw new RuntimeException("Article Content cannot be empty.");
		}

		if (article.getTitle() == null) {
			throw new RuntimeException("Article Title cannot be empty.");
		}
		
	}
	
	
	private void validateNonBk(PojoArticleReqUpdate article) {
		if (article.getArticleId() == null) {
			throw new RuntimeException("Article cannot be empty.");
		}
		if (article.getVer() == null) {
			throw new RuntimeException("Article version cannot be empty.");
		}
	}
	
	private void validateBkNotExist(String id) {
		if(articleDao.getById(id).isEmpty()) {
			throw new RuntimeException("Article cannot be empty.");
		}
	}
	
	
	public PojoArticleRes getAll(int offset, int limit) {
		final PojoArticleRes articles = new PojoArticleRes();
		final List<PojoArticleResData> articleList = new ArrayList<>();
		articleDao.getAll(offset, limit).forEach(data -> {
			PojoArticleResData article = new PojoArticleResData();
			article.setArticleId(data.getId());
			article.setContent(data.getContentArticle());
			article.setFileId(data.getFile().getId());
			article.setViewers(getCountViewer(data.getId()));
			article.setIsActive(data.getIsActive());
			article.setUserId(data.getUser().getId());
			article.setNameUser(data.getUser().getProfile().getFullname());
			article.setTitle(data.getTitle());
			article.setVer(data.getVersion());
			article.setCreatedAt(data.getCreatedAt());
			articleList.add(article);

		});
		articles.setData(articleList);
		articles.setTotal(getTotalCount());
		return articles;
	}
	
	
	
	public PojoArticleRes getAllArticle(int offset, int limit) {
		final PojoArticleRes articles = new PojoArticleRes();
		final List<PojoArticleResData> articleList = new ArrayList<>();
		articleDao.getAllArticle(offset, limit).forEach(data -> {
			PojoArticleResData article = new PojoArticleResData();
			article.setArticleId(data.getId());
			article.setContent(data.getContentArticle());
			article.setFileId(data.getFile().getId());
			article.setViewers(getCountViewer(data.getId()));
			article.setIsActive(data.getIsActive());
			article.setUserId(data.getUser().getId());
			article.setNameUser(data.getUser().getProfile().getFullname());
			article.setTitle(data.getTitle());
			article.setVer(data.getVersion());
			article.setCreatedAt(data.getCreatedAt());
			articleList.add(article);

		});
		articles.setData(articleList);
		articles.setTotal(getTotalCount());
		return articles;
	}
	
	
	public List<PojoArticleResData> getAllByMostViewer(int offset, int limit) {
		List<Article> listArticle = articleDao.getAllByMostViewer(offset, limit);
		List<Long> listViewer = articleDao.getNumViewersAllByMostViewer(offset, limit);

		final List<PojoArticleResData> listResData = new ArrayList<>();
		for (int i = 0; i <listArticle.size(); i++) {
			final Article article = listArticle.get(i);
			final Long viewers = listViewer.get(i);

			PojoArticleResData resData = new PojoArticleResData();
			resData.setArticleId(article.getId());
			resData.setUserId(article.getUser().getId());
			resData.setTitle(article.getTitle());
			resData.setContent(article.getContentArticle());
			if(article.getFile() != null ) {
			resData.setFileId( article.getFile().getId());
			resData.setFileContent( article.getFile().getFileContent() );
			resData.setFileExtension(article.getFile().getFileContent());
			resData.setFileVer(article.getFile().getVersion());
		
			}
			
			resData.setIsActive(article.getIsActive());
			resData.setNameUser(article.getUser().getProfile().getFullname());
			resData.setVer(article.getVersion());
			resData.setIsActive(article.getIsActive());
			resData.setCreatedAt(article.getCreatedAt());
			resData.setViewers(viewers);

			listResData.add(resData);
		}

		return listResData;
	}
	
	
	
	

	public List<PojoArticleResData> getAllForMember(int offset, int limit) {
		List<Article> listArticle = articleDao.getAllByCreatedAt(offset, limit);
		List<Long> listViewer = articleDao.getNumViewersAll(offset, limit);

		final List<PojoArticleResData> listResData = new ArrayList<>();
		for (int i = 0; i <listArticle.size(); i++) {
			final Article article = listArticle.get(i);
			final Long viewers = listViewer.get(i);

			PojoArticleResData resData = new PojoArticleResData();
			resData.setArticleId(article.getId());
			resData.setUserId(article.getUser().getId());
			resData.setTitle(article.getTitle());
			resData.setContent(article.getContentArticle());
			if(article.getFile() != null ) {
			resData.setFileId( article.getFile().getId());
			resData.setFileContent( article.getFile().getFileContent() );
			resData.setFileExtension(article.getFile().getFileContent());
			resData.setFileVer(article.getFile().getVersion());
		
			}
			
			resData.setIsActive(article.getIsActive());
			resData.setNameUser(article.getUser().getProfile().getFullname());
			resData.setVer(article.getVersion());
			resData.setIsActive(article.getIsActive());
			resData.setCreatedAt(article.getCreatedAt());
			resData.setViewers(viewers);

			listResData.add(resData);
		}

		return listResData;
	}
	
	public PojoArticleResData getByIdForMember(String id){
		final PojoArticleResData article = new PojoArticleResData();
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		final Article data = articleDao.getByIdRef(id);
		if(!articleViewerDao.getIsView(user.getId(), data.getId())) {
			ConnHandler.begin();
			ArticleViewer articleViewer = new ArticleViewer();
			articleViewer.setArticle(data);
			articleViewer.setUser(user);
			articleViewer.setIsActive(true);
			articleViewerDao.save(articleViewer);
		    ConnHandler.commit();
		}
		article.setArticleId(data.getId());
		article.setContent(data.getContentArticle());
		article.setFileId(data.getFile().getId());
		article.setFileContent(data.getFile().getFileContent());
		article.setFileExtension(data.getFile().getFileExtension());
		article.setFileVer(data.getFile().getVersion());
		article.setIsActive(data.getIsActive());
		article.setUserId(data.getUser().getId());
		article.setViewers(getCountViewer(data.getId()));
		article.setNameUser(data.getUser().getProfile().getFullname());
		article.setTitle(data.getTitle());
		article.setVer(data.getVersion());
		article.setCreatedAt(data.getCreatedAt());
		return article;
	}
	
	

	private Long getCountViewer(String articleId) {
		Long countLike = articleViewerDao.countViewer(articleId);
		return countLike;
	}
	public PojoArticleResData getByIdForAdmin(String id){
		final PojoArticleResData article = new PojoArticleResData();
		final Article data = articleDao.getByIdRef(id);
		final Article dataUpdate = articleDao.getByIdAndDetach(data.getId()).get();
		article.setArticleId(dataUpdate.getId());
		article.setContent(dataUpdate.getContentArticle());
		article.setFileId(dataUpdate.getFile().getId());
		article.setFileContent(dataUpdate.getFile().getFileContent());
		article.setFileExtension(dataUpdate.getFile().getFileExtension());
		article.setFileVer(dataUpdate.getFile().getVersion());
		article.setIsActive(dataUpdate.getIsActive());
		article.setUserId(dataUpdate.getUser().getId());
		article.setViewers(getCountViewer(data.getId()));
		article.setNameUser(dataUpdate.getUser().getProfile().getFullname());
		article.setTitle(dataUpdate.getTitle());
		article.setVer(dataUpdate.getVersion());
		article.setCreatedAt(dataUpdate.getCreatedAt());
		return article;
	}

	public PojoRes deleteById(String id) {
		validateBkNotExist(id);
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = articleDao.deleteById(Article.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}

	public PojoUpdateRes update(PojoArticleReqUpdate data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			validateNonBk(data);
			
			ConnHandler.begin();
			final Article article = articleDao.getByIdRef(data.getArticleId());
			articleDao.getByIdAndDetach(Article.class, article.getId());
			article.setId(article.getId());
			article.setContentArticle(data.getContent());
			final File file = fileDao.getByIdRef(data.getFileId());
			file.setFileContent(data.getFileContent());
			file.setFileExtension(data.getFileExtension());
			file.setVersion(data.getVer());
			final File fileUpdated = fileDao.saveAndFlush(file);
			article.setFile(fileUpdated);
			article.setVersion(data.getVer());
			article.setTitle(data.getTitle());
			final Article articleNew = articleDao.saveAndFlush(article);
			ConnHandler.commit();
			pojoUpdateRes.setId(articleNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(articleNew.getVersion());

		} catch (Exception e) {
			e.printStackTrace();
			pojoUpdateRes.setId(data.getArticleId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update the data");
		}
		return pojoUpdateRes;

	}

	public PojoInsertRes save(PojoArticleReqInsert data) {
		validateNonBk(data);
		
		ConnHandler.begin();
		final Article article = new Article();
		article.setContentArticle(data.getContent());
		final File file = new File();
		file.setFileExtension(data.getExtensions());
		file.setFileName(GenerateString.generateFileName(data.getExtensions()));
		file.setFileContent(data.getFileContent());
		file.setIsActive(true);
		final File fileNew = fileDao.save(file);

		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		article.setUser(user);
		article.setFile(fileNew);
		article.setIsActive(true);
		article.setTitle(data.getTitle());
		article.setIsActive(true);
		final Article articleNew = articleDao.save(article);
		ConnHandler.commit();
		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(articleNew.getId());
		pojoRes.setMessage("Update Success!");
		return pojoRes;
	}
	public Long getTotalCount() {

		return articleDao.getTotalCount();
	}
}
