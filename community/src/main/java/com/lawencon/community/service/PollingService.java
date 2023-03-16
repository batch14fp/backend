package com.lawencon.community.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PollingDao;
import com.lawencon.community.dao.PollingOptionDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PollingOption;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostType;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoPollingInsertReq;
import com.lawencon.community.pojo.post.PojoPollingUpdateReq;

@Service
public class PollingService {
	private PostDao postDao;
	private PollingDao pollingDao;
	private PostTypeDao postTypeDao;
	private PollingOptionDao pollingOptionDao;
	public PollingService(final PostTypeDao postTypeDao, final PostDao postDao, final PollingDao pollingDao, final PollingOptionDao pollingOptionDao) {
		this.pollingOptionDao = pollingOptionDao;
		this.pollingDao = pollingDao;
		this.postDao  = postDao;
		this.postTypeDao = postTypeDao;
	}
	
	public PojoInsertRes save(PojoPollingInsertReq data) {
		ConnHandler.begin();
		
		
		final Polling polling = new Polling();
		polling.setTitle(data.getPollingTitle());
		polling.setEndAt(data.getEndAt());
		
		final Post post = postDao.getByIdRef(data.getPostId());
		postDao.getByIdAndDetach(Post.class, post.getId());
		
		
		final PostType postType = postTypeDao.getByIdRef(post.getPostType().getId());
		
		
		post.setPostType(postType);
		
	
		
		
		final Polling pollingNew = pollingDao.save(polling);
		data.getPollingOptions().forEach(option->{
			final PollingOption pollingOptions = new PollingOption();
			pollingOptions.setPolling(pollingNew);
			pollingOptions.setContentPolling(option.getPollingContent());
			pollingOptionDao.save(pollingOptions);
		});
		ConnHandler.commit();
		final PojoInsertRes res = new PojoInsertRes();
		res.setId(pollingNew.getId());
		res.setMessage("Save Success!");
		return res;
	}
	
	
	
	public PojoUpdateRes update(PojoPollingUpdateReq data) {
		ConnHandler.begin();
		final Polling polling  = pollingDao.getByIdRef(data.getPollingId());
		pollingDao.getByIdAndDetach(Polling.class, polling.getId());
		polling.setEndAt(data.getEndAt());
		polling.setTitle(data.getPollingTitle());
		data.getPollingOptions().forEach(option->{
			final PollingOption pollingOptions = pollingOptionDao.getByIdRef(option.getPollingOptionId());
			pollingOptionDao.getByIdAndDetach(PollingOption.class, pollingOptions.getId());
			pollingOptions.setPolling(polling);
			pollingOptions.setId(pollingOptions.getId());
			pollingOptions.setContentPolling(option.getPollingOptionContent());
			pollingOptionDao.save(pollingOptions);
		});
		ConnHandler.commit();
		final PojoUpdateRes res = new PojoUpdateRes();
		res.setId(polling.getId());
		res.setMessage("Save Success!");
		return res;
	}
	
	
	public PojoRes delete(String id) throws Exception {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		final List<PollingOption> pollingOptions = pollingOptionDao.getAllOptionByPollingId(id);;
		
		pollingOptionDao.delete(pollingOptions.getClass());
		
		Boolean result = pollingDao.deleteById(Polling.class, id);
	
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}
	}

	
}
