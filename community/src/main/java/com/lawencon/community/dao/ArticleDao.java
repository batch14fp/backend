package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Article;

public class ArticleDao extends BaseMasterDao<Article>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getAll() {
		final String sql = "SELECT * FROM t_article WHERE is_active = TRUE";	
		final List<Article> res = ConnHandler.getManager().createNativeQuery(sql, Article.class).getResultList();
		
		return res;
	}

	@Override
	public Optional<Article> getById(String id) {
		return Optional.ofNullable(super.getById(Article.class, id));
	}

	@Override
	public Article getByIdRef(String id) {
		return super.getByIdRef(Article.class, id);
	}
	
	@Override
	public Optional<Article> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Article.class, id));

	}
}
