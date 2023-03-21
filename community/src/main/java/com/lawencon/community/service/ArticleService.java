package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ArticleDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Article;
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

	
	@Autowired
	private PrincipalService principalService;
	public ArticleService(final UserDao userDao, final ArticleDao articleDao, final FileDao fileDao) {
		this.articleDao = articleDao;
		this.fileDao = fileDao;
		this.userDao = userDao;	}

	public PojoArticleRes getAll(int offset, int limit) {
		final PojoArticleRes articles = new PojoArticleRes();
		final List<PojoArticleResData> articleList = new ArrayList<>();
		articleDao.getAll(offset, limit).forEach(data -> {
			PojoArticleResData article = new PojoArticleResData();
			article.setArticleId(data.getId());
			article.setContent(data.getContentArticle());
			article.setFileId(data.getFile().getId());
			article.setViewers(data.getViewers());
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
		final List<PojoArticleResData> res = new ArrayList<>();
		articleDao.getAllByMostViewer(offset, limit).forEach(data -> {
			PojoArticleResData article = new PojoArticleResData();
			article.setArticleId(data.getId());
			article.setContent(data.getContentArticle());
			article.setFileId(data.getFile().getId());
			article.setViewers(data.getViewers());
			article.setIsActive(data.getIsActive());
			article.setUserId(data.getUser().getId());
			article.setNameUser(data.getUser().getProfile().getFullname());
			article.setTitle(data.getTitle());
			article.setVer(data.getVersion());
			res.add(article);

		});
		return res;
	}

	public PojoArticleResData getByIdForMember(String id){
		final PojoArticleResData article = new PojoArticleResData();
		ConnHandler.begin();
		final Article data = articleDao.getByIdRef(id);
		final Article dataUpdate = articleDao.getByIdAndDetach(data.getId()).get();
		dataUpdate.setViewers(dataUpdate.getViewers()+ 1);
	    articleDao.saveAndFlush(dataUpdate);
	    ConnHandler.commit();
		article.setArticleId(data.getId());
		article.setContent(data.getContentArticle());
		article.setFileId(data.getFile().getId());
		article.setFileContent(data.getFile().getFileContent());
		article.setFileExtension(data.getFile().getFileExtension());
		article.setFileVer(data.getFile().getVersion());
		article.setIsActive(data.getIsActive());
		article.setUserId(data.getUser().getId());
		article.setViewers(data.getViewers());
		article.setNameUser(data.getUser().getProfile().getFullname());
		article.setTitle(data.getTitle());
		article.setVer(data.getVersion());
		article.setCreatedAt(data.getCreatedAt());
		return article;
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
		article.setViewers(dataUpdate.getViewers());
		article.setNameUser(dataUpdate.getUser().getProfile().getFullname());
		article.setTitle(dataUpdate.getTitle());
		article.setVer(dataUpdate.getVersion());
		article.setCreatedAt(dataUpdate.getCreatedAt());
		return article;
	}

	public PojoRes deleteById(String id) {
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
		article.setViewers(0);
		article.setTitle(data.getTitle());
		article.setIsActive(true);
		final Article articleNew = articleDao.save(article);
		ConnHandler.commit();
		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(articleNew.getId());
		pojoRes.setMessage("Update Success!");
		return pojoRes;
	}

	public int getTotalCount() {

		return articleDao.getTotalCount();
	}
}
