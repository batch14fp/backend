package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;

@Repository
public class ArticleDao extends AbstractJpaDao {

	@SuppressWarnings("unchecked")
	public List<Article> getAll(int offset, int limit) {

		final List<Article> listArticle = new ArrayList<>();
		final StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append(
				"SELECT a.id, a.file_id, a.title, a.content_article, a.user_id, u.profile_id, p.fullname, a.ver, a.is_active, a.created_at ");
		sqlQuery.append("FROM t_article a ");
		sqlQuery.append("INNER JOIN t_user u ON u.id = a.user_id ");
		sqlQuery.append("INNER JOIN t_profile p ON p.id = u.profile_id ");
		sqlQuery.append("WHERE a.is_active = TRUE ");
		sqlQuery.append("ORDER BY a.created_at DESC");

		try {
			final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setMaxResults(limit)
					.setFirstResult(offset)
					.getResultList();

			for (final Object objs : result) {
				final Object[] obj = (Object[]) objs;

				final Article article = new Article();
				article.setId(obj[0].toString());

				final File file = new File();
				if (obj[1].toString() != null) {
					file.setId(obj[1].toString());
					article.setFile(file);
				}
				article.setTitle(obj[2].toString());
				article.setContentArticle(obj[3].toString());

				final User user = new User();
				user.setId(obj[4].toString());

				final Profile profile = new Profile();
				profile.setId(obj[5].toString());
				profile.setFullname(obj[6].toString());
				user.setProfile(profile);

				article.setUser(user);

				article.setVersion(Integer.valueOf(obj[7].toString()));
				article.setIsActive(Boolean.valueOf(obj[8].toString()));
				article.setCreatedAt(Timestamp.valueOf(obj[9].toString()).toLocalDateTime());
				listArticle.add(article);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return listArticle;
	}
	@SuppressWarnings("unchecked")
	public List<Article> getAllArticle(int offset, int limit) {
		final List<Article> listArticle = new ArrayList<>();
		final StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append(
				"SELECT a.id, a.file_id, a.title, a.content_article,  a.user_id, u.profile_id, p.fullname, a.ver, a.is_active, a.created_at ");
		sqlQuery.append("FROM t_article a ");
		sqlQuery.append("INNER JOIN t_user u ON u.id = a.user_id ");
		sqlQuery.append("INNER JOIN t_profile p ON p.id = u.profile_id ");
		sqlQuery.append("WHERE a.is_active = TRUE ");
		sqlQuery.append("ORDER BY a.created_at DESC");

		try {
			final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setMaxResults(limit)
					.setFirstResult((offset-1)*limit)
					.getResultList();

			for (final Object objs : result) {
				final Object[] obj = (Object[]) objs;

				final Article article = new Article();
				article.setId(obj[0].toString());

				final File file = new File();
				if (obj[1].toString() != null) {
					file.setId(obj[1].toString());
					article.setFile(file);
				}
				article.setTitle(obj[2].toString());
				article.setContentArticle(obj[3].toString());

				final User user = new User();
				user.setId(obj[4].toString());

				final Profile profile = new Profile();
				profile.setId(obj[5].toString());
				profile.setFullname(obj[6].toString());
				user.setProfile(profile);

				article.setUser(user);

				article.setVersion(Integer.valueOf(obj[7].toString()));
				article.setIsActive(Boolean.valueOf(obj[8].toString()));
				article.setCreatedAt(Timestamp.valueOf(obj[9].toString()).toLocalDateTime());
				listArticle.add(article);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return listArticle;
	}
	

	@SuppressWarnings("unchecked")
	public List<Article> getAllByMostViewer(int offset, int limit) {

	    final List<Article> listArticle = new ArrayList<>();
	    final StringBuilder sqlQuery = new StringBuilder();

	    sqlQuery.append("SELECT a.id, a.file_id, f.file_name, f.file_content, f.file_extension, a.title, a.content_article, a.user_id, u.profile_id, p.fullname, a.ver, a.is_active, a.created_at ");
	    sqlQuery.append("FROM t_article a ");
	    sqlQuery.append("INNER JOIN t_user u ON u.id = a.user_id ");
	    sqlQuery.append("INNER JOIN t_profile p ON p.id = u.profile_id ");
	    sqlQuery.append("LEFT JOIN t_article_viewer av ON a.id = av.article_id ");
	    sqlQuery.append("INNER JOIN t_file f ON f.id  = a.file_id ");
	    sqlQuery.append("WHERE a.is_active = TRUE ");
	    sqlQuery.append("GROUP BY a.id, a.file_id, a.title, a.content_article, a.user_id, u.profile_id, p.fullname, a.ver, a.is_active, f.file_name, f.file_content, f.file_extension ");
	    sqlQuery.append("ORDER BY COUNT(av.article_id) DESC ");
	    try {
	        final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	                .setMaxResults(limit).setFirstResult((offset-1)*limit).getResultList();

	        for (final Object objs : result) {
	            final Object[] obj = (Object[]) objs;

	            final Article article = new Article();
	            article.setId(obj[0].toString());

	            final File file = new File();
	            if (obj[1] != null) {
	                file.setId(obj[1].toString());
	                file.setFileName(obj[2].toString());
	                file.setFileContent(obj[3].toString());
	                file.setFileExtension(obj[4].toString());
	                
	                article.setFile(file);
	            }
	            article.setTitle(obj[5].toString());
	            article.setContentArticle(obj[6].toString());

	            final User user = new User();
	            user.setId(obj[7].toString());

	            final Profile profile = new Profile();
	            profile.setId(obj[8].toString());
	            profile.setFullname(obj[9].toString());
	            user.setProfile(profile);
	            article.setUser(user);
	            article.setVersion(Integer.valueOf(obj[10].toString()));
	            article.setIsActive(Boolean.valueOf(obj[11].toString()));
	            article.setCreatedAt(Timestamp.valueOf(obj[12].toString()).toLocalDateTime());
	            listArticle.add(article);
	        }

	    } catch (final Exception e) {
	        e.printStackTrace();
	    }

	    return listArticle;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllByCreatedAt(int offset, int limit) {
	    final List<Article> listArticle = new ArrayList<>();
	    final StringBuilder sqlQuery = new StringBuilder();

	    sqlQuery.append("SELECT a.id, a.file_id, f.file_name, f.file_content, f.file_extension, a.title, a.content_article, a.user_id, u.profile_id, p.fullname, a.ver, a.is_active, a.created_at ");
	    sqlQuery.append("FROM t_article a ");
	    sqlQuery.append("INNER JOIN t_user u ON u.id = a.user_id ");
	    sqlQuery.append("INNER JOIN t_profile p ON p.id = u.profile_id ");
	    sqlQuery.append("LEFT JOIN t_article_viewer av ON a.id = av.article_id ");
	    sqlQuery.append("INNER JOIN t_file f ON f.id  = a.file_id ");
	    sqlQuery.append("WHERE a.is_active = TRUE ");
	    sqlQuery.append("ORDER BY a.created_at DESC ");
	    try {
	        final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	                .setMaxResults(limit).setFirstResult((offset-1)*limit).getResultList();

	        for (final Object objs : result) {
	            final Object[] obj = (Object[]) objs;

	            final Article article = new Article();
	            article.setId(obj[0].toString());

	            final File file = new File();
	            if (obj[1] != null) {
	                file.setId(obj[1].toString());
	                file.setFileName(obj[2].toString());
	                file.setFileContent(obj[3].toString());
	                file.setFileExtension(obj[4].toString());
	                
	                article.setFile(file);
	            }
	            article.setTitle(obj[5].toString());
	            article.setContentArticle(obj[6].toString());

	            final User user = new User();
	            user.setId(obj[7].toString());

	            final Profile profile = new Profile();
	            profile.setId(obj[8].toString());
	            profile.setFullname(obj[9].toString());
	            user.setProfile(profile);
	            article.setUser(user);
	            article.setVersion(Integer.valueOf(obj[10].toString()));
	            article.setIsActive(Boolean.valueOf(obj[11].toString()));
	            article.setCreatedAt(Timestamp.valueOf(obj[12].toString()).toLocalDateTime());
	            listArticle.add(article);
	        }

	    } catch (final Exception e) {
	        e.printStackTrace();
	    }

	    return listArticle;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Long> getNumViewersAll(int offset, int limit){
	    final List<Long> listNumViews = new ArrayList<>();
	    final StringBuilder sqlQuery = new StringBuilder();
	    sqlQuery.append("SELECT a.id, COALESCE(COUNT(av.article_id), 0) AS num_views ");
	    sqlQuery.append("FROM t_article a ");
	    sqlQuery.append("INNER JOIN t_user u ON u.id = a.user_id ");
	    sqlQuery.append("INNER JOIN t_profile p ON p.id = u.profile_id ");
	    sqlQuery.append("LEFT JOIN t_article_viewer av ON a.id = av.article_id ");
	    sqlQuery.append("WHERE a.is_active = TRUE ");
	    sqlQuery.append("GROUP BY a.id ");
	    sqlQuery.append("ORDER BY a.created_at DESC ");
	    try {
	        final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	                .setMaxResults(limit).setFirstResult((offset-1)*limit).getResultList();

	        for (final Object objs : result) {
	            final Object[] obj = (Object[]) objs;
	            listNumViews.add(Long.valueOf(obj[1].toString()));
	        }

	    } catch (final Exception e) {
	        e.printStackTrace();
	    }

	    return listNumViews;
	}

	
	
	@SuppressWarnings("unchecked")
	public List<Long> getNumViewersAllByMostViewer(int offset, int limit){
	    final List<Long> listNumViews = new ArrayList<>();
	    final StringBuilder sqlQuery = new StringBuilder();
	    sqlQuery.append("SELECT a.id, COALESCE(COUNT(av.article_id), 0) AS num_views ");
	    sqlQuery.append("FROM t_article a ");
	    sqlQuery.append("INNER JOIN t_user u ON u.id = a.user_id ");
	    sqlQuery.append("INNER JOIN t_profile p ON p.id = u.profile_id ");
	    sqlQuery.append("LEFT JOIN t_article_viewer av ON a.id = av.article_id ");
	    sqlQuery.append("WHERE a.is_active = TRUE ");
	    sqlQuery.append("GROUP BY a.id ");
	    sqlQuery.append("ORDER BY COUNT(av.article_id) DESC ");
	    try {
	        final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
	                .setMaxResults(limit).setFirstResult((offset-1)*limit).getResultList();

	        for (final Object objs : result) {
	            final Object[] obj = (Object[]) objs;
	            listNumViews.add(Long.valueOf(obj[1].toString()));
	        }

	    } catch (final Exception e) {
	        e.printStackTrace();
	    }

	    return listNumViews;
	}

	public Long getTotalCount() {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT COUNT(id) as total FROM t_article ");
		sqlQuery.append("WHERE is_active = TRUE ");
		Long totalCount = Long
				.valueOf(ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).getSingleResult().toString());
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
