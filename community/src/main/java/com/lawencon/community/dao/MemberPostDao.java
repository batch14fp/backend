package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.MemberPost;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;

@Repository
public class MemberPostDao extends BaseMasterDao<MemberPost> {

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberPost> getAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT mp.id, u.id, p.id ");
		sb.append("FROM t_member_post mp ");
		sb.append("INNER JOIN m_user u ON u.id = mp.member_id ");
		sb.append("INNER JOIN t_post p ON p.id = mp.post_id ");

		final List<MemberPost> memberPosts = new ArrayList<>();
		try {

			final List<Object[]> result = ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();

			for (Object[] obj : result) {
				final MemberPost memberPost = new MemberPost();
				memberPost.setId(obj[0].toString());

				final User user = new User();
				user.setId(obj[1].toString());
				memberPost.setMember(user);

				final Post post = new Post();
				post.setId(obj[2].toString());
				memberPost.setPost(post);
				memberPosts.add(memberPost);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberPosts;

	}

	@Override
	public Optional<MemberPost> getById(String id) {
		return Optional.ofNullable(super.getById(MemberPost.class, id));
	}

	@Override
	public MemberPost getByIdRef(String id) {
		return super.getByIdRef(MemberPost.class, id);
	}

	@Override
	public Optional<MemberPost> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(MemberPost.class, id));

	}

}
