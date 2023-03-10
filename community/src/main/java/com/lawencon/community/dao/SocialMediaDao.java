package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.SocialMedia;

@Repository
public class SocialMediaDao extends BaseMasterDao<SocialMedia> {

	@SuppressWarnings("unchecked")
	@Override
	public List<SocialMedia> getAll() {
		final String sql = "SELECT * FROM t_social_media WHERE  is_active = TRUE";
		final List<SocialMedia> res = ConnHandler.getManager().createNativeQuery(sql, SocialMedia.class)
				.getResultList();

		return res;
	}

	@Override
	public Optional<SocialMedia> getById(String id) {
		return Optional.ofNullable(super.getById(SocialMedia.class, id));
	}

	@Override
	public SocialMedia getByIdRef(String id) {
		return super.getByIdRef(SocialMedia.class, id);
	}
	
	@Override
	public Optional<SocialMedia> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(SocialMedia.class, id));

	}

}
