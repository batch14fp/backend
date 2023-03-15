package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ArticleDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.File;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.article.PojoArticleInsertReq;
import com.lawencon.community.pojo.article.PojoArticleUpdateReq;
import com.lawencon.community.pojo.article.PojoResGetArticle;

@Service
public class ArticleService {

	private final ArticleDao articleDao;
	private final FileDao fileDao;

	public ArticleService(final ArticleDao articleDao, final FileDao fileDao) {
		this.articleDao = articleDao;
		this.fileDao = fileDao;
	}

	public List<PojoResGetArticle> getAll(int offset, int limit) {
		final List<PojoResGetArticle> res = new ArrayList<>();
		articleDao.getAll(offset, limit).forEach(data -> {
			PojoResGetArticle article = new PojoResGetArticle();
			article.setArticleId(data.getId());
			article.setContent(data.getContentArticle());
			article.setImageId(data.getFile().getId());
			article.setIsActive(data.getIsActive());
			article.setNameUser("perlu ditambah");
			article.setTitle(data.getTitle());
			article.setVer(data.getVersion());
			res.add(article);

		});
		return res;
	}

	public PojoResGetArticle getById(String id) {
		final PojoResGetArticle article = new PojoResGetArticle();
		final Article data = articleDao.getByIdRef(id);
		article.setArticleId(data.getId());
		article.setContent(data.getContentArticle());
		article.setImageId(data.getFile().getId());
		article.setIsActive(data.getIsActive());
		article.setNameUser("Perlu ditambah");
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
		article.setFile(file);
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
