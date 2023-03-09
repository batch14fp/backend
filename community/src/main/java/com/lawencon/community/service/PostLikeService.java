package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.community.dao.PostLikeDao;

@Service
public class PostLikeService {
	private PostLikeDao postLikeDao;
	
	public PostLikeService( PostLikeDao postLikeDao) {
		this.postLikeDao = postLikeDao;
	}
}
