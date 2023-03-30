package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.File;
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
		sqlQuery.append("SELECT ps.id, p.id as profile_id, p.fullname, sm.id as social_media_id, sm.platform_name, ps.url,  ps.ver, ps.is_active, ps.created_at, ps.created_by  ");
		sqlQuery.append("FROM t_profile_social_media ps ");
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
				profileSocialMedia.setIsActive(Boolean.valueOf(obj[7].toString()));
				profileSocialMedia.setCreatedAt(Timestamp.valueOf(obj[8].toString()).toLocalDateTime());
				profileSocialMedia.setCreatedBy(obj[9].toString());
				list.add(profileSocialMedia);
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProfileSocialMedia> getSocialMediaByProfileId(String id) throws Exception {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT tp.id , sm.id as social_media_id,tp.profile_id,file_id, platform_name,tp.url, tp.is_active,tp.ver ");
		sqlQuery.append("FROM t_profile_social_media tp ");
		sqlQuery.append("INNER JOIN t_social_media sm ON tp.social_media_id = sm.id ");
		sqlQuery.append("WHERE tp.profile_id = :id ");
		sqlQuery.append("UNION ALL ");
		sqlQuery.append("SELECT null,sm.id,null,file_id,platform_name, null, null,null ");
		sqlQuery.append("FROM t_social_media sm ");
		sqlQuery.append("WHERE sm.id NOT IN (SELECT tp.social_media_id FROM t_profile_social_media tp WHERE tp.profile_id = :id)");
		final List<ProfileSocialMedia> list = new ArrayList<>();
		try {
		final List<Object[]> objs = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setParameter("id", id).getResultList();
				


		if (!objs.isEmpty()) {
			for (Object[] obj : objs) {
				final ProfileSocialMedia profileSocialMedia = new ProfileSocialMedia();
				if(obj[0]!=null) {
				profileSocialMedia.setId(obj[0].toString());
				}
				final SocialMedia socialMedia = new SocialMedia();
				socialMedia.setId(obj[1].toString());
				
				if(obj[2]!=null) {
					final Profile profile = new Profile();
					profile.setId(obj[2].toString());
					profileSocialMedia.setProfile(profile);
					}
				if(obj[3]!=null) {
				final File file = new File();
				file.setId(obj[3].toString());
				socialMedia.setFile(file);
				}
				socialMedia.setPlatformName(obj[4].toString());
				if(obj[5]!=null) {
				profileSocialMedia.setUrl(obj[5].toString());
				}
				
				
				if(obj[6]!=null) {
				profileSocialMedia.setIsActive(Boolean.valueOf(obj[6].toString()));
				}
				if(obj[7]!=null) {
					profileSocialMedia.setVersion(Integer.valueOf(obj[7].toString()));
					}
				
				profileSocialMedia.setSocialMedia(socialMedia);
				list.add(profileSocialMedia);
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
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
