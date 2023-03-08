package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Article;

public class ArticleDao extends BaseMasterDao<Article>{

	@Override
	List<Article> getAll() {
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	Optional<Article> getById(Long id) {
		final Article article = ConnHandler.getManager().find(Article.class, id);
		return Optional.empty();
	}

	@SuppressWarnings("hiding")
	@Override
	public <Article> Article getByIdRef(Class<Article> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}
}
