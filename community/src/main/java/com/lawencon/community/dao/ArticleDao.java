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
	public Optional<Article> getById(String id) {
		return Optional.ofNullable(super.getById(Article.class, id));
	}

	@Override
	Optional<Article> getByIdRef(String id) {
		return Optional.ofNullable(super.getByIdRef(Article.class, id));
	}
	
	@Override
	public Optional<Article> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Article.class, id));

	}
}
