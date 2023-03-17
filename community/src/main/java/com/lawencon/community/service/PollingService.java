package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.BaseBatchDao;
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
import com.lawencon.community.pojo.post.PojoPollingOptionInsertReq;
import com.lawencon.community.pojo.post.PojoPollingOptionUpdateReq;
import com.lawencon.community.pojo.post.PojoPollingUpdateReq;

@Service
public class PollingService {
	private PostDao postDao;
	private PollingDao pollingDao;
	private PostTypeDao postTypeDao;
	private PollingOptionDao pollingOptionDao;
	private BaseBatchDao baseBatchDao;
	public PollingService(final BaseBatchDao baseBatchDao,final PostTypeDao postTypeDao, final PostDao postDao, final PollingDao pollingDao, final PollingOptionDao pollingOptionDao) {
		this.pollingOptionDao = pollingOptionDao;
		this.pollingDao = pollingDao;
		this.postDao  = postDao;
		this.postTypeDao = postTypeDao;
		this.baseBatchDao =baseBatchDao;
	}
	
	public PojoInsertRes save(PojoPollingInsertReq data) {
	    ConnHandler.begin();

	    try {
	        final Polling polling = new Polling();
	        polling.setTitle(data.getPollingTitle());
	        polling.setEndAt(data.getEndAt());
	        polling.setIsOpen(true);
	        
	        final Post post = postDao.getByIdRef(data.getPostId());
	        post.setPolling(polling);
	        postDao.getByIdAndDetach(Post.class, post.getId());

	        final PostType postType = postTypeDao.getByIdRef(post.getPostType().getId());
	        post.setPostType(postType);

	        final Polling pollingNew = pollingDao.save(polling);

	        final List<PollingOption> options = new ArrayList<>();
	        for (PojoPollingOptionInsertReq option : data.getPollingOptions()) {
	            final PollingOption pollingOption = new PollingOption();
	            pollingOption.setPolling(pollingNew);
	            pollingOption.setContentPolling(option.getPollingContent());
	            options.add(pollingOption);
	        }
	        baseBatchDao.saveAll(options);

	        ConnHandler.commit();

	        final PojoInsertRes res = new PojoInsertRes();
	        res.setId(pollingNew.getId());
	        res.setMessage("Save Success!");
	        return res;

	    } catch (Exception e) {
	        ConnHandler.rollback();
	        throw e;
	    }
	}

	public PojoUpdateRes update(PojoPollingUpdateReq data) {
	    ConnHandler.begin();

	    try {
	        final Polling polling  = pollingDao.getByIdRef(data.getPollingId());
	        pollingDao.getByIdAndDetach(Polling.class, polling.getId());
	        polling.setEndAt(data.getEndAt());
	        polling.setTitle(data.getPollingTitle());

	        final List<PollingOption> options = new ArrayList<>();
	        for (PojoPollingOptionUpdateReq option : data.getPollingOptions()) {
	            final PollingOption pollingOption = pollingOptionDao.getByIdRef(option.getPollingOptionId());
	            pollingOptionDao.getByIdAndDetach(PollingOption.class, pollingOption.getId());
	            pollingOption.setPolling(polling);
	            pollingOption.setId(pollingOption.getId());
	            pollingOption.setContentPolling(option.getPollingOptionContent());
	            options.add(pollingOption);
	        }

	        baseBatchDao.saveAll(options);

	        
	        
	      
	        ConnHandler.commit();

	        final PojoUpdateRes res = new PojoUpdateRes();
	        res.setId(polling.getId());
	        res.setMessage("Update Success!");
	        return res;

	    } catch (Exception e) {
	        ConnHandler.rollback();
	        throw e;
	    }
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
