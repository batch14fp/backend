package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostComment;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;

@Repository
public class PostCommentDao extends AbstractJpaDao {

	@SuppressWarnings("unchecked")
	public List<PostComment> getAllByPostId(final String postId, final int limit, final int offset) throws Exception {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT pc.id AS pc_id, pc.user_id AS pc_user_id, ");
		sql.append("pc.post_id AS pc_post_id, c.comment_id AS c_comment_id, ");
		sql.append("c.user_id AS c_user_id, c.post_id AS c_post_id, pc.body, ");
		sql.append(
				"c.created_at as c_created_at, pr.fullname, p.content_post, pc.created_at as pc_created_at, pc.ver, pr.image_profile_id, pr.position_id ");
		sql.append("FROM t_post_comment pc ");
		sql.append("LEFT JOIN t_post_comment c ON pc.comment_id = c.id ");
		sql.append("INNER JOIN t_user u ON pc.user_id = u.id ");
		sql.append("INNER JOIN t_profile pr ON u.profile_id = pr.id ");
		sql.append("INNER JOIN t_post p ON pc.post_id = p.id ");
		sql.append("WHERE pc.post_id = :postId ");
		sql.append("ORDER BY pc.created_at DESC ");

		try {
			final List<Object[]> listObj = ConnHandler.getManager().createNativeQuery(sql.toString())
					.setParameter("postId", postId).setMaxResults(limit).setFirstResult((offset-1)*limit).getResultList();
			final List<PostComment> listResult = new ArrayList<>();
			for (Object[] obj : listObj) {
				final PostComment postComment = new PostComment();
				postComment.setId((obj[0].toString()));
				final User user = new User();
				final Profile profile = new Profile();
				user.setId(obj[1].toString());

				profile.setFullname(obj[8].toString());
				if (obj[12] != null) {
					final File file = new File();
					file.setId(obj[12].toString());
					profile.setImageProfile(file);
				}
				final Position position = new Position();
				position.setId(obj[13].toString());

				profile.setPosition(position);

				user.setProfile(profile);
				postComment.setUser(user);

				final Post post = new Post();
				post.setId(obj[2].toString());
				postComment.setPost(post);

				if (obj[3] != null) {
					final PostComment postCommentReply = new PostComment();
					postCommentReply.setId((String) obj[3]);
					final User userComment = new User();
					userComment.setId(obj[4].toString());

					postCommentReply.setUser(userComment);

					final Post postReply = new Post();
					postReply.setId(obj[5].toString());
					postComment.setPost(postReply);
					postReply.setCreatedAt(Timestamp.valueOf(obj[7].toString()).toLocalDateTime());
					postComment.setComment(postCommentReply);

				}
				postComment.setBody(obj[6].toString());
				postComment.getPost().setContentPost(obj[9].toString());
				postComment.setCreatedAt((Timestamp.valueOf(obj[10].toString()).toLocalDateTime()));
				postComment.setVersion(Integer.valueOf(obj[11].toString()));
				listResult.add(postComment);
			}
			return listResult;
		} catch (Exception e) {
			throw new Exception("Error if execute query getAllByPostId: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<PostComment> getByPostId(final String postId) {
		final StringBuilder sql = new StringBuilder();
		final List<PostComment> listResult = new ArrayList<>();
	
		sql.append("SELECT pc.id AS pc_id, pc.user_id AS pc_user_id, ");
		sql.append("pc.post_id AS pc_post_id, c.comment_id AS c_comment_id, ");
		sql.append("c.user_id AS c_user_id, c.post_id AS c_post_id, pc.body, ");
		sql.append(
				"c.created_at as c_created_at, pr.fullname, p.content_post, pc.created_at as pc_created_at, pc.ver, pr.image_profile_id, pr.position_id ");
		sql.append("FROM t_post_comment pc ");
		sql.append("LEFT JOIN t_post_comment c ON pc.comment_id = c.id ");
		sql.append("INNER JOIN t_user u ON pc.user_id = u.id ");
		sql.append("INNER JOIN t_profile pr ON u.profile_id = pr.id ");
		sql.append("INNER JOIN t_post p ON pc.post_id = p.id ");
		sql.append("WHERE pc.post_id = :postId ");
		sql.append("ORDER BY pc.created_at DESC ");
		try {
		final List<Object[]> listObj = ConnHandler.getManager().createNativeQuery(sql.toString())
				.setParameter("postId", postId).getResultList();

		for (Object[] obj : listObj) {
			final PostComment postComment = new PostComment();
			postComment.setId((obj[0].toString()));
			final User user = new User();
			final Profile profile = new Profile();
			user.setId(obj[1].toString());

			if (obj[12] != null) {
				final File file = new File();
				file.setId(obj[12].toString());
				profile.setImageProfile(file);
			}
			final Position position = new Position();
			position.setId(obj[13].toString());

			profile.setPosition(position);
			profile.setFullname(obj[8].toString());
			user.setProfile(profile);
			postComment.setUser(user);

			final Post post = new Post();
			post.setId(obj[2].toString());
			postComment.setPost(post);

			if (obj[3] != null) {
				final PostComment postCommentReply = new PostComment();
				postCommentReply.setId((String) obj[3]);
				final User userComment = new User();
				userComment.setId(obj[4].toString());

				postCommentReply.setUser(userComment);

				final Post postReply = new Post();
				postReply.setId(obj[5].toString());

				postReply.setCreatedAt(Timestamp.valueOf(obj[7].toString()).toLocalDateTime());
				postComment.setPost(postReply);
				postComment.setComment(postCommentReply);

			}
			postComment.setBody(obj[6].toString());
			postComment.getPost().setContentPost(obj[9].toString());
			postComment.setCreatedAt((Timestamp.valueOf(obj[10].toString()).toLocalDateTime()));
			postComment.setVersion(Integer.valueOf(obj[11].toString()));
			listResult.add(postComment);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return listResult;

	}

	public Optional<PostComment> getById(String id) {
		return Optional.ofNullable(super.getById(PostComment.class, id));
	}

	public PostComment getByIdRef(String id) {
		return super.getByIdRef(PostComment.class, id);
	}

	public Optional<PostComment> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PostComment.class, id));

	}

	public Long getCountPostComment(final String postId) {
		final StringBuilder sql = new StringBuilder();
		Long count = null;
		sql.append("SELECT COUNT(id) FROM t_post_comment ");
		sql.append("WHERE post_id = :postId ");

		count = Long.valueOf(ConnHandler.getManager().createNativeQuery(sql.toString())

				.setParameter("postId", postId).getSingleResult().toString());

		return count;

	}

}
