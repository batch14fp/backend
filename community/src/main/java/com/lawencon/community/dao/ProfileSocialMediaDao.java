package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.ProfileSocialMedia;
import com.lawencon.community.model.SocialMedia;


@Repository
public class ProfileSocialMediaDao extends BaseMasterDao<ProfileSocialMedia>{

	public List<ProfileSocialMedia> getAll() throws Exception {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ps.id, p.id as profile_id, p.fullname, sm.id as social_media_id, sm.platform_name, ps.url, ps.ver FROM t_profile_social_media ps ");
		sqlQuery.append("INNER JOIN t_profile p ON ps.profile_id = p.id ");
		sqlQuery.append("INNER JOIN t_social_media sm ON ps.social_media_id = sm.id");


		@SuppressWarnings("unchecked")
		final List<Object[]> objs = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).getResultList();
		List<ProfileSocialMedia> list = new ArrayList<>();

		if (!objs.isEmpty()) {
			for (Object[] obj : objs) {
				final ProfileSocialMedia profileSocialMedia= new ProfileSocialMedia();
				final Profile profile = new Profile();
				final SocialMedia socialMedia = new SocialMedia();

				profileSocialMedia.setId(obj[0].toString());
				profile.setId(obj[1].toString());
				profile.setFullname(obj[2].toString());
				socialMedia.setId(obj[3].toString());
				socialMedia.setPlatformName(obj[4].toString());
				profileSocialMedia.setUrl(obj[5].toString());

				profileSocialMedia.setProfile(profile);
				profileSocialMedia.setSocialMedia(socialMedia);
				profileSocialMedia.setVersion(Integer.valueOf(obj[6].toString()));
				list.add(profileSocialMedia);
			}
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	public List<ProfileSocialMedia> getByProfileId(String id) throws Exception {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ps.id, p.id as profile_id, p.fullname, sm.id as social_media_id, sm.platform_name, ps.url, ps.ver FROM t_profile_social_media ps ");
		sqlQuery.append("INNER JOIN t_profile p ON ps.profile_id = p.id ");
		sqlQuery.append("INNER JOIN t_social_media sm ON ps.social_media_id = sm.id ");
		sqlQuery.append("WHERE ps.profile_id = :id");

		final List<Object[]> objs = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setParameter("id", id).getResultList();
				
		final List<ProfileSocialMedia> list = new ArrayList<>();

		if (!objs.isEmpty()) {
			for (Object[] obj : objs) {
				final ProfileSocialMedia profileSocialMedia = new ProfileSocialMedia();
				final Profile profile = new Profile();
				SocialMedia socialMedia = new SocialMedia();
				profileSocialMedia.setId(obj[0].toString());
				profile.setId(obj[1].toString());
				profile.setFullname(obj[2].toString());
				socialMedia.setId(obj[3].toString());
				socialMedia.setPlatformName(obj[4].toString());
				profileSocialMedia.setUrl(obj[5].toString());

				profileSocialMedia.setProfile(profile);
				profileSocialMedia.setSocialMedia(socialMedia);
				profileSocialMedia.setVersion(Integer.valueOf(obj[6].toString()));
				list.add(profileSocialMedia);
			
			}
		}
		return list;
	}

	@Override
	Optional<ProfileSocialMedia> getById(String id) {
		return Optional.ofNullable(super.getById(ProfileSocialMedia.class, id));
	}

	@Override
	public ProfileSocialMedia getByIdRef(String id) {
	
		return super.getByIdRef(ProfileSocialMedia.class, id);
	}

	@Override
	Optional<ProfileSocialMedia> getByIdAndDetach(String id) {
		
		return Optional.ofNullable(super.getByIdAndDetach(ProfileSocialMedia.class, id));
	}

}
