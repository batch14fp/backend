package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.File;



@Repository
public class ArticleDao extends AbstractJpaDao{

	
	@SuppressWarnings("unchecked")
	public List<Article> getAll(int offset, int limit) {
		
		final List<Article> listArticle = new ArrayList<>();
		final StringBuilder sqlQuery = new StringBuilder();
		
		sqlQuery.append("SELECT id, file_id, title, content_article, ver,is_active ");
		sqlQuery.append("FROM t_article ");
		sqlQuery.append("WHERE is_active = TRUE ");
		sqlQuery.append("ORDER BY p.created_at DESC ");
		sqlQuery.append("LIMIT :limit OFFSET :offset ");

		try {
		final List<Object> result = ConnHandler.getManager()
				.createNativeQuery(sqlQuery.toString())
				.setParameter("offset", offset)
				.setParameter("limit",limit)
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
	
	
	

    public int getTotalCount() {
    	final StringBuilder sqlQuery = new StringBuilder();
    	sqlQuery.append("SELECT COUNT(id) as total FROM t_article");
    	sqlQuery.append("WHERE is_active = TRUE ");
        int totalCount = Integer.valueOf(ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
        .getSingleResult().toString()); 
        return totalCount;
    }


	public Optional<Article> getById(String id) {
		return Optional.ofNullable(super.getById(Article.class, id));
	}


	public Article getByIdRef(String id) {
		return super.getByIdRef(Article.class, id);
	}
	

	public Optional<Article> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Article.class, id));

	}
}
