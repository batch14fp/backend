package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.File;
import com.lawencon.community.model.FilePost;
import com.lawencon.community.model.Post;

@Repository
public class FilePostDao extends AbstractJpaDao{
	

	@SuppressWarnings("unchecked")
	public List<FilePost> getAllFilePost() {
		final List<FilePost> listFilePosts = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, file_id, post_id ");
		sql.append("FROM t_file_post ");

		final List<Object[]> listObj = ConnHandler.getManager().createNativeQuery(sql.toString()).getResultList();
		for (Object[] obj : listObj) {
			final FilePost filePost = new FilePost();
			filePost.setId(obj[0].toString());

			final File file = new File();
			file.setId(obj[1].toString());
		
			filePost.setFile(file);

			final Post post = new Post();
			post.setId(obj[2].toString());
		
			filePost.setPost(post);

			listFilePosts.add(filePost);
		}

		return listFilePosts;
	}
	
	@SuppressWarnings("unchecked")
	public List<FilePost> getAllFileByPostId(final String postId){
		final List<FilePost> listFilePosts = new ArrayList<>();
		try {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT id, file_id, post_id ");
		sqlQuery.append("FROM t_file_post ");
		sqlQuery.append("WHERE post_id = :postId");

		final List<Object[]> listObj = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("postId", postId)
				.getResultList();
		for (Object[] obj : listObj) {
			final FilePost filePost = new FilePost();
			filePost.setId(obj[0].toString());

			final File file = new File();
			file.setId(obj[1].toString());
		
			filePost.setFile(file);

			final Post post = new Post();
			post.setId(obj[2].toString());
		
			filePost.setPost(post);

			listFilePosts.add(filePost);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return listFilePosts;
	}



	

}
