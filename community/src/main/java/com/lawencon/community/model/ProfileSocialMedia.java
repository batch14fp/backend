package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_profile_social_media", uniqueConstraints = {
		@UniqueConstraint(name = "profile_social_media_ck", columnNames = { "profile_id", "social_media_id" }

		) })
public class ProfileSocialMedia extends BaseEntity {
	
	
	@OneToOne
	@JoinColumn(name="profile_id", nullable= false)
	private Profile profile;
	
	@OneToOne
	@JoinColumn(name="social_media_id", nullable= false)
	private SocialMedia socialMedia;
	
	
	private String url;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public SocialMedia getSocialMedia() {
		return socialMedia;
	}

	public void setSocialMedia(SocialMedia socialMedia) {
		this.socialMedia = socialMedia;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
