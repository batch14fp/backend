package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.model.Article;

public class ArticleDao extends BaseMasterDao<Article>{

	@Override
	public List<Article> getAll() {
		return null;
	}

	@Override
	public Optional<Article> getById(Long id) {
		return Optional.ofNullable(super.getById(Article.class, id));
	}

	@Override
	Optional<Article> getByIdRef(Long id) {
		return Optional.ofNullable(super.getByIdRef(Article.class, id));
	}
}
