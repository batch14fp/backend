package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.MemberPost;



@Repository
public class MemberPostDao extends BaseMasterDao<MemberPost>{

	@SuppressWarnings("unchecked")
	@Override
	List<MemberPost> getAll() {
		final String sql = "SELECT * FROM t_member_post WHERE  is_active = TRUE";	
		final List<MemberPost> res = ConnHandler.getManager().createNativeQuery(sql, MemberPost.class).getResultList();
		
		return res;
	}

	@Override
	Optional<MemberPost> getById(Long id) {
		return Optional.ofNullable(super.getById(MemberPost.class, id));
	}

	@SuppressWarnings("hiding")
	@Override
	public <MemberPost> MemberPost getByIdRef(Class<MemberPost> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}


	@SuppressWarnings("unchecked")
	List<MemberPost> getByOffsetLimit(Long offset, Long limit) {
		  final String sql = "SELECT * FROM t_member_post WHERE is_active = TRUE LIMIT :limit OFFSET :offset";
			
			final List<MemberPost> res = ConnHandler.getManager().createNativeQuery(sql, MemberPost.class)
					.setParameter("offset", offset)
					.setParameter("limit",limit)
					.getResultList();
			
			return res;
	}

	@Override
	Optional<MemberPost> getByIdRef(Long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(super.getByIdRef(MemberPost.class, id));
	}
	
	
	
}
