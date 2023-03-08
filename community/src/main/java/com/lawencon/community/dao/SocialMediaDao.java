package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.SocialMedia;


@Repository
public class SocialMediaDao extends BaseMasterDao<SocialMedia>{

	@SuppressWarnings("unchecked")
	@Override
	List<SocialMedia> getAll() {
		final String sql = "SELECT * FROM t_social_media WHERE  is_active = TRUE";	
		final List<SocialMedia> res = ConnHandler.getManager().createNativeQuery(sql, SocialMedia.class).getResultList();
		
		return res;
	}

	@Override
	Optional<SocialMedia> getById(Long id) {
		final SocialMedia socialMedia= ConnHandler.getManager().find(SocialMedia.class, id);
		return Optional.ofNullable(socialMedia);
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <SocialMedia> SocialMedia getByIdRef(Class<SocialMedia> entityClass, Object id) {
		return super.getByIdRef(entityClass, id);
	}
	

}
