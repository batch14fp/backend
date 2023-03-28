package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.ArticleViewer;
import com.lawencon.community.model.User;

@Repository
public class ArticleViewerDao extends AbstractJpaDao{
	
	
	@SuppressWarnings("unchecked")
	public List<ArticleViewer> getByIdPost(String articleId) {
		 StringBuilder sqlQuery = new StringBuilder();
		 final List<ArticleViewer> result = new ArrayList<>();
		 try {
		 sqlQuery.append("SELECT id, user_id, article_id ");
		 sqlQuery.append("FROM t_article_viewer ");
		 sqlQuery.append("WHERE article_id = :articleId ");
		 sqlQuery.append("AND is_active = TRUE");

		    final List<Object[]> articleViewerList =  ConnHandler
				    .getManager()
				    .createNativeQuery(sqlQuery.toString())
				    .setParameter("articleId", articleId)
				    .getResultList();
		
		    for (Object[] obj : articleViewerList) {
		        final ArticleViewer articleViewer = new ArticleViewer();
		        articleViewer.setId(obj[0].toString());

		        final User user = new User();
		        user.setId((String) obj[1]);
		        articleViewer.setUser(user);

		        final Article article = new Article();
		        article.setId(obj[2].toString());
		        articleViewer.setArticle(article);

		        result.add(articleViewer);
		    }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		    return result;
	}
	
	
	public Long countViewer( String artcileId) {
		try {
		final StringBuilder sql = new StringBuilder();
		Long count =null;
		sql.append("SELECT COUNT(id) FROM t_article_viewer ");
		sql.append("WHERE article_id = :artcileId ");
		sql.append("AND is_active = TRUE "); 

		count= Long.valueOf(ConnHandler.getManager().createNativeQuery(sql.toString())
				.setParameter("artcileId",artcileId)
				.getSingleResult().toString());
		
		return count;	
		}catch (NonUniqueResultException e) {
	        throw new IllegalStateException("Expected single result, but got multiple", e);
	    } 
	    catch (Exception e) {
	        throw new RuntimeException("Unexpected error", e);
	    }
	}
	
	
	@SuppressWarnings("unused")
	public Boolean getIsView(String userId , String articleId) {
		 Boolean data = false;
		 try {
		 final StringBuilder sqlQuery = new StringBuilder();
		 sqlQuery.append("SELECT id, user_id, article_id ");
		 sqlQuery.append("FROM t_article_viewer ");
		 sqlQuery.append("WHERE user_id= :userId ");
		 sqlQuery.append("AND article_id = :articleId ");
		 sqlQuery.append("AND is_active = TRUE" );
	
		    final Object result = 
	  		  ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	  		  .setParameter("userId", userId)
	  		  .setParameter("articleId", articleId)
	  		  .getSingleResult();

			data = true;
		 
		 }catch(final Exception e)
		 {}
		    return data;
	}
	
		

}
