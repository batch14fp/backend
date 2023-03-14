package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.File;



@Repository
public class ArticleDao extends BaseMasterDao<Article>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getAll() {
		
		final List<Article> listArticle = new ArrayList<>();
		final StringBuilder sqlQuery = new StringBuilder();
		
		sqlQuery.append("SELECT id, file_id, title, content_article, ver,is_active ");
		sqlQuery.append("FROM t_article WHERE is_active = TRUE ");	
		try {
		final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.getResultList();
		
		for(final Object objs : result) {
			final Object[] obj  = (Object[]) objs;
			
			final Article article = new Article();
			article.setId(obj[0].toString());
			
			final File file = new File();
			if(obj[1].toString() != null) {
			file.setId(obj[1].toString());
			article.setFile(file);
			}
			article.setTitle(obj[2].toString());
			article.setContentArticle(obj[3].toString());
			article.setViewers(Integer.valueOf(obj[4].toString()));
			article.setVersion(Integer.valueOf(obj[5].toString()));
			article.setIsActive(Boolean.valueOf(obj[6].toString()));
			
			listArticle.add(article);
		}
		
		}catch(final Exception e) {
			e.printStackTrace();
		}
		
		return listArticle;
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
