package com.lawencon.community.dao;

import java.util.ArrayList;
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
		    StringBuilder sql = new StringBuilder();
		    sql.append("SELECT id, platform_name, ver, is_active ");
		    sql.append("FROM t_social_media");

		    List<SocialMedia> socialMediaList = new ArrayList<>();
		    try {
		    List<Object[]> result = ConnHandler.getManager().createNativeQuery(sql.toString()).getResultList();

		    for (Object[] obj : result) {
		        final SocialMedia socialMedia = new SocialMedia();
		        socialMedia.setId(obj[0].toString());
		        socialMedia.setPlatformName(obj[1].toString());
		        socialMedia.setVersion(Integer.valueOf(obj[2].toString()));
		        socialMedia.setIsActive(Boolean.valueOf(obj[3].toString()));
		        socialMediaList.add(socialMedia);
		    }
		    }catch(Exception e) {
		    	e.printStackTrace();
		    }

		    return socialMediaList;
		
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
