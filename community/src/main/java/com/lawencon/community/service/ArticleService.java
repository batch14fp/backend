package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
import com.lawencon.community.pojo.article.PojoArticleInsertReq;
import com.lawencon.community.pojo.article.PojoArticleUpdateReq;
import com.lawencon.community.pojo.article.PojoResGetArticle;
import com.lawencon.community.pojo.article.PojoResGetArticleData;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ArticleService {

	private final ArticleDao articleDao;
	private final FileDao fileDao;
	private final UserDao userDao;

	
	@Inject
	private PrincipalService principalService;
	public ArticleService(final UserDao userDao, final ArticleDao articleDao, final FileDao fileDao) {
		this.articleDao = articleDao;
		this.fileDao = fileDao;
		this.userDao = userDao;	}

	public PojoResGetArticle getAll(int offset, int limit) {
		final PojoResGetArticle articles = new PojoResGetArticle();
		final List<PojoResGetArticleData> articleList = new ArrayList<>();
		articleDao.getAll(offset, limit).forEach(data -> {
			PojoResGetArticleData article = new PojoResGetArticleData();
			article.setArticleId(data.getId());
			article.setContent(data.getContentArticle());
			article.setImageId(data.getFile().getId());
			article.setViewers(data.getViewers());
			article.setIsActive(data.getIsActive());
			article.setUserId(data.getUser().getId());
			article.setNameUser(data.getUser().getProfile().getFullname());
			article.setTitle(data.getTitle());
			article.setVer(data.getVersion());
			articleList.add(article);

		});
		articles.setData(articleList);
		articles.setTotal(getTotalCount());
		return articles;
	}
	
	
	public List<PojoResGetArticleData> getAllByMostViewer(int offset, int limit) {
		final List<PojoResGetArticleData> res = new ArrayList<>();
		articleDao.getAllByMostViewer(offset, limit).forEach(data -> {
			PojoResGetArticleData article = new PojoResGetArticleData();
			article.setArticleId(data.getId());
			article.setContent(data.getContentArticle());
			article.setImageId(data.getFile().getId());
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

	public PojoResGetArticleData getById(String id){
		final PojoResGetArticleData article = new PojoResGetArticleData();
		ConnHandler.begin();
		final Article data = articleDao.getByIdRef(id);
		final Article dataUpdate = articleDao.getByIdAndDetach(data.getId()).get();
		dataUpdate.setViewers(dataUpdate.getViewers()+ 1);
	    articleDao.saveAndFlush(dataUpdate);
	    ConnHandler.commit();
		article.setArticleId(data.getId());
		article.setContent(data.getContentArticle());
		article.setImageId(data.getFile().getId());
		article.setIsActive(data.getIsActive());
		article.setUserId(data.getUser().getId());
		article.setViewers(data.getViewers());
		article.setNameUser(data.getUser().getProfile().getFullname());
		article.setTitle(data.getTitle());
		article.setVer(data.getVersion());
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

	public PojoUpdateRes update(PojoArticleUpdateReq data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			final Article article = articleDao.getByIdRef(data.getArticleId());
			articleDao.getByIdAndDetach(Article.class, article.getId());
			article.setId(article.getId());
			article.setContentArticle(data.getContent());
			final File file = fileDao.getByIdRef(data.getImageArticle());
			article.setFile(file);
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

	public PojoInsertRes save(PojoArticleInsertReq data) {
		ConnHandler.begin();
		final Article article = new Article();
		article.setContentArticle(data.getContent());
		final File file = fileDao.getByIdRef(data.getImageArticle());
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		article.setUser(user);
		article.setFile(file);
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
